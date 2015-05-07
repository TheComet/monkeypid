package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class AbstractControllerCalculator
        implements IControllerCalculator {

    private ArrayList<IControllerCalculatorListener> listeners = new ArrayList<>();
    protected Plant plant = null;
    protected double parasiticTimeConstantFactor = 0.1;
    private IController controller;

    public AbstractControllerCalculator(Plant plant) {
        setPlant(plant);
    }

    protected double beautifyTpSoNiklausIsHappy(double value) {
        DecimalFormat f = new DecimalFormat("###.##");
        return Double.parseDouble(f.format(value));
    }

    protected abstract IController calculate();

    @Override
    public final void run() {
        controller = calculate();
        controller.setColor(getColor());
        notifyCalculationComplete();
    }

    @Override
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    @Override
    public void setParasiticTimeConstantFactor(double parasiticTimeConstantFactor) {
        this.parasiticTimeConstantFactor = parasiticTimeConstantFactor;
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

    private synchronized void notifyCalculationComplete() {
        for (IControllerCalculatorListener listener : listeners) {
            listener.onControllerCalculationComplete(this);
        }
    }
}