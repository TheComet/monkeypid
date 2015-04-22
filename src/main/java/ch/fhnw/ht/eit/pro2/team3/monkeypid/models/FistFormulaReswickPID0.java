package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Regulator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.RegulatorCalculator;

public class FistFormulaReswickPID0 extends AbstractRegulatorCalculator {

    @Override
    public void calculate(ControlPath path) {
        this.regulator = new PIRegulator(
                path.getTg() / (path.getKs() * path.getTu()),
                path.getTu() * 4
        );
    }
}