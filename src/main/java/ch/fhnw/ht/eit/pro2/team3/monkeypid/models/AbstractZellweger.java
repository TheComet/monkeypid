package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IZellweger;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractZellweger extends AbstractControllerCalculator implements IZellweger
{
    protected Plant plant = null;
    protected double phiDamping;
    double startFreq, endFreq;

    // 18 iterations is enough for a precision of 4 decimal digits (1.0000)
    protected double maxIterations = 18;

    protected int numSamplePoints = 1000;

	@Override
    public void setPlant(Plant path) {
        plant = path;
    }

    @Override
    public void setNumSamplePoints(int samples) {
        numSamplePoints = samples;
    }

    @Override
    public void setPhiDamping(double phiDamping) {
        this.phiDamping = phiDamping;
    }

    @Override
    public void setMaxIterations(int iterations) {
        maxIterations = iterations;
    }

    public double calculatePI() {

        // get minimum and maximum time constants
        List timeConstantsList = Arrays.asList(ArrayUtils.toObject(plant.getTimeConstants()));
        double tcMin = (double) Collections.min(timeConstantsList);
        double tcMax = (double) Collections.max(timeConstantsList);

        // calculate the frequency range to use in all following calculations,
        // based on the time constants.
        startFreq = 1.0 / (tcMax * 10.0);
        endFreq = 1.0 / (tcMin / 10.0);

        double[] omega = linspace(startFreq, endFreq, numSamplePoints);

        // calculate amplitude control path, both in linear and dB
        double[] ampControlPath = new double[numSamplePoints];
        for (int i = 0; i != numSamplePoints; ++i) {
            ampControlPath[i] = amplitudeControlPath(
                    omega[i],
                    plant.getKs(),
                    plant.getTimeConstants());
        }

        // calculate phase control path
        double[] phiControlPath = new double[numSamplePoints];
        for (int i = 0; i != numSamplePoints; ++i) {
            phiControlPath[i] = phaseControlPath(omega[i], plant.getTimeConstants());
        }

        // TODO this is user-adjustable in the GUI
        double phiPI = -90.0;

        // find phiPI on the phase of the control path
        double topFreq = endFreq;
        double bottomFreq = startFreq;
        double actualFreq = (topFreq + bottomFreq) / 2.0;
        double phiControlPathPI;
        for (int i = 0; i != maxIterations; ++i) {
            phiControlPathPI = phaseControlPath(actualFreq, plant.getTimeConstants());
            if (phiControlPathPI < phiPI) {
                topFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            } else if (phiControlPathPI > phiPI) {
                bottomFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            }
        }

        // found wPI
        double wPI = actualFreq;

        // Tn parameter of controller
        double tn = 1.0 / wPI;

        // phase controller
        double[] phiController = new double[numSamplePoints];
        double[] phiOpenLoop = new double[numSamplePoints];
        for(int i = 0; i != numSamplePoints; i++) {
            phiController[i] = phaseControllerPI(omega[i], tn);
            phiOpenLoop[i] = phiControlPath[i] + phiController[i];
        }

        // amplitude controller
        double[] ampController = new double[numSamplePoints];
        double[] ampOpenLoop = new double[numSamplePoints];
        for(int i = 0; i != numSamplePoints; i++) {
            ampController[i] = amplitudeControllerPI(omega[i], tn);
            ampOpenLoop[i] = ampController[i] * ampControlPath[i];
        }

        // find phiDamping on the phase of the open loop
        topFreq = endFreq;
        bottomFreq = startFreq;
        actualFreq = (topFreq + bottomFreq) / 2.0;
        for(int i = 0; i != maxIterations; i++) {
            double phiOpenLoopBuffer = phaseControlPath(actualFreq, plant.getTimeConstants()) +
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
        double ampOpenLoopKr = amplitudeControlPath(
                wDamping,
                plant.getKs(),
                plant.getTimeConstants()) * amplitudeControllerPI(wDamping, tn);

        // Kr is the reciprocal of the amplitude at wDamping
        double kr = 1.0 / ampOpenLoopKr;

        return kr;
    }

    double calculatePID() {

        // get minimum and maximum time constants
        List timeConstantsList = Arrays.asList(ArrayUtils.toObject(plant.getTimeConstants()));
        double tcMin = (double) Collections.min(timeConstantsList);
        double tcMax = (double) Collections.max(timeConstantsList);

        // calculate the frequency range to use in all following calculations,
        // based on the time constants.
        startFreq = 1.0 / (tcMax * 10.0);
        endFreq = 1.0 / (tcMin / 10.0);

        double[] omega = linspace(startFreq, endFreq, numSamplePoints);

        // calculate amplitude control path, both in linear and dB
        double[] ampControlPath = new double[numSamplePoints];
        for (int i = 0; i != numSamplePoints; ++i) {
            ampControlPath[i] = amplitudeControlPath(
                    omega[i],
                    plant.getKs(),
                    plant.getTimeConstants());
        }

        // calculate phase control path
        double[] phiControlPath = new double[numSamplePoints];
        for (int i = 0; i != numSamplePoints; ++i) {
            phiControlPath[i] = phaseControlPath(omega[i], plant.getTimeConstants());
        }

        // TODO this is user-adjustable in the GUI
        double phiPI = -135.0;

        // find phiPI on the phase of the control path
        double topFreq = endFreq;
        double bottomFreq = startFreq;
        double actualFreq = (topFreq + bottomFreq) / 2.0;
        double phiControlPathPI;
        for (int i = 0; i != maxIterations; ++i) {
            phiControlPathPI = phaseControlPath(actualFreq, plant.getTimeConstants());
            if (phiControlPathPI < phiPI) {
                topFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            } else if (phiControlPathPI > phiPI) {
                bottomFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            }
        }

        // found wPID
        double wPID = actualFreq;

        double beta = beta(wPID, plant.getTimeConstants());

        double tnk = 1.0 / (wPID * beta);
        double tvk = beta / wPID;
        double tp = tvk / 10.0; // Tp is one decade higher than Tvk

        // phase controller
        double[] phiController = new double[numSamplePoints];
        double[] phiOpenLoop = new double[numSamplePoints];
        for(int i = 0; i != numSamplePoints; i++) {
            phiController[i] = phaseControllerPID(omega[i], tnk, tvk, tp);
            phiOpenLoop[i] = phiControlPath[i] + phiController[i];
        }

        // amplitude controller
        double[] ampController = new double[numSamplePoints];
        double[] ampOpenLoop = new double[numSamplePoints];
        for(int i = 0; i != numSamplePoints; i++) {
            ampController[i] = amplitudeControllerPID(omega[i], tnk, tvk, tp);
            ampOpenLoop[i] = ampController[i] * ampControlPath[i];
        }

        // find phiDamping on the phase of the open loop
        topFreq = endFreq;
        bottomFreq = startFreq;
        actualFreq = (topFreq + bottomFreq) / 2.0;
        for(int i = 0; i != maxIterations; i++) {
            double phiOpenLoopBuffer = phaseControlPath(actualFreq, plant.getTimeConstants()) +
                    phaseControllerPID(actualFreq, tnk, tvk, tp);
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
        double ampOpenLoopKr = amplitudeControlPath(
                wDamping,
                plant.getKs(),
                plant.getTimeConstants()) * amplitudeControllerPID(wDamping, tnk, tvk, tp);

        // Kr is the reciprocal of the amplitude at wDamping
        double krk = 1.0 / ampOpenLoopKr;

        return 0.0;
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
     * Calculates the phase of a PI controller
     * @param omega Omega
     * @param tn Controller parameter Tn
     * @return atan(w*Tn)-pi/2
     */
    private double phaseControllerPI(double omega, double tn) {
        return (Math.atan(omega * tn) - Math.PI / 2.0) * 180 / Math.PI;
    }

    /**
     * Calculates the phase of a PID controller (without control path)
     * @param omega Omega
     * @param tnk Bode parameter Tnk
     * @param tvk Bode parameter Tvk
     * @param tp Bode parameter Tp
     * @return Returns the phase in degrees. atan(w*Tnk)+atan(w*Tvk)-atan(w*Tp)-pi/2.
     */
    private double phaseControllerPID(double omega, double tnk, double tvk, double tp) {
        double phi = Math.atan(omega * tnk);
        phi += Math.atan(omega * tvk);
        phi -= Math.atan(omega * tp);
        phi -= Math.PI / 2.0;
        return phi * 180 / Math.PI; // convert to degree
    }

    /**
     * Calculates the amplitude of a PI controller
     * @param omega Omega
     * @param tn Controller parameter Tn
     * @return (sqrt(1+(w*Tn).^2)./(w*Tn))
     */
    private double amplitudeControllerPI(double omega, double tn) {
        return Math.sqrt(1.0 + Math.pow(omega * tn, 2)) / (omega * tn);
    }

    /**
     * Calculates the amplitude of a PID controller
     * @param omega Omega
     * @param tnk Bode parameter Tnk
     * @param tvk Bode parameter Tvk
     * @param tp Bode parameter Tp
     * @return (sqrt(1+(w*Tnk)^2)*sqrt(1+(w*Tvk)^2))/((w*Tnk)*sqrt(1+(w*Tp)^2))
     */
    private double amplitudeControllerPID(double omega, double tnk, double tvk, double tp) {
        double numerator = Math.sqrt(1.0 + Math.pow(omega * tnk, 2.0));
        numerator *= Math.sqrt(1.0 + Math.pow(omega * tvk, 2.0));

        double denominator = omega * tnk * Math.sqrt(1.0 + Math.pow(omega * tp, 2.0));

        return numerator / denominator;
    }

    /**
     * Calculates beta for Tnk and Twk
     *
     * Calculation is:
     * Z = -wPID * (-(atan(w*T1)+atan(w*T2)+atan(w*Tc)));
     * Beta = 1/Z - (+) sqrt(1/Z^2 - 1);
     * eventually use positive or negative solution of sqrt()
     *   ATTENTION: Beta has to be in the interval ]0,1]
     *   For Z > 1 -> set Z to 1 -> Beta is 1
     * @param wPID Omega of the found angle phiPID
     * @param timeConstants Array of time constants to use
     * @return Returns beta.
     */
    private double beta(double wPID, double[] timeConstants) {
        double phiBuffer = 0;
        for(double timeConstant : timeConstants) {
            phiBuffer -= Math.atan(wPID * timeConstant);
        }

        double z = -wPID * phiBuffer - 0.5;
        z = Math.max(z, 1.0); // clamp z to 1.0 so no complex betas are calculated

        return 1.0 / z - Math.sqrt(1.0 / Math.pow(z, 2.0) - 1.0);
    }

    /**
     * Converts the bode parameters to the controller parameters
     * @param tnk Bode parameter Tnk
     * @param tvk Bode parameter Tvk
     * @param tp Bode parameter Tp
     * @param krk Bode parameter Krk
     * @return Returns a double array of Tn, Tv, Kr (in that order), where Tn, Tv, and Kr are
     * calculated as follows:
     *   Tn = Tnk+Tvk-Tp;
     *   Tv = (Tnk*Tvk)/(Tnk+Tvk-Tp) - Tp;
     *   Kr = Krk*(1+Tvk/Tnk);
     */
    private double[] bodeToController(double tnk, double tvk, double tp, double krk) {
        double[] tntvkr = new double[3]; // return Tn, Tv, and Kr in an array
        tntvkr[0] = tnk + tvk - tp;
        tntvkr[1] = (tnk * tvk) / tntvkr[0] - tp;
        tntvkr[2] = krk * (1.0 + tvk / tnk);
        return tntvkr;
    }
}