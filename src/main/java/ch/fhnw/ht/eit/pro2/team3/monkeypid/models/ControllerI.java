package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.text.DecimalFormat;

public class ControllerI extends AbstractController {

    private double ti;

    public ControllerI(String name, double ti) {
        super(name);
        setParameters(ti);
    }

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

    public void setParameters(double ti) {
        this.ti = ti;
        calculateTransferFunction();
    }

    public double getTi() {
        return ti;
    }
}
