package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.text.DecimalFormat;

/**
 * Implements an I controller. This consists of the controller parameter Ti and a transfer function.
 * @author Alex Murray
 */
public class ControllerI extends AbstractController {

    private double ti;

    /**
     * Constructs a new I controller from the parameter Ti and calculates the transfer function.
     * @param name A unique name for this controller. Must be unique for all controllers in a simulation.
     * @param ti The controller parameter Ti.
     */
    public ControllerI(String name, double ti) {
        super(name);
        setParameters(ti);
    }

    /**
     * Sets the parameter Ti for the I controller and calculates the transfer function.
     * @param ti The parameter Ti.
     */
    public final void setParameters(double ti) {
        this.ti = ti;
        calculateTransferFunction();
    }

    /**
     * Gets the controller parameter Ti.
     * @return Ti.
     */
    public final double getTi() {
        return ti;
    }

    /**
     * Calculates the transfer function for a PI controller.
     */
    @Override
    protected void calculateTransferFunction() {
        // Numerator and Denominator Poly of the pi-controller:
        // Kr (1 + 1/(s*Tn)) = Kr * (s*Tn + 1)/(s*Tn)
        //   Br = [1];
        //   Ar = [Tn 0];
        double[] numeratorCoefficients = new double[] {1};
        double[] denominatorCoefficients = new double[] {ti, 0};
        setTransferFunction(
                new TransferFunction(numeratorCoefficients, denominatorCoefficients)
        );
    }

    /**
     * Creates an array of strings to be inserted into the table in the GUI.
     * @return Array of strings of the length 5.
     */
    @Override
    public String[] getTableRowStrings() {
        return new String[]{
                getName(),
                "", // Kr  (PI/PID only)
                new DecimalFormat("0.000E0").format(getTi()),
                "", // Tv  (PID only)     -- need to pad so overswing value has the correct offset in the table
                ""  // Tp  (PID only)
        };
    }
}
