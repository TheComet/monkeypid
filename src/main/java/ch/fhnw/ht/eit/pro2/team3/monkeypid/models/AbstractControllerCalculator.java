package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.ControllerCalculatorListener;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class AbstractControllerCalculator implements Runnable {

    private ArrayList<ControllerCalculatorListener> listeners = new ArrayList<>();
    protected Plant plant = null;
    protected double parasiticTimeConstantFactor = 0.1;
    private AbstractController controller;

    // stores where the calculated controller will be inserted into the table
    private int tableRowIndex = -1; // see issue #29

    public AbstractControllerCalculator(Plant plant) {
        setPlant(plant);
    }

    protected double beautifyTpSoNiklausIsHappy(double value) {
        DecimalFormat f = new DecimalFormat("###.##");
        return Double.parseDouble(f.format(value));
    }

    protected abstract AbstractController calculate();

    @Override
    public final void run() {
        controller = calculate();
        controller.setColor(getColor());
        notifyCalculationComplete();
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public void setParasiticTimeConstantFactor(double parasiticTimeConstantFactor) {
        this.parasiticTimeConstantFactor = parasiticTimeConstantFactor;
    }

    public final AbstractController getController() {
        return controller;
    }

    public final void setTableRowIndex(int index) {
        tableRowIndex = index;
    }

    public final int getTableRowIndex() {
        return tableRowIndex;
    }

    public final void registerListener(ControllerCalculatorListener listener) {
        listeners.add(listener);
    }

    public final void unregisterListener(ControllerCalculatorListener listener) {
        listeners.remove(listener);
    }

    private synchronized void notifyCalculationComplete() {
        for (ControllerCalculatorListener listener : listeners) {
            listener.onControllerCalculationComplete(this);
        }
    }

    public abstract String getName();

    public abstract Color getColor();
}