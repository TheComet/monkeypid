package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import javax.swing.table.DefaultTableModel;

public class PIDController extends AbstractController {

    private double kr = 0.0;
    private double tn = 0.0;
    private double tv = 0.0;
    private double tp = 0.0;

    public PIDController(String name, double tn, double tv, double kr, double tp) {
        super(name);
        setParameters(tn, tv, kr, tp);
    }

    @Override
    protected void calculateTransferFunction() {
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

    @Override
    public synchronized void addToTable(DefaultTableModel table) {
        table.addRow(new String[] {
                getName(),
                Double.toString(getKr()),
                Double.toString(getTn()),
                Double.toString(getTv()),
                Double.toString(getTp())
        });
    }

    public void setParameters(double tn, double tv, double kr, double tp) {
        this.tn = tn;
        this.tv = tv;
        this.kr = kr;
        this.tp = tp;
        calculateTransferFunction();
    }

    public double getTn() {
        return tn;
    }

    public double getTv() {
        return tv;
    }

    public double getKr() {
        return kr;
    }

    public double getTp() {
        return tp;
    }
}
