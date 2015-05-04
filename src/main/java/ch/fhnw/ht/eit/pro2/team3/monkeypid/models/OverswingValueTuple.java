package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class OverswingValueTuple {
    private double phaseMargin;
    private String overswing;

    public OverswingValueTuple(double phaseMargin, String overswing) {
        this.phaseMargin = phaseMargin;
        this.overswing = overswing;
    }

    public String percent() {
        return this.overswing;
    }

    public double angle() {
        return phaseMargin;
    }
}