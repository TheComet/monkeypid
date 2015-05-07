package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;

public interface IControllerCalculator extends Runnable {
    public void calculate();
    public void setPlant(Plant plant);
    public IController getController();
    public String getName();
    public void registerListener(IControllerCalculatorListener listener);
    public void unregisterListener(IControllerCalculatorListener listener);
}