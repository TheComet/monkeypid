package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;

import java.util.Observable;

public abstract class AbstractControllerCalculator
        extends Observable
        implements IControllerCalculator {

    protected Plant plant = null;
    protected IController controller;

    public AbstractControllerCalculator(Plant plant) {
        setPlant(plant);
    }

    @Override
    public void run() {
        calculate();
        setChanged();
        notifyObservers();
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