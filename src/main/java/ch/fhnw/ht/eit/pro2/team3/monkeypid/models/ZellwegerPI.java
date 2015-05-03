package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

public class ZellwegerPI extends AbstractZellweger {

    public ZellwegerPI(double phaseMargin, Plant plant) {
        super(phaseMargin, plant);
    }

    @Override
    public void calculate() {
        setAngleOfInflection(-90.0);
        this.controller = calculatePI();
    }

    private IController calculatePI() {

        // Tn parameter of controller
        double tn = 1.0 / findPhaseOnControlPath();

        // get omega damping
        double wDamping = findPhaseOnOpenLoopPI(tn);

        // amplitude of the open loop at the wDamping frequency
        double ampOpenLoopKr = calculatePlantAmplitude(
                wDamping,
                plant.getKs(),
                plant.getTimeConstants()) * amplitudeControllerPI(wDamping, tn);

        // Kr is the reciprocal of the amplitude at wDamping
        double kr = 1.0 / ampOpenLoopKr;

        return new PIController(kr, tn);
    }

    protected double findPhaseOnOpenLoopPI(double tn) {

        // find phiDamping on the phase of the open loop
        double topFreq = endFreq;
        double bottomFreq = startFreq;
        double actualFreq = (topFreq + bottomFreq) / 2.0;
        for(int i = 0; i != maxIterations; i++) {
            double phiOpenLoopBuffer = calculatePlantPhase(actualFreq, plant.getTimeConstants()) +
                    phaseControllerPI(actualFreq, tn);
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
    private double phaseControllerPI(double omega, double tn) {
        return (Math.atan(omega * tn) - Math.PI / 2.0) * 180 / Math.PI;
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
}