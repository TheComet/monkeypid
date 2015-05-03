package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class PIDController extends AbstractController {

    private double kr = 0.0;
    private double tn = 0.0;
    private double tv = 0.0;
    private double tp = 0.0;

    public PIDController(double tn, double tv, double kr, double tp) {
        setKr(kr);
        setTn(tn);
        setTv(tv);
        setTp(tp);
    }

    @Override
    public void fillTable() {
        // TODO fill the table with parameters
    }

    @Override
    public PIDController clone() throws CloneNotSupportedException {
        return (PIDController)super.clone();
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

    public double getKr() {
        return kr;
    }

    public void setKr(double kr) {
        this.kr = kr;
    }

    public double getTp() {
        return tp;
    }

    public void setTp(double tp) {
        this.tp = tp;
    }
}
