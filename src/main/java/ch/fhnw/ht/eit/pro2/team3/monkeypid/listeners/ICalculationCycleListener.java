package ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;

/**
 * Allows classes to register as listeners to the CaclulationCycle class.
 * @author Alex Murray
 */
public interface ICalculationCycleListener {

	/**
	 * This is called when the calculation cycle finishes calculating a new step response.
	 * By "new" this means that this is the first time the step response has been calculated.
	 * @param closedLoop The closed loop object that completed the step response calculation.
	 */
	void onNewCalculationCycleComplete(ClosedLoop closedLoop, boolean visible);

	/**
	 * This is called when the calculation cycle finishes re-calculating an existing step response.
	 * @param closedLoop The closed loop object that completed the step response calculation.
	 */
	void onUpdateCalculationCycleComplete(ClosedLoop closedLoop);
}
