package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class OverswingValue {
    private double phiSt;
    private String overswing;

    public OverswingValue(double phiSt, String overswing) {
        this.phiSt = phiSt;
        this.overswing = overswing;
    }

    public String asString() {
        return this.overswing;
    }

    public double asAngle() {
        return phiSt;
    }
}