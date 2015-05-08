package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IClosedLoopListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Model implements IControllerCalculatorListener, IClosedLoopListener {

    public class UnknownRegulatorTypeException extends RuntimeException {
        UnknownRegulatorTypeException(String message) {
            super(message);
        }
    }

    public class InvalidPlantForPIDSimulationException extends RuntimeException {
        InvalidPlantForPIDSimulationException(String message) {
            super(message);
        }
    }

    private ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    // have the model own the sani curves, so they don't have to be reloaded from
    // disk every time a new calculation is performed.
    private SaniCurves sani = new SaniCurves();

    // current plant to use for controller calculations
    private Plant plant = null;

    // phase margin for Zellweger
    private double phaseMargin = 0.0;

    // regulator types to calculate when user simulates
    private enum RegulatorType {
        PI,
        PID
    }
    private RegulatorType regulatorType;

    private double parasiticTimeConstantFactor;

    // list of closed loops to be displayed on the graph
    ClosedLoop selectedSimulation = null;
    private ArrayList<ClosedLoop> closedLoops = new ArrayList<>();

    // list of Model listeners
    private ArrayList<IModelListener> listeners = new ArrayList<>();

    /**
     * Select the regulator types to be simulated (I/PI/PID)
     * @param regulatorTypeName A string containing either "I", "IP", or "PID"
     */
    public void setRegulatorType(String regulatorTypeName) throws UnknownRegulatorTypeException {
        if(regulatorTypeName.compareTo("PI") == 0) {
            regulatorType = RegulatorType.PI;
        } else if(regulatorTypeName.compareTo("PID") == 0) {
            regulatorType = RegulatorType.PID;
        } else {
            throw new UnknownRegulatorTypeException("Unknown regulator \"" + regulatorTypeName + "\"");
        }
    }

    /**
     * Updates the plant to be used for all simulations.
     * @param tu Plant value Tu.
     * @param tg Plant value Ks.
     * @param ks Plant value Ks.
     */
    public void setPlant(double tu, double tg, double ks) {
        this.plant = new Plant(tu, tg, ks, sani);
    }

    /**
     * Updates the phase margin used in Zellweger-based calculations.
     * @param phaseMargin A positive angle in degrees, usually in the range of 45° and 76.3°
     */
    public void setPhaseMargin(double phaseMargin) {
        this.phaseMargin = phaseMargin;
    }

    /**
     * This is used to calculate Tp.
     * Updates the parasitic time constant factor to use in all simulations. Zellweger methods will multiply this
     * with Tvk to get Tp (Tp = factor * Tvk). Fist formulas will multiply it with Tv to get Tp (Tp = factor * Tv)
     * @param parasiticTimeConstantFactor The factor to use.
     */
    public void setParasiticTimeConstantFactor(double parasiticTimeConstantFactor) {
        this.parasiticTimeConstantFactor = parasiticTimeConstantFactor;
    }

    /**
     * Clears all existing simulations. The graph, table, and checkboxes will be cleared along with all closed loops.
     */
    private void clearSimulations() {

        // deselect all simulations
        selectedSimulation = null;

        // notify all listeners that we're removing all closed loops
        for(ClosedLoop loop : closedLoops) {
            for (IModelListener listener : listeners) {
                listener.onRemoveClosedLoop(loop);
            }
        }
        closedLoops = new ArrayList<>();
    }

    /**
     * Clears, then calculates all controllers and their closed loop step responses. If a simulation is still active,
     * this method will not do anything.
     */
    public void simulateAll() {

        // It's not thread safe to call this method while calculators are still active
        if(isSimulationActive()) {
            return;
        }

        // see issue #31 - disallow orders n=2 for PID simulations
        validatePlantIsPIDCompliant();

        // clean up from last simulation
        clearSimulations();

        // get all calculators and notify simulation begin
        ArrayList<IControllerCalculator> calculators = getControllerCalculators();
        notifySimulationBegin(calculators.size());

        // dispatch all calculators
        threadPool.submit(() -> dispatchControllerCalculators(calculators));
    }

    private void validatePlantIsPIDCompliant() {
        // see issue #31 - disallow orders n=2 for PID simulations
        if(regulatorType == RegulatorType.PID) {
            double ratio = plant.getTu() / plant.getTg();
            if(sani.lookupPower(ratio) == 2) {
                throw new InvalidPlantForPIDSimulationException("Current plant has order n=2. These aren't allowed for PID simulations");
            }
        }
    }

    /**
     * Returns true if a simulation is in progress.
     * @return True if a simulation is in progress, false if otherwise.
     */
    public boolean isSimulationActive() {
        int queued = threadPool.getQueue().size();
        int active = threadPool.getActiveCount();
        return (queued + active > 0);
    }

    public void selectSimulation(String name) {
        for(ClosedLoop loop : closedLoops) {
            if(loop.getName().compareTo(name) == 0) {
                selectedSimulation = loop;
                return;
            }
        }
    }

    public void hideSelectedSimulation() {
        if(selectedSimulation == null) {
            return;
        }

        notifyHideSimulation(selectedSimulation);
    }

    public void showSelectedSimulation() {
        if(selectedSimulation == null) {
            return;
        }

        notifyShowSimulation(selectedSimulation);
    }

    private ArrayList<IControllerCalculator> getControllerCalculators() {
        ArrayList<IControllerCalculator> calculators = new ArrayList<>();

        // generate a list of all calculators
        if(regulatorType == RegulatorType.PID) {
            calculators.add(new FistFormulaOppeltPID(plant));
            calculators.add(new FistFormulaReswickStoerPID0(plant));
            calculators.add(new FistFormulaReswickStoerPID20(plant));
            calculators.add(new FistFormulaReswickFuehrungPID0(plant));
            calculators.add(new FistFormulaReswickFuehrungPID20(plant));
            calculators.add(new FistFormulaRosenbergPID(plant));
            calculators.add(new ZellwegerPID(plant, phaseMargin));
        } else {
            calculators.add(new FistFormulaOppeltPI(plant));
            calculators.add(new FistFormulaReswickStoerPI0(plant));
            calculators.add(new FistFormulaReswickStoerPI20(plant));
            calculators.add(new FistFormulaReswickFuehrungPI0(plant));
            calculators.add(new FistFormulaReswickFuehrungPI20(plant));
            calculators.add(new FistFormulaRosenbergPI(plant));
            calculators.add(new ZellwegerPI(plant, phaseMargin));
        }

        // set row indices of calculator - see issue #29
        for(int i = 0; i < calculators.size(); i++) {
            calculators.get(i).setTableRowIndex(i);
        }

        return calculators;
    }

    private void dispatchControllerCalculators(ArrayList<IControllerCalculator> calculators) {
        for(IControllerCalculator calculator : calculators) {
            calculator.registerListener(this);
            calculator.setParasiticTimeConstantFactor(parasiticTimeConstantFactor);
            calculator.run(); // for some reason, this can't be threaded
        }
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

    private synchronized void notifyAddClosedLoop(ClosedLoop loop) {
        for(IModelListener listener : listeners) {
            listener.onAddClosedLoop(loop);
        }
    }

    private synchronized void notifySimulationBegin(int numberOfCalculators) {
        for(IModelListener listener : listeners) {
            listener.onSimulationBegin(numberOfCalculators);
        }
    }

    private synchronized void tryNotifySimulationComplete() {
        if(!isSimulationActive()) {
            listeners.forEach(ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener::onSimulationComplete);
        }
    }

    private synchronized void notifyHideSimulation(ClosedLoop closedLoop) {
        for(IModelListener listener : listeners) {
            listener.onHideStepResponse(closedLoop);
        }
    }

    private synchronized void notifyShowSimulation(ClosedLoop closedLoop) {
        for(IModelListener listener : listeners) {
            listener.onShowStepResponse(closedLoop);
        }
    }

    /**
     * Called when a controller finishes being calculated.
     * @param calculator The calculator that finished.
     */
    @Override
    public final synchronized void onControllerCalculationComplete(IControllerCalculator calculator) {
        ClosedLoop closedLoop = new ClosedLoop(plant, calculator.getController());

        // register as listener so we know when the step response calculation completes
        closedLoop.registerListener(this);

        // Add to list of closed loops and let the closed loop know its index in the table - see issue #29
        closedLoop.setTableRowIndex(calculator.getTableRowIndex());
        closedLoops.add(closedLoop);

        threadPool.submit(() -> closedLoop.calculateStepResponse(8 * 1024)); // number of sample points
    }

    /**
     * Called when a step response calculation completes.
     * @param closedLoop The closed loop object holding the results of the step response.
     */
    @Override
    public void onStepResponseCalculationComplete(ClosedLoop closedLoop) {
        notifyAddClosedLoop(closedLoop);
        tryNotifySimulationComplete();
    }
}
