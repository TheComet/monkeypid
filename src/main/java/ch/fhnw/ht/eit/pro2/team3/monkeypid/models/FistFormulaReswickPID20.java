package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Regulator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.RegulatorCalculator;

public class FistFormulaReswickPID20 implements RegulatorCalculator {

    @Override
    public Regulator calculate(ControlPath path) {
        return new PIDRegulator(
                0.7 * path.getTg() / (path.getKs() * path.getTu()),
                4.0 * path.getTu(),
                0.42 * path.getTu()
        );
    }
}