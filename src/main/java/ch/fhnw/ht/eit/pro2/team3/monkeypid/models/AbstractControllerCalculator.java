package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;

import java.util.ArrayList;

public abstract class AbstractControllerCalculator
        implements IControllerCalculator {

    private ArrayList<IControllerCalculatorListener> listeners = new ArrayList<>();
    protected Plant plant = null;
    protected IController controller;

    public AbstractControllerCalculator(Plant plant) {
        setPlant(plant);
    }

    @Override
    public void run() {
        calculate();
        notifyNewController();
    }

    @Override
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    @Override
    public IController getController() {
        return controller;
    }

    @Override
    public void registerListener(IControllerCalculatorListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(IControllerCalculatorListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyNewController() {
        for(IControllerCalculatorListener listener : listeners) {
            listener.notifyNewController(controller);
        }
    }
}