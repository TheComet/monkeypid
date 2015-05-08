package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;

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
    public String[] getTableRowString() {
        return new String[]{
                getName(),
                new DecimalFormat("0.000E0").format(getKr()),
                new DecimalFormat("0.000E0").format(getTn()),
                        "", // Tv  (PID only)     -- need to pad so overswing value has the correct offset in the table
                        ""  // Tp  (PID only)
        };
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
