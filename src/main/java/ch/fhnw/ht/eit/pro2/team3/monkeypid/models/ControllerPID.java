package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.text.DecimalFormat;

/**
 * Implements a PID controller. This consists of the controller parameters Kr, Tn, Tv, and Tp, and a transfer function.
 * @author Alex Murray
 */
public class ControllerPID extends AbstractController {

    private double kr = 0.0;
    private double tn = 0.0;
    private double tv = 0.0;
    private double tp = 0.0;

    /**
     * Constructs a new PI controller from the parameters Kr and Tn and calculates the transfer function.
     * @param name A unique name for this controller. Must be unique for all controllers in a simulation.
     * @param tn The controller parameter Tn.
     * @param tv The controller parameter Tv.
     * @param kr The controller parameter Kr.
     * @param tp The controller parameter Tp.
     */
    public ControllerPID(String name, double tn, double tv, double kr, double tp) {
        super(name);
        setParameters(tn, tv, kr, tp);
    }

    /**
     * Sets the parameters Tn, Tv, Kr, and Tp for the PID controller and calculates the transfer function.
     * @param tn The controller parameter Tn.
     * @param tv The controller parameter Tv.
     * @param kr The controller parameter Kr.
     * @param tp The controller parameter p.
     */
    public final void setParameters(double tn, double tv, double kr, double tp) {
        this.tn = tn;
        this.tv = tv;
        this.kr = kr;
        this.tp = tp;
        calculateTransferFunction();
    }

    /**
     * Gets the controller parameter Tn.
     * @return Tn.
     */
    public final double getTn() {
        return tn;
    }

    /**
     * Gets the controller parameter Tv.
     * @return Tv.
     */
    public final double getTv() {
        return tv;
    }

    /**
     * Gets the controller parameter Kr.
     * @return Kr.
     */
    public final double getKr() {
        return kr;
    }

    /**
     * Gets the controller parameter Tp.
     * @return Tp.
     */
    public final double getTp() {
        return tp;
    }

    @Override
    public final void setKr(double kr) {
        setParameters(tn, tv, kr, tp);
    }

    /**
     * Calculates the transfer function for a PID controller.
     */
    @Override
    protected final void calculateTransferFunction() {
        // Numerator and Denominator Poly of the pid-controller:
        // Gr = Kr*(1+ 1/(s*Tn) + s*Tv/(1+s*Tp))
        //    = Kr * (s^2(Tn*Tv + Tn*Tp) + s*(Tn+Tv) + 1)/(s^2*(Tn*Tp)+ s*Tn)
        // Br = Kr*[Tn*Tv+Tn*Tp Tn+Tv 1];
        // Ar = [Tn*Tp Tn 0];
        double[] numeratorCoefficients = new double[] {kr * (tn*tv + tn*tp), kr * (tn + tv), kr};
        double[] denominatorCoefficients = new double[] {tn*tp, tn, 0};
        setTransferFunction(
                new TransferFunction(numeratorCoefficients, denominatorCoefficients)
        );
    }

    /**
     * Creates an array of strings to be inserted into the table in the GUI.
     * @return Array of strings of the length 5.
     */
    @Override
    public final String[] getTableRowStrings() {
        return new String[]{
                getName(),
                new DecimalFormat("0.000E0").format(getKr()),
                new DecimalFormat("0.000E0").format(getTn()),
                new DecimalFormat("0.000E0").format(getTv()),
                new DecimalFormat("0.000E0").format(getTp())
        };
    }
}
