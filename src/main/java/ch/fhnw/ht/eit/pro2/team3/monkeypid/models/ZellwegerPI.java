package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

public class ZellwegerPI extends AbstractZellweger {

    @Override
    public void calculate(Plant path) {
        setPlant(path);
        setPhi(-90.0);
        this.controller = calculatePI();
    }

    private IController calculatePI() {

        double[] omega = linspace(startFreq, endFreq, numSamplePoints);

        /* TODO Apparently this isn't required?
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
        }*/

        // find phiPI on the phase of the control path
        double topFreq = endFreq;
        double bottomFreq = startFreq;
        double actualFreq = (topFreq + bottomFreq) / 2.0;
        double phiControlPathPI;
        for (int i = 0; i != maxIterations; ++i) {
            phiControlPathPI = phaseControlPath(actualFreq, plant.getTimeConstants());
            if (phiControlPathPI < phi) {
                topFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            } else if (phiControlPathPI > phi) {
                bottomFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            }
        }

        // found wPI
        double wPI = actualFreq;

        // Tn parameter of controller
        double tn = 1.0 / wPI;

        // TODO Apparently this isn't required?
        /*
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
        }*/

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

        return new PIController(kr, tn);
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