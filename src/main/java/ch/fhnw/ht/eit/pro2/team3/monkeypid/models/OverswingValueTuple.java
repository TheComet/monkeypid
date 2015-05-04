package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class OverswingValueTuple {
    private double phiSt;
    private String overswing;

    public OverswingValueTuple(double phiSt, String overswing) {
        this.phiSt = phiSt;
        this.overswing = overswing;
    }

    public String percent() {
        return this.overswing;
    }

    public double angle() {
        return phiSt;
    }
}