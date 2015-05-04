package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import javax.swing.table.DefaultTableModel;

public class PIController extends AbstractController {

    private double kr = 0.0;
    private double tn = 0.0;

    public PIController(String name, double kr, double tn) {
        super(name);
        setKr(kr);
        setTn(tn);
    }

    @Override
    public void addToTable(DefaultTableModel table) {
        table.addRow(new String[] {
                getName(),
                Double.toString(getKr()),
                Double.toString(getTn())
        });
    }

    public double getKr() {
        return kr;
    }

    public void setKr(double kr) {
        this.kr = kr;
    }

    public double getTn() {
        return tn;
    }

    public void setTn(double tn) {
        this.tn = tn;
    }
}
