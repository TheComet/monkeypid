package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Regulator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.RegulatorCalculator;

public class FistFormulaOppeltPI extends AbstractRegulatorCalculator {

    @Override
    public void calculate(ControlPath path) {
        this.regulator = new PIRegulator(
                0.8 * path.getTg() / (path.getKs() * path.getTu()),
                3.0 * path.getTu()
        );
    }
}