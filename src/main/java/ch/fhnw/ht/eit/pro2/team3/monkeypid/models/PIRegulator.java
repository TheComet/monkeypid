package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class PIRegulator extends AbstractRegulator {

    private double kr = 0.0;
    private double tn = 0.0;

    public PIRegulator(double kr, double tn) {
        setKr(kr);
        setTn(tn);
    }

    @Override
    public void fillTable() {
        // TODO fill the table with
    }

    @Override
    public PIRegulator clone() throws CloneNotSupportedException {
        return (PIRegulator)super.clone();
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
