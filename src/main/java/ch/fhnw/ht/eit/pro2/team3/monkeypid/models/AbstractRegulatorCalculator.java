package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Regulator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.RegulatorCalculator;

public abstract class AbstractRegulatorCalculator implements RegulatorCalculator{

    protected Regulator regulator;

    public Regulator getRegulator() {
        return this.regulator;
    }
}