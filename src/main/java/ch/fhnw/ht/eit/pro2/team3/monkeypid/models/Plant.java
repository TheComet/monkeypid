package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class Plant {

    private double ks = 0.0;
    private double tu = 0.0;
    private double tg = 0.0;
    private double[] timeConstants;

    public Plant(double tu, double tg, double ks, SaniCurves sani) {
        this.ks = ks;
        this.tu = tu;
        this.tg = tg;
        timeConstants = sani.calculateTimeConstants(tu, tg);
    }

    public double getKs() {
        return this.ks;
    }

    public double getTu() {
        return this.tu;
    }

    public double getTg() {
        return this.tg;
    }

    public double[] getTimeConstants() {
        return this.timeConstants;
    }
}
