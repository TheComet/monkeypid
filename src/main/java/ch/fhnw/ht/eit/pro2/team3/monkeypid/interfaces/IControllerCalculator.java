package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;

import java.awt.*;

public interface IControllerCalculator extends Runnable {
    void setPlant(Plant plant);
    void setParasiticTimeConstantFactor(double parasiticTimeConstantFactor);
    IController getController();
    String getName();
    Color getColor();
    void registerListener(IControllerCalculatorListener listener);
    void unregisterListener(IControllerCalculatorListener listener);
}