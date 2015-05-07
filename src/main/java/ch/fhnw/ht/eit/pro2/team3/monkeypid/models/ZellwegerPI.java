package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

import java.awt.*;

public class ZellwegerPI extends AbstractZellweger {

    public ZellwegerPI(Plant plant, double phaseMargin) {
        super(plant, phaseMargin);
    }

    @Override
    protected final IController calculate() {
        setAngleOfInflection(-90.0);
        return calculatePI();
    }

    @Override
    public String getName() {
        return CalculatorNames.ZELLWEGER_PI;
    }

    @Override
    public Color getColor() {
        return RenderColors.ZELLWEGER_PI;
    }

    private IController calculatePI() {

        // Tn parameter of controller
        double tn = 1.0 / findAngleOnPlantPhase();

        // get omega damping
        double omegaDamping = findAngleOnOpenLoopPhase(tn);

        // amplitude of the open loop at the omegaDamping frequency
        double ampOpenLoopKr = calculatePlantAmplitude(omegaDamping) * calculateControllerAmplitude(omegaDamping, tn);

        // Kr is the reciprocal of the amplitude at omegaDamping
        double kr = 1.0 / ampOpenLoopKr;

        return new PIController(getName(), kr, tn);
    }

    protected double findAngleOnOpenLoopPhase(double tn) {

        // find phiDamping on the phase of the open loop
        double topFreq = endFreq;
        double bottomFreq = startFreq;
        double actualFreq = (topFreq + bottomFreq) / 2.0;
        for(int i = 0; i != maxIterations; i++) {
            double phiOpenLoopBuffer = calculatePlantPhase(actualFreq, plant.getTimeConstants()) +
                    calculateControllerPhase(actualFreq, tn);
            if(phiOpenLoopBuffer < phiDamping) {
                topFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            } else if(phiOpenLoopBuffer > phiDamping) {
                bottomFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            }
        }

        return actualFreq;
    }

    /**
     * Calculates the phase of a PI controller
     * @param omega Omega
     * @param tn Controller parameter Tn
     * @return atan(w*Tn)-pi/2
     */
    private double calculateControllerPhase(double omega, double tn) {
        return (Math.atan(omega * tn) - Math.PI / 2.0) * 180 / Math.PI;
    }

    /**
     * Calculates the amplitude of a PI controller
     * @param omega Omega
     * @param tn Controller parameter Tn
     * @return (sqrt(1+(w*Tn).^2)./(w*Tn))
     */
    private double calculateControllerAmplitude(double omega, double tn) {
        return Math.sqrt(1.0 + Math.pow(omega * tn, 2)) / (omega * tn);
    }
}