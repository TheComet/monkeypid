package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.ICalculationCycleListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This is the heart of all data related operations. All simulations,
 * calculations and events are orchestrated here.
 *
 * First, some terminology: - A *calculation* is the process of calculating a
 * controller, creating a closed loop, and calculating the step response of that
 * closed loop. There are usually multiple calculations in every simulation. - A
 * *simulation* is the process of executing all calculations.
 *
 * There are multiple settings which can be configured for simulations: - The
 * plant can be set with Tu, Tg, and Ks. - The controller type can be set to
 * either I, PI, or PID. - The parasitic time constant factor can be set. This
 * influences how Tp is calculated. - The phase margin for Zellweger
 * calculations can be set.
 *
 * When a simulation is initiated, a list of CalculationCycle objects is generated,
 * based on what has been configured. These are executed in parallel, and each
 * produce a controller to fit the configured plant. Once that is done, the list
 * of resulting controllers are used to create a closed loop system with the
 * plant, and a step response is calculated. When all step responses complete,
 * the end of the simulation is notified.
 *
 * The model will store the current zellweger CalculationCycle object so it can be
 * recalculated later using the slider in the GUI.
 *
 * @author Alex Murray
 */
public class Model implements ICalculationCycleListener {

	/**
	 * Is thrown when an unknown regulator string is passed to
	 * setRegulatorType().
	 */
	public class UnknownRegulatorTypeException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		UnknownRegulatorTypeException(String message) {
			super(message);
		}
	}

	/**
	 * Is thrown when the user tries to simulate PID calculations with an order
	 * of n=2. See issue #31.
	 */
	public class InvalidPlantForPIDSimulationException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		InvalidPlantForPIDSimulationException(String message) {
			super(message);
		}
	}

	/**
	 * Handles the entire process of calculating a controller, constructing a
	 * closed loop, and calculating the step response, as well as applying
	 * iterative approximation for the overshoot.
	 */
	private class CalculationCycle implements Runnable {
		private AbstractControllerCalculator controllerCalculator;
		private ClosedLoop closedLoop;
		private double targetOvershoot;
		private ArrayList<ICalculationCycleListener> listeners = new ArrayList<>();
		private boolean firstCalculation = true;
		private boolean isVisible = true;

		/**
		 * Constructs a new calculation cycle from a given controller calculator.
		 * @param controllerCalculator The controller calculator to use.
		 */
		CalculationCycle(AbstractControllerCalculator controllerCalculator) {
			this.controllerCalculator = controllerCalculator;
			this.closedLoop = new ClosedLoop(plant, null);
		}

		/**
		 * Gets the controller calculator.
		 * @return The controller calculator.
		 */
		public AbstractControllerCalculator getControllerCalculator() {
			return controllerCalculator;
		}

		/**
		 * Sets the target overshoot to approximate using the iterative
		 * approximation method.
		 * @param overshoot The overshoot in percent.
		 */
		public void setTargetOvershoot(double overshoot) {
			if (overshoot < 0.5)
				overshoot = 0.5; 	// overshoot of 0% produces weird results,
									// round up to the maximum tolerant value
			this.targetOvershoot = overshoot;
		}

		/**
		 * Sets this calculation to be visible.
		 */
		public void show() {
			isVisible = true;
			notifyShowCalculation(closedLoop);
		}

		/**
		 * Hides this calculation.
		 */
		public void hide() {
			isVisible = false;
			notifyHideCalculation(closedLoop);
		}

		/**
		 * Sets the visibility flag. This is used to copy the settings of the old calculators to the new ones.
		 * @param flag True or false.
		 */
		public void setVisibilityFlag(boolean flag) {
			isVisible = flag;
		}

		/**
		 * Returns if this calculation is visible or not.
		 * @return True or false.
		 */
		public boolean isVisible() {
			return isVisible;
		}

		/**
		 * Gets the closed loop object that was last calculated (or still needs to be calculated.
		 * @return The closed loop object.
		 */
		public ClosedLoop getClosedLoop() {
			return closedLoop;
		}

		/**
		 * Register as a listener to this class in order to receive notifications.
		 * @param listener The object to register.
		 */
		public final void registerListener(ICalculationCycleListener listener) {
			listeners.add(listener);
		}

		/**
		 * Unregister as a listener from this class.
		 * @param listener The object to unregister.
		 */
		@SuppressWarnings("unused")
		public final void unregisterListener(ICalculationCycleListener listener) {
			listeners.remove(listener);
		}

		/**
		 * Depending on whether this is the first calculation or not, notify the appropriate methods
		 */
		private void notifyStepResponseCalculationComplete() {
			if(firstCalculation) {
				for (ICalculationCycleListener listener : listeners) {
					listener.onNewCalculationCycleComplete(closedLoop, isVisible);
				}
			} else {
				for(ICalculationCycleListener listener : listeners) {
					listener.onUpdateCalculationCycleComplete(closedLoop);
				}
			}
		}

		/**
		 * (Re)calculates and returns a new controller.
		 * @return The resulting controller object.
		 */
		private AbstractController calculateController() {
			controllerCalculator.run();
			return controllerCalculator.getController();
		}

		/**
		 * Executes the calculation cycle.
		 */
		@Override
		public void run() {

			// first step, calculate the controller
			AbstractController controller = calculateController();

			// prepare/update the closed loop object
			closedLoop.setTableRowIndex(controllerCalculator.getTableRowIndex());
			closedLoop.setPlantAndController(plant, controller);

			// if maxKr is greater than minKr, it means we have a window to use
			// for iterative approximation
			if (controller.getMaxKr() > controller.getMinKr()) {

				// calculate initial step response beginning in the middle
				double topKr = controller.getMaxKr();
				double bottomKr = controller.getMinKr();
				double actualKr = (topKr + bottomKr) / 2.0;
				controller.setKr(actualKr);
				closedLoop.setPlantAndController(plant, controller);
				closedLoop.calculateStepResponse();

				// approximate the overshoot using a binary search by adjusting Kr
				for (int i = 0; i < 9; i++) {
					if (closedLoop.getOvershoot() > targetOvershoot) {
						topKr = actualKr;
						actualKr = (topKr + bottomKr) / 2.0;
					} else {
						bottomKr = actualKr;
						actualKr = (topKr + bottomKr) / 2.0;
					}

					// re-calculate step response with the next value of Kr
					controller.setKr(actualKr);
					closedLoop.setPlantAndController(plant, controller);
					closedLoop.calculateStepResponse();
				}
			} else {
				// no approximation, just calculate as usual.
				closedLoop.calculateStepResponse();
			}

			// done - notify
			notifyStepResponseCalculationComplete();

			// set first calculation flag to false, now that we've calculated at least once
			firstCalculation = false;
		}
	}

	// have the model own the sani curves, so they don't have to be reloaded
	// from disk every time a new calculation is performed.
	private SaniCurves sani = new SaniCurves();

	// current plant to use for controller calculations
	private Plant plant = null;

	// phase margin for Zellweger
	private double overshoot;

	// regulator types to calculate when user simulates
	private enum RegulatorType {
		I, PI, PID
	}

	private RegulatorType regulatorType;
	private double parasiticTimeConstantFactor;
	CalculationCycle selectedCalculation = null;

	// list of closed loops to be displayed on the graph
	private ArrayList<CalculationCycle> calculationCycles = null;

	// list of Model listeners
	private ArrayList<IModelListener> listeners = new ArrayList<>();

	// Stores the active zellweger calculation cycle. This is re-used when the user adjusts
	// the angle of inflection slider in the GUI to re-calculate the zellweger step response.
	private CalculationCycle currentZellwegerCalculationCycle = null;

	// -----------------------------------------------------------------------------------------------------------------
	// Public methods
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Updates the plant to be used for all calculations.
	 * @param tu Plant parameter Tu.
	 * @param tg Plant parameter Ks.
	 * @param ks Plant parameter Ks.
	 */
	public final void setPlant(double tu, double tg, double ks) {
		this.plant = new Plant(tu, tg, ks, sani);
		notifySetPlant(plant);
	}

	/**
	 * Select the regulator types to be simulated. We currently support I, PI,
	 * and PID type regulators. When the simulation is initiated, only
	 * calculators matching the selected type will be calculated.
	 * @param regulatorTypeName A string containing either "I", "IP", or "PID".
	 */
	public final void setRegulatorType(String regulatorTypeName)
			throws UnknownRegulatorTypeException {
		switch (regulatorTypeName) {
		case "I":
			regulatorType = RegulatorType.I;
			break;
		case "PI":
			regulatorType = RegulatorType.PI;
			break;
		case "PID":
			regulatorType = RegulatorType.PID;
			break;
		default:
			throw new UnknownRegulatorTypeException("Unknown regulator \"" + regulatorTypeName + "\"");
		}
	}

	/**
	 * This is used to calculate Tp. Updates the parasitic time constant factor
	 * to use in all simulations. Zellweger methods will multiply this with Tvk
	 * to get Tp (Tp = factor * Tvk). Fist formulas will multiply it with Tv to
	 * get Tp (Tp = factor * Tv)
	 * @param parasiticTimeConstantFactor The factor to use. The value should be
	 *                                    absolute, not in percent.
	 */
	public final void setParasiticTimeConstantFactor(
			double parasiticTimeConstantFactor) {
		this.parasiticTimeConstantFactor = parasiticTimeConstantFactor;
	}

	/**
	 * Sets the overshoot to use for zellweger based calculations.
	 * @param overshoot The overshoot in percent.
	 */
	public final void setOvershoot(double overshoot) {
		this.overshoot = overshoot;
	}

	/**
	 * Clears, then calculates all controllers and their closed loop step
	 * responses. If a simulation is still active, this method will not do
	 * anything.
	 */
	public final void simulateAll() {

		// see issue #31 - disallow orders n=2 for PID simulations
		validatePlantIsPIDCompliant();

		// clean up from last simulation
		clearSimulation();

		// get all calculators and notify simulation begin
		updateCalculators();
		notifySimulationBegin(calculationCycles.size());

		// dispatch all calculators
		calculationCycles.forEach(CalculationCycle::run);
		/*
		ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		calculationCycles.forEach(threadPool::submit);
		threadPool.shutdown();

		try {
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			notifySimulationComplete();
		}*/
	}

	/**
	 * Recalculates the last Zellweger calculator with an updated angle of inflection.
	 * This also recalculates the step response and notifies all listeners, updating
	 * the plot and output table.
	 * @param angleOfInflectionOffset This is used as an offset for the currently fixed
	 *                                angleOfInflection parameter of the last Zellweger
	 *                                controller.
	 */
	public void updateZellweger(int angleOfInflectionOffset) {
		// No need to update if the user hasn't calculated anything yet.
		if (currentZellwegerCalculationCycle == null) {
			return;
		}

		// TODO if implementing async simulateAll(), add a check here again

		// update the parasitic time constant factor
		currentZellwegerCalculationCycle.getControllerCalculator()
				.setParasiticTimeConstantFactor(parasiticTimeConstantFactor);

		// update phase inflection offset - we know it's a zellweger so cast should be safe
		((AbstractZellweger)currentZellwegerCalculationCycle.getControllerCalculator())
				.setAngleOfInflectionOffset(angleOfInflectionOffset);

		// update the target overshoot
		currentZellwegerCalculationCycle.setTargetOvershoot(overshoot);
		
		//dispatch the calculator
		currentZellwegerCalculationCycle.run();
	}

	/**
	 * Selects an active calculation. After selecting a calculation, it's
	 * possible to manipulate it with other methods, such as
	 * hideSelectedCalculation() or showSelectedCalculation().
	 * @param name The unique name of the calculation to select.
	 */
	public final void selectCalculation(String name) {
		for (CalculationCycle cycle : calculationCycles) {
			if (cycle.getClosedLoop().getName().equals(name)) {
				selectedCalculation = cycle;
				return;
			}
		}
	}

	/**
	 * Hides the currently selected calculation. This sends an
	 * onHideCalculation() event to all model listeners. Saves the new
	 * visibility of the calculation i
	 */
	public final void hideSelectedCalculation() {
		if (selectedCalculation == null) {
			return;
		}
		selectedCalculation.hide();
	}

	/**
	 * Shows the currently selected calculation. This sends an
	 * onShowCalculation() event to all model listeners.
	 */
	public final void showSelectedCalculation() {
		if (selectedCalculation == null) {
			return;
		}
		selectedCalculation.show();
	}

	/**
	 * Registers a model listener.
	 * @param listener An object implementing IModelListener.
	 */
	public final void registerListener(IModelListener listener) {
		listeners.add(listener);
	}

	/**
	 * Unregisters a model listener.
	 * @param listener An object that previously called registerListener.
	 */
	public final void unregisterListener(IModelListener listener) {
		listeners.remove(listener);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Private methods
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Clears all existing calculations. The graph, table, and checkboxes will
	 * be cleared along with all closed loops. NOTE: This should only be called
	 * if there isn't an active simulation, because the calculations are
	 * threaded.
	 */
	private void clearSimulation() {

		// deselect calculation
		selectedCalculation = null;
		currentZellwegerCalculationCycle = null;

		// notify all listeners that we're removing all closed loops
		if(calculationCycles != null) {
			for (CalculationCycle cycle : calculationCycles) {
				notifyRemoveCalculation(cycle.getClosedLoop());
			}
		}
	}

	/**
	 * PID simulations with order n=2 don't make much sense, so throw an
	 * exception if that happens. Request from Peter Niklaus, see issue #31.
	 */
	private void validatePlantIsPIDCompliant() {
		// see issue #31 - disallow orders n=2 for PID simulations
		if (regulatorType == RegulatorType.PID) {
			double ratio = plant.getTu() / plant.getTg();
			if (sani.lookupOrder(ratio) == 2) {
				throw new InvalidPlantForPIDSimulationException("Die Strecke ist n=2. Eine PID Simulation ist Sinnlos");
			}
		}
	}

	/**
	 * Creates a list of CalculationCycle objects, filtered to the currently
	 * selected controller type (I, PI, or PID). The calculators are configured
	 * with the currently set plant, and the Zellweger calculators are
	 * configured with the currently selected phase margin. Additionally, each
	 * calculator is given a unique index, which corresponds to the index they will
	 * end up in in the result table. This is so their order isn't
	 * undefined, even when they are computed in parallel (See issue #29).
	 */
	private void updateCalculators() {

		ArrayList<CalculationCycle> calculators = new ArrayList<>();

		// generate a list of all calculators matching the currently selected
		// controller type
		// NOTE: The Zellweger cycle is the only one that has to be stored for later use
		switch (regulatorType) {
		case PID:
			currentZellwegerCalculationCycle = new CalculationCycle(new ZellwegerPID(plant, overshoot));
			calculators.add(new CalculationCycle(new FistFormulaOppeltPID(plant)));
			calculators.add(new CalculationCycle(new FistFormulaReswickStoerPID0(plant)));
			calculators.add(new CalculationCycle(new FistFormulaReswickStoerPID20(plant)));
			calculators.add(new CalculationCycle(new FistFormulaReswickFuehrungPID0(plant)));
			calculators.add(new CalculationCycle(new FistFormulaReswickFuehrungPID20(plant)));
			calculators.add(new CalculationCycle(new FistFormulaRosenbergPID(plant)));
			break;

		case PI:
			currentZellwegerCalculationCycle = new CalculationCycle(new ZellwegerPI(plant, overshoot));
			calculators.add(new CalculationCycle(new FistFormulaOppeltPI(plant)));
			calculators.add(new CalculationCycle(new FistFormulaReswickStoerPI0(plant)));
			calculators.add(new CalculationCycle(new FistFormulaReswickStoerPI20(plant)));
			calculators.add(new CalculationCycle(new FistFormulaReswickFuehrungPI0(plant)));
			calculators.add(new CalculationCycle(new FistFormulaReswickFuehrungPI20(plant)));
			calculators.add(new CalculationCycle(new FistFormulaRosenbergPI(plant)));
			break;

		case I:
			currentZellwegerCalculationCycle = new CalculationCycle(new ZellwegerI(plant));

		default:
			break;
		}

		// add the zellweger
		calculators.add(currentZellwegerCalculationCycle);

		// Configure the calculators. This includes:
		// Setting the parasitic time constant factor.
		// Set table row indices of calculator - See issue #29.
		// Set the target overshoot for iterative approximation.
		// Register as a listener.
		int i = 0;
		for (CalculationCycle calculator : calculators) {
			calculator.getControllerCalculator().setParasiticTimeConstantFactor(parasiticTimeConstantFactor);
			calculator.getControllerCalculator().setTableRowIndex(i);
			calculator.setTargetOvershoot(overshoot);
			calculator.registerListener(this);
			i++;
		}

		// in order for the calculators to remember whether they are visible or not, match and copy
		// the visibility flags from the old calculators and insert them into the new calculators.
		if(calculationCycles != null) {
			for (CalculationCycle oldCC : calculationCycles) {
				for (CalculationCycle newCC : calculators) {
					if (oldCC.getControllerCalculator().getName().equals(newCC.getControllerCalculator().getName())) {
						newCC.setVisibilityFlag(oldCC.isVisible());
						break;
					}
				}
			}
		}

		calculationCycles = calculators;
	}

	/**
	 * Call this to notify that a new completed calculation was added to the
	 * internal list.
	 * @param loop The closed loop that was added.
	 * @param visible Whether or not the calculation should be visible so the GUI knows to show or hide it.
	 */
	private synchronized void notifyAddCalculation(ClosedLoop loop, boolean visible) {
		for (IModelListener listener : listeners) {
			listener.onAddCalculation(loop, visible);
		}
	}

	/**
	 * Call this to notify that an existing closed loop object was updated.
	 * @param loop The closed loop that was updated.
	 */
	private synchronized void notifyUpdateCalculation(ClosedLoop loop) {
		for(IModelListener listener : listeners) {
			listener.onUpdateCalculation(loop);
		}
	}

	/**
	 * Call this to notify that a calculation was removed from the internal
	 * list.
	 * @param loop The closed loop that was removed.
	 */
	private synchronized void notifyRemoveCalculation(ClosedLoop loop) {
		for (IModelListener listener : listeners) {
			listener.onRemoveCalculation(loop);
		}
	}

	/**
	 * Call this to notify that a new simulation is about to begin.
	 * @param numberOfCalculators The number of calculators that will be executed.
	 */
	private synchronized void notifySimulationBegin(int numberOfCalculators) {
		for (IModelListener listener : listeners) {
			listener.onSimulationBegin(numberOfCalculators);
		}
	}

	/**
	 * Call this to notify that a simulation has completed.
	 */
	private synchronized void notifySimulationComplete() {
		listeners.forEach(IModelListener::onSimulationComplete);
	}

	/**
	 * Call this to notify that a calculation was hidden. This should cause the
	 * curve in the plot to be hidden.
	 * @param closedLoop The closed loop being hidden.
	 */
	private void notifyHideCalculation(ClosedLoop closedLoop) {
		for (IModelListener listener : listeners) {
			listener.onHideCalculation(closedLoop);
		}
	}

	/**
	 * Call this to notify that a calculation was made visible. This should
	 * cause the curve in the plot to be made visible.
	 * 
	 * @param closedLoop
	 *			The closed loop to make visible.
	 */
	private void notifyShowCalculation(ClosedLoop closedLoop) {
		for (IModelListener listener : listeners) {
			listener.onShowCalculation(closedLoop);
		}
	}

	/**
	 * Call this to notify that a Plant has been set. This should cause the inputPanel to
	 * show the order of the new set plant in the GUI
	 * @param plant The plant being set.
	 */
	private void notifySetPlant(Plant plant) {
		for (IModelListener listener : listeners) {
			listener.onNewPlant(plant);
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Derived methods
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Called when a step response calculation completes.
	 * @param closedLoop The closed loop object holding the results of the step response.
	 * @param visible Whether or not this calculation is visible, so the GUI knows to show or hide it.
	 */
	@Override
	public final synchronized void onNewCalculationCycleComplete(ClosedLoop closedLoop, boolean visible) {
		notifyAddCalculation(closedLoop, visible);
	}

	/**
	 * Called when a step response calculation has been recalculated.
	 * @param closedLoop The closed loop object that completed the step response calculation.
	 */
	@Override
	public final synchronized void onUpdateCalculationCycleComplete(ClosedLoop closedLoop) {
		notifyUpdateCalculation(closedLoop);
	}
}
