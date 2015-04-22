package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IPlant;

public abstract class AbstractPlant implements IPlant {

    protected ControllerParameters parameters = new ControllerParameters();

    @Override
    public ControllerParameters getParameters() {
        return this.parameters;
    }
}
