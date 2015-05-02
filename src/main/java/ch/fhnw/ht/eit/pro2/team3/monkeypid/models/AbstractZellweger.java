package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IZellweger;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractZellweger extends AbstractControllerCalculator implements IZellweger
{
    protected Plant plant = null;
    protected double phiDamping;
    protected double phi;
    protected double startFreq, endFreq;

    // 18 iterations is enough for a precision of 4 decimal digits (1.0000)
    protected double maxIterations = 18;

    protected int numSamplePoints = 1000;

	@Override
    public void setPlant(Plant path) {
        plant = path;
        updateFrequencyRange();
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
    public void setPhi(double phi) {
        this.phi = phi;
    }

    @Override
    public void setMaxIterations(int iterations) {
        maxIterations = iterations;
    }

    @Override
    public IController getController() {
        return this.controller;
    }

    private void updateFrequencyRange() {

        // get minimum and maximum time constants
        List timeConstantsList = Arrays.asList(ArrayUtils.toObject(plant.getTimeConstants()));
        double tcMin = (double) Collections.min(timeConstantsList);
        double tcMax = (double) Collections.max(timeConstantsList);

        // calculate the frequency range to use in all following calculations,
        // based on the time constants.
        startFreq = 1.0 / (tcMax * 10.0);
        endFreq = 1.0 / (tcMin / 10.0);
    }

    protected double[] linspace(double start, double end, int num) {
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
    protected double phaseControlPath(double omega, double[] timeConstants) {
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
    protected double amplitudeControlPath(double omega, double ks, double[] timeConstants) {
        double denominator = 1.0;
        for(double timeConstant : timeConstants) {
            denominator += Math.sqrt(1.0 + Math.pow(omega * timeConstant, 2));
        }
        return ks / denominator;
    }
}