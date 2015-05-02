package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;

public abstract class AbstractControllerCalculator implements IControllerCalculator {

    protected IController controller;

    public IController getController() {
        return this.controller;
    }
}