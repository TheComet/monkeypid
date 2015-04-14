package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.MathMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Zellweger implements MathMethod
{
    private MathMethodFactory.Mode mode;

    public Zellweger(MathMethodFactory.Mode mode) {
        this.mode = mode;
    }

    public void exec(double phiDamping, double ks, double[] timeConstants) {

        // 18 iterations is enough for a precision of 4 decimal digits (1.0000)
        int maxIterations = 18;

        // get minimum and maximum time constants
        List timeConstantsList = Arrays.asList(timeConstants);
        double tcMin = (double)Collections.min(timeConstantsList);
        double tcMax = (double)Collections.max(timeConstantsList);

        // calculate the frequency range to use in all following calculations,
        // based on the time constants.
        double startFreq = 1.0 / (tcMax * 10.0);
        double endFreq = 1.0 / (tcMin * 10.0);

        int numSamplePoints = 1000;
        double[] freq = linspace(startFreq, endFreq, numSamplePoints);
        double[] omega = linspace(startFreq, endFreq, numSamplePoints);

        // calculate amplitude control path, both in linear and dB
        double[] ampControlPath = new double[numSamplePoints];
        double[] ampControlPathdB = new double[numSamplePoints];
        for(int i = 0; i != numSamplePoints; ++i) {
            ampControlPath[i] = amplitudeControlPath(omega[i], ks, timeConstants);
            ampControlPathdB[i] = 20.0 * Math.log10(ampControlPath[i]);
        }

        // calculate phase control path, both in linear and dB
        double[] phiControlPath = new double[numSamplePoints];
        for(int i = 0; i != numSamplePoints; ++i) {
            phiControlPath[i] = phaseControlPath(omega[i], timeConstants);
        }

        // TODO this is user-adjustable in the GUI
        double phiPI = -90.0;

        // find phiPI on the phase of the control path
        double topFreq = endFreq;
        double bottomFreq = startFreq;
        double actualFreq = (topFreq + bottomFreq) / 2.0;
        double phiControlPathPI;
        for(int i = 0; i != maxIterations; ++i) {
            phiControlPathPI = phaseControlPath(actualFreq, timeConstants);
            if(phiControlPathPI < phiPI) {
                topFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            } else if(phiControlPathPI > phiPI) {
                bottomFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            }
        }

        // found wPI
        double wPI = actualFreq;

        // Tn parameter of controller
        double tn = 1.0 / wPI;

        // phase controller for plot
        double[] phiController = new double[numSamplePoints];
        double[] phiOpenLoop = new double[numSamplePoints];
        for(int i = 0; i != numSamplePoints; i++) {
            phiController[i] = amplitudeControllerPI(omega[i], tn);
            phiOpenLoop[i] = phiControlPath[i] + phiController[i];
        }

        // amplitude controller
        double[] ampController = new double[numSamplePoints];
        double[] ampOpenLoop = new double[numSamplePoints];
        double[] ampOpenLoopdB = new double[numSamplePoints];
        for(int i = 0; i != numSamplePoints; i++) {
            ampController[i] = amplitudeControllerPI(omega[i], tn);
            ampOpenLoop[i] = ampController[i] * ampControlPath[i];
            ampOpenLoopdB[i] = 20.0 * Math.log10(ampOpenLoop[i]);
        }

        // find phiDamping on the phase of the open loop
        topFreq = endFreq;
        bottomFreq = startFreq;
        actualFreq = (topFreq + bottomFreq) / 2.0;
        for(int i = 0; i != maxIterations; i++) {
            double phiOpenLoopBuffer = phaseControlPath(actualFreq, timeConstants) +
                    phaseControllerPI(actualFreq, tn);
            if(phiOpenLoopBuffer < phiDamping) {
                topFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            } else if(phiOpenLoopBuffer > phiDamping) {
                bottomFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            }
        }

        // found damping
        double wDamping = actualFreq;

        // amplitude of the open loop at the wDamping frequency
        double ampOpenLoopKr = amplitudeControlPath(wDamping, ks, timeConstants) *
                amplitudeControllerPI(wDamping, tn);

        // Kr is the reciprocal of the amplitude at wDamping
        double kr = 1.0 / ampOpenLoopKr;
    }

    private double[] linspace(double start, double end, int num) {
        double ret[] = new double[num];
        double value = start;
        double step = (end - start) / num;

        for(int i = 0; i != ret.length; i++) {
            ret[i] = value;
            value += step;
        }

        return ret;
    }

    /**
     * Calcualtes the phase of the control path (without the regulator)
     * @param omega Omega
     * @param timeConstants Array of time constants to use
     * @return -(atan(w*T1)+atan(w*T2)+atan(w*Tc))
     */
    private double phaseControlPath(double omega, double[] timeConstants) {
        double phiControlPath = 0.0;
        for(double timeConstant : timeConstants) {
            phiControlPath += Math.atan(omega * timeConstant);
        }

        // convert to degrees and invert sign
        return -phiControlPath * 180.0 / Math.PI;
    }

    /**
     * Calculates the amplitude of the control path (without the regulator)
     * @param omega Omega
     * @param ks Multiplicator of the control path
     * @param timeConstants Array of time constants to use
     * @return Ks/(sqrt(1+(w*T1)^2)*sqrt(1+(w*T2)^2)*sqrt(1+(w*Tc)^2))
     */
    private double amplitudeControlPath(double omega, double ks, double[] timeConstants) {
        double denominator = 1.0;
        for(double timeConstant : timeConstants) {
            denominator += Math.sqrt(1.0 + Math.pow(omega * timeConstant, 2));
        }
        return ks / denominator;
    }

    /**
     * Calculates the phase of the controller
     * @param omega Omega
     * @param tn Controller parameter Tn
     * @return atan(w*Tn)-pi/2
     */
    private double phaseControllerPI(double omega, double tn) {
        return (Math.atan(omega * tn) - Math.PI / 2.0) * 180 / Math.PI;
    }

    /**
     * Calculates the amplitude of the controller
     * @param omega Omega
     * @param tn Controller parameter Tn
     * @return (sqrt(1+(w*Tn).^2)./(w*Tn))
     */
    private double amplitudeControllerPI(double omega, double tn) {
        return Math.sqrt(1.0 + Math.pow(omega * tn, 2)) / (omega * tn);
    }

    @Override
    public void setOverSwing(double percent) {

    }

    @Override
    public void doCalculation(double tg, double tu, double ks) {
    }

    @Override
    public ControllerParameters getResult() {
        return null;
    }
}