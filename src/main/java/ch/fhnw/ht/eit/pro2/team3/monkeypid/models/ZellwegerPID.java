package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

public class ZellwegerPID extends AbstractZellweger {

    private double beta = 0.0;

    public ZellwegerPID(double phiDamping) {
        super(phiDamping);
    }

    @Override
    public void calculate(Plant plant) {
        setPlant(plant);
        setAngleOfInflection(-135.0);
        this.controller = calculatePID();
    }

    private IController calculatePID() {

        // find angleOfInflection on the phase of the control path
        double wPID = findPhaseOnControlPath();

        beta = calculateBeta(wPID, plant.getTimeConstants());

        double tnk = 1.0 / (wPID * beta);
        double tvk = beta / wPID;
        double tp = tvk / 10.0; // Tp is one decade higher than Tvk

        // find phiDamping on the phase of the open loop
        double wDamping = findPhaseOpenLoop(tnk, tvk, tp);

        // amplitude of the open loop at the wDamping frequency
        double ampOpenLoopKr = calculatePlantAmplitude(
                wDamping,
                plant.getKs(),
                plant.getTimeConstants()) * amplitudeControllerPID(wDamping, tnk, tvk, tp);

        // Kr is the reciprocal of the amplitude at wDamping
        double krk = 1.0 / ampOpenLoopKr;

        double[] tntvkr = bodeToController(tnk, tvk, tp, krk);

        return new PIDController(tntvkr[0], tntvkr[1], tntvkr[2], tp);
    }

    private double findPhaseOpenLoop(double tnk, double tvk, double tp) {

        // find phiDamping on the phase of the open loop
        double topFreq = endFreq;
        double bottomFreq = startFreq;
        double actualFreq = (topFreq + bottomFreq) / 2.0;
        for(int i = 0; i != maxIterations; i++) {
            double phiOpenLoopBuffer = calculatePlantPhase(actualFreq, plant.getTimeConstants()) +
                    phaseControllerPID(actualFreq, tnk, tvk, tp);
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
     * Calculates calculateBeta for Tnk and Twk
     *
     * Calculation is:
     * Z = -wPID * (-(atan(w*T1)+atan(w*T2)+atan(w*Tc)));
     * Beta = 1/Z - (+) sqrt(1/Z^2 - 1);
     * eventually use positive or negative solution of sqrt()
     *   ATTENTION: Beta has to be in the interval ]0,1]
     *   For Z > 1 -> set Z to 1 -> Beta is 1
     * @param wPID Omega of the found angle phiPID
     * @param timeConstants Array of time constants to use
     * @return Returns calculateBeta.
     */
    private double calculateBeta(double wPID, double[] timeConstants) {
        double phiBuffer = 0;
        for(double timeConstant : timeConstants) {
            phiBuffer -= Math.atan(wPID * timeConstant);
        }

        double z = -wPID * phiBuffer - 0.5;
        z = Math.min(z, 1.0); // clamp z to 1.0 so no complex betas are calculated

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
        tntvkr[2] = krk * (tnk + tvk - tp) / tnk;
        return tntvkr;
    }

    public double getBeta() {
        return beta;
    }
}