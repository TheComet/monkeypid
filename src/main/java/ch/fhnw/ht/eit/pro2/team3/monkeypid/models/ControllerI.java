package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.text.DecimalFormat;

public class ControllerI extends AbstractController {

    private double tn;

    public ControllerI(String name, double tn) {
        super(name);
        setParameters(tn);
    }

    @Override
    protected void calculateTransferFunction() {
        // Numerator and Denominator Poly of the pi-controller:
        // Kr (1 + 1/(s*Tn)) = Kr * (s*Tn + 1)/(s*Tn)
        //   Br = [1];
        //   Ar = [Tn 0];
        double[] numeratorCoefficients = new double[] {1};
        double[] denominatorCoefficients = new double[] {tn, 0};
        setTransferFunction(
                new TransferFunction(numeratorCoefficients, denominatorCoefficients)
        );
    }

    @Override
    public String[] getTableRowStrings() {
        return new String[]{
                getName(),
                "", // Kr  (PI/PID only)
                new DecimalFormat("0.000E0").format(getTn()),
                "", // Tv  (PID only)     -- need to pad so overswing value has the correct offset in the table
                ""  // Tp  (PID only)
        };
    }

    public void setParameters(double tn) {
        this.tn = tn;
        calculateTransferFunction();
    }

    public double getTn() {
        return tn;
    }
}
