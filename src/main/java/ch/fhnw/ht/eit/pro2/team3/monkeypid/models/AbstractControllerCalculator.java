package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.ControllerCalculator;

public abstract class AbstractControllerCalculator implements ControllerCalculator {

    protected Controller controller;

    public Controller getController() {
        return this.controller;
    }
}