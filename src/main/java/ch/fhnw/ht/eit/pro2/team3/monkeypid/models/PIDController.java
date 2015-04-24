package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class PIDController extends AbstractController {

    private double kr = 0.0;
    private double tn = 0.0;
    private double tv = 0.0;

    public PIDController(double kr, double tn, double tv) {
        setKr(kr);
        setTn(tn);
        setTv(tv);
    }

    @Override
    public void fillTable() {
        // TODO fill the table with parameters
    }

    @Override
    public PIDController clone() throws CloneNotSupportedException {
        return (PIDController)super.clone();
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

    public double getTv() {
        return tv;
    }

    public void setTv(double tv) {
        this.tv = tv;
    }
}
