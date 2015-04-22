package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ControlPath;

public interface RegulatorCalculator {
    public Regulator calculate(ControlPath path);
}