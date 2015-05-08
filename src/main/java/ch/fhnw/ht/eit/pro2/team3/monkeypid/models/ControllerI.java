package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class ControllerI extends AbstractController {

    private double tn;

    public ControllerI(String name) {
        super(name);
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
        return new String[0];
    }

    public void setParameters(double tn) {
        this.tn = tn;
        calculateTransferFunction();
    }

    public double getTn() {
        return tn;
    }
}
