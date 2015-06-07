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
 * When a simulation is initiated, a list of controller calculators is generated
 * based on what has been configured. These are executed in parallel, and each
 * produce a controller to fit the configured plant. Once that is done, the list
 * of resulting controllers are used to create a closed loop system with the
 * plant, and a step response is calculated. When all step responses complete,
 * the end of the simulation is notified.
 *
 * @author Alex Murray
 */
public class Model implements ICalculationCycleListener {
	/**
	 * Is thrown when an unknown regulator string is passed to
	 * setRegulatorType().
	 */
	public class UnknownRegulatorTypeException extends RuntimeException {
		UnknownRegulatorTypeException(String message) {
			super(message);
		}
	}

	/**
	 * Is thrown when the user tries to simulate PID calculations with an order
	 * of n=2. See issue #31.
	 */
	public class InvalidPlantForPIDSimulationException extends RuntimeException {
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
		public final void unregisterListener(ICalculationCycleListener listener) {
			listeners.remove(listener);
		}

		private void notifyStepResponseCalculationComplete() {
			// depending on whether this is the first calculation or not, notify the appropriate methods
			if(firstCalculation) {
				for (ICalculationCycleListener listener : listeners) {
					listener.onNewCalculationCycleComplete(closedLoop);
				}
			} else {
				for(ICalculationCycleListener listener : listeners) {
					listener.onUpdateCalculationCycleComplete(closedLoop);
				}
			}
		}

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

	// list of closed loops to be displayed on the graph
	ClosedLoop selectedCalculation = null;
	private ArrayList<ClosedLoop> closedLoops = new ArrayList<>();

	// list of Model listeners
	private ArrayList<IModelListener> listeners = new ArrayList<>();

	// list, which represents the visibility of the stepResponse of a closedLoop
	// in the Graph, default all visible
	private boolean[] curvesVisible = { true, true, true, true, true, true, true };

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
	 * 
	 * @param regulatorTypeName
	 *			A string containing either "I", "IP", or "PID".
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
	 *									absolute, not in percent.
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
		ArrayList<CalculationCycle> calculators = getCalculators();
		notifySimulationBegin(calculators.size());

		// dispatch all calculators
		ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		calculators.forEach(threadPool::submit);
		threadPool.shutdown();

		try {
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			notifySimulationComplete();
		}
	}

	/**
	 * Updates the last caluclated Zellweger-Controller.
	 * Uses the parameter phaseInflectionOffset as Offset for the phaseInflection
	 * of the last Zellweger-Controller. This method calculates also the step-response.
	 * The table and the graph of the GUI are automatically updated by the given 
	 * notifiers.
	 * This method calculates only a new zellwegerMethod, if no other Calculation
	 * is active and at least one Zellweger-Calculation is done.
	 * @param angleOfInflectionOffset // TODO
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
	 * 
	 * @param name
	 *			The unique name of the calculation to select.
	 */
	public final void selectCalculation(String name) {
		for (ClosedLoop loop : closedLoops) {
			if (loop.getName().equals(name)) {
				selectedCalculation = loop;
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
		curvesVisible[selectedCalculation.getTableRowIndex()] = false;
		notifyHideCalculation(selectedCalculation);
	}

	/**
	 * Shows the currently selected calculation. This sends an
	 * onShowCalculation() event to all model listeners.
	 */
	public final void showSelectedCalculation() {
		if (selectedCalculation == null) {
			return;
		}
		curvesVisible[selectedCalculation.getTableRowIndex()] = true;
		notifyShowCalculation(selectedCalculation);
	}

	/**
	 * Registers a model listener.
	 * 
	 * @param listener
	 *			An object implementing IModelListener.
	 */
	public final void registerListener(IModelListener listener) {
		listeners.add(listener);
	}

	/**
	 * Unregisters a model listener.
	 * 
	 * @param listener
	 *			An object that previously called registerListener.
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

		// notify all listeners that we're removing all closed loops
		closedLoops.forEach(this::notifyRemoveCalculation);
		closedLoops = new ArrayList<>();
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
	 * Creates a list of controller calculators, filtered to the currently
	 * selected controller type (I, PI, or PID). The calculators are configured
	 * with the currently set plant, and the Zellweger calculators are
	 * configured with the currently selected phase margin. Additionally, each
	 * calculator is given a unique index. This is so their order isn't
	 * undefined, even when they are computed in parallel.
	 * 
	 * @return An ArrayList of calculators. See issue #29
	 */
	private ArrayList<CalculationCycle> getCalculators() {

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

		// Set parasitic time constant.
		// Set table row indices of calculator - See issue #29.
		int i = 0;
		for (CalculationCycle calculator : calculators) {
			calculator.getControllerCalculator().setParasiticTimeConstantFactor(parasiticTimeConstantFactor);
			calculator.getControllerCalculator().setTableRowIndex(i);
			calculator.setTargetOvershoot(overshoot);
			calculator.registerListener(this);
			i++;
		}

		return calculators;
	}

	/**
	 * Call this to notify that a new completed calculation was added to the
	 * internal list.
	 * @param loop The closed loop that was added.
	 */
	private void notifyAddCalculation(ClosedLoop loop) {
		for (IModelListener listener : listeners) {
			listener.onAddCalculation(loop, curvesVisible[loop.getTableRowIndex()]);
		}
	}

	/**
	 * Call this to notify that an existing closed loop object was updated.
	 * @param loop The closed loop that was updated.
	 */
	private void notifyUpdateCalculation(ClosedLoop loop) {
		for(IModelListener listener : listeners) {
			listener.onUpdateCalculation(loop);
		}
	}

	/**
	 * Call this to notify that a calculation was removed from the internal
	 * list.
	 * @param loop The closed loop that was removed.
	 */
	private void notifyRemoveCalculation(ClosedLoop loop) {
		for (IModelListener listener : listeners) {
			listener.onRemoveCalculation(loop);
		}
	}

	/**
	 * Call this to notify that a new simulation is about to begin.
	 * @param numberOfCalculators The number of calculators that will be executed.
	 */
	private void notifySimulationBegin(int numberOfCalculators) {
		for (IModelListener listener : listeners) {
			listener.onSimulationBegin(numberOfCalculators);
		}
	}

	/**
	 * Call this to notify that a simulation has completed.
	 */
	private void notifySimulationComplete() {
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
	 * show the order of the new set  Plant in the GUI
	 * @param plant
	 */
	private void notifySetPlant(Plant plant) {
		for (IModelListener listener : listeners) {
			listener.onSetPlant(plant);
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Derived methods
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Called when a step response calculation completes.
	 * @param closedLoop The closed loop object holding the results of the step response.
	 */
	@Override
	public final synchronized void onNewCalculationCycleComplete(ClosedLoop closedLoop) {
		closedLoops.add(closedLoop);
		notifyAddCalculation(closedLoop);
	}

	@Override
	public void onUpdateCalculationCycleComplete(ClosedLoop closedLoop) {
		notifyUpdateCalculation(closedLoop);
	}
}
