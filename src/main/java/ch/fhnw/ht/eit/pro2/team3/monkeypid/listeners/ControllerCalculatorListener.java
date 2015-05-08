package ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.AbstractControllerCalculator;

/**
 * Allows classes to register as listeners to the ControllerCalculator class.
 */
public interface ControllerCalculatorListener {

    /**
     * This is called when the calculator has completed calculating a controller for a given plant.
     * @param calculator The calculator.
     */
    void onControllerCalculationComplete(AbstractControllerCalculator calculator);
}