package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Regulator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.RegulatorCalculator;

public class FistFormulaRosenbergPI implements RegulatorCalculator {

    @Override
    public Regulator calculate(ControlPath path) {
        return new PIRegulator(
                0.91 * path.getTg() / (path.getKs() * path.getTu()),
                3.3 * path.getTu()
        );
    }
}