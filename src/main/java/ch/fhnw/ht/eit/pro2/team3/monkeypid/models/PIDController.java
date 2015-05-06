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
        
    }

    @Override
    public void addToTable(DefaultTableModel table) {
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
