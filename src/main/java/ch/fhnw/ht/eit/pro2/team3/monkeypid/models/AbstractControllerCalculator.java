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
    public final void run() {
        calculate();
        notifyCalculationComplete();
    }

    @Override
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    @Override
    public final IController getController() {
        return controller;
    }

    @Override
    public final void registerListener(IControllerCalculatorListener listener) {
        listeners.add(listener);
    }

    @Override
    public final void unregisterListener(IControllerCalculatorListener listener) {
        listeners.remove(listener);
    }

    private void notifyCalculationComplete() {
        for(IControllerCalculatorListener listener : listeners) {
            listener.onControllerCalculationComplete(this);
        }
    }
}