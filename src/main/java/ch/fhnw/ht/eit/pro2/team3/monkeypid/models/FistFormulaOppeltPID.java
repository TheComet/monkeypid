package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Regulator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.RegulatorCalculator;

public class FistFormulaOppeltPID extends AbstractRegulatorCalculator {

    @Override
    public void calculate(ControlPath path) {
        this.regulator = new PIDRegulator(
                1.2 * path.getTg() / (path.getKs() * path.getTu()),
                2.0 * path.getTu(),
                0.42 * path.getTu()
        );
    }
}