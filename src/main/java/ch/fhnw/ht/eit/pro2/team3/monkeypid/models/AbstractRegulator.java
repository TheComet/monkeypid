package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Regulator;

public abstract class AbstractRegulator implements Regulator, Cloneable {
    protected double tp;

    @Override
    public void setParasiticTimeConstant(double tp) {
        this.tp = tp;
    }
}