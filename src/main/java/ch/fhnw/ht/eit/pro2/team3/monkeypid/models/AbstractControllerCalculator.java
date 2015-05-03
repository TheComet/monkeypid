package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;

public abstract class AbstractControllerCalculator implements IControllerCalculator {

    protected Plant plant = null;
    protected IController controller;

    public AbstractControllerCalculator(Plant plant) {
        setPlant(plant);
    }

    @Override
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    @Override
    public IController getController() {
        return controller;
    }
}