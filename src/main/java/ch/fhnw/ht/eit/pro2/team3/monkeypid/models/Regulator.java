package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.RegulatorInterface;

public abstract class Regulator implements RegulatorInterface {

    private ControllerParameters result = new ControllerParameters();

    @Override
    public void setOverSwing(double percent) {

    }

    @Override
    public ControllerParameters getResult() {
        return this.result;
    }
}