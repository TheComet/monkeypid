package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import javax.swing.table.DefaultTableModel;

public class PIController extends AbstractController {

    private double kr = 0.0;
    private double tn = 0.0;

    public PIController(String name, double kr, double tn) {
        super(name);
        setParameters(kr, tn);
    }

    @Override
    protected void calculateTransferFunction() {
        // Numerator and Denominator Poly of the pi-controller:
        // Kr (1 + 1/(s*Tn)) = Kr * (s*Tn + 1)/(s*Tn)
        //   Br = Kr*[Tn 1];
        //   Ar = [Tn 0];
        double[] numeratorCoefficients = new double[] {tn * kr, kr};
        double[] denominatorCoefficients = new double[] {tn, 0};
        setTransferFunction(
                new TransferFunction(numeratorCoefficients, denominatorCoefficients)
        );
    }

    @Override
    public synchronized void addToTable(DefaultTableModel table) {
        table.addRow(new String[]{
                getName(),
                Double.toString(getKr()),
                Double.toString(getTn())
        });
    }

    public void setParameters(double kr, double tn) {
        this.kr = kr;
        this.tn = tn;
        calculateTransferFunction();
    }

    public double getKr() {
        return kr;
    }

    public double getTn() {
        return tn;
    }
}
