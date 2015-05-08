package ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.AbstractControllerCalculator;

public interface ControllerCalculatorListener {
    void onControllerCalculationComplete(AbstractControllerCalculator calculator);
}