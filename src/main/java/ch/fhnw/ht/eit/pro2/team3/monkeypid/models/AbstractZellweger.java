package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IZellweger;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractZellweger extends AbstractControllerCalculator implements IZellweger
{
    protected double phiDamping;
    protected double angleOfInflection;
    protected double startFreq, endFreq;
    protected int numSamplePoints = 1000;

    // 18 iterations is enough for a precision of 4 decimal digits (1.0000)
    protected double maxIterations = 18;

    public AbstractZellweger(double phaseMargin, Plant plant) {
        super(plant);
        setPhaseMargin(phaseMargin);
    }

    /**
     * Because Zellweger has to do some additional calculations whenever the plant changes,
     * override the method.
     * @param plant The new plant to use for future calculations.
     */
    @Override
    public void setPlant(Plant plant) {
        this.plant = plant;
        updateFrequencyRange();
    }

    @Override
    public void setNumSamplePoints(int samples) {
        numSamplePoints = samples;
    }

    @Override
    public void setPhaseMargin(double phi) {
        this.phiDamping = phi - 180;
    }

    @Override
    public void setAngleOfInflection(double angleOfInflection) {
        this.angleOfInflection = angleOfInflection;
    }

    @Override
    public void setMaxIterations(int iterations) {
        maxIterations = iterations;
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

    /**
     * Calculates the phase of the plant (without the regulator)
     * @param omega Omega
     * @param timeConstants Array of time constants to use
     * @return -(atan(w*T1)+atan(w*T2)+atan(w*Tc))
     */
    protected double calculatePlantPhase(double omega, double[] timeConstants) {
        double phiControlPath = 0.0;
        for(double timeConstant : timeConstants) {
            phiControlPath += Math.atan(omega * timeConstant);
        }

        // convert to degrees and invert sign
        return -phiControlPath * 180.0 / Math.PI;
    }

    /**
     * Calculates the amplitude of the plant (without the regulator)
     * @param omega Omega
     * @param ks Multiplicator of the control path
     * @param timeConstants Array of time constants to use
     * @return Ks/(sqrt(1+(w*T1)^2)*sqrt(1+(w*T2)^2)*sqrt(1+(w*Tc)^2))
     */
    protected double calculatePlantAmplitude(double omega, double ks, double[] timeConstants) {
        double denominator = 1.0;
        for(double timeConstant : timeConstants) {
            denominator *= Math.sqrt(1.0 + Math.pow(omega * timeConstant, 2));
        }
        return ks / denominator;
    }

    protected double findPhaseOnControlPath() {

        // find angleOfInflection on the phase of the control path
        double topFreq = endFreq;
        double bottomFreq = startFreq;
        double actualFreq = (topFreq + bottomFreq) / 2.0;
        double phi;
        for (int i = 0; i != maxIterations; ++i) {
            phi = calculatePlantPhase(actualFreq, plant.getTimeConstants());
            if (phi < angleOfInflection) {
                topFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            } else if (phi > angleOfInflection) {
                bottomFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            }
        }

        return actualFreq;
    }
}