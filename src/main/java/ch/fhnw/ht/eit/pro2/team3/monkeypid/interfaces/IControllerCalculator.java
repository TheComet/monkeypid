package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;

public interface IControllerCalculator extends Runnable {
    void calculate();
    void setPlant(Plant plant);
    void setParasiticTimeConstantFactor(double parasiticTimeConstantFactor);
    IController getController();
    String getName();
    void registerListener(IControllerCalculatorListener listener);
    void unregisterListener(IControllerCalculatorListener listener);
}