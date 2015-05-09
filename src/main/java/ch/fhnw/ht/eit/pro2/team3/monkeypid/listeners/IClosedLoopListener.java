package ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;

/**
 * Allows classes to register as listeners to the ClosedLoop class.
 * @author Alex Murray
 */
public interface IClosedLoopListener {

    /**
     * This is called when the closed loop finishes calculating the step response.
     * @param closedLoop The closed loop object that completed the step response calculation.
     */
    void onStepResponseCalculationComplete(ClosedLoop closedLoop);
}