package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

public class PIController implements IController {

    private double kr = 0.0;
    private double tn = 0.0;

    public PIController(double kr, double tn) {
        setKr(kr);
        setTn(tn);
    }

    @Override
    public void fillTable() {
        // TODO fill the table with stuff -- do we even need this?
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
