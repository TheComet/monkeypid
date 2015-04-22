package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IRegulator;

public abstract class AbstractRegulator implements IRegulator {

    protected ControllerParameters parameters = new ControllerParameters();

    @Override
    public ControllerParameters getParameters() {
        return this.parameters;
    }
}
