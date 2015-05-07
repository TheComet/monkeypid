package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IClosedLoopListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Model implements IControllerCalculatorListener, IClosedLoopListener {

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
    };
    private RegulatorType regulatorType;

    private double parasiticTimeConstantFactor;

    // list of closed loops to be displayed on the graph
    private ArrayList<ClosedLoop> closedLoops = new ArrayList<>();

    // list of Model listeners
    private ArrayList<IModelListener> listeners = new ArrayList<>();

    public void setRegulatorType(String regulatorTypeName) {
        if(regulatorTypeName.compareTo("PI") == 0) {
            regulatorType = RegulatorType.PI;
        } else if(regulatorTypeName.compareTo("PID") == 0) {
            regulatorType = RegulatorType.PID;
        } else {
            System.out.println("Unknown regulator type: " + regulatorTypeName);
        }
    }

    public void setPlant(double ks, double tu, double tg) {
        this.plant = new Plant(tu, tg, ks, sani);
    }

    public void setPhaseMargin(double phaseMargin) {
        this.phaseMargin = phaseMargin;
    }

    public void setParasiticTimeConstantFactor(double parasiticTimeConstantFactor) {
        this.parasiticTimeConstantFactor = parasiticTimeConstantFactor;
    }

    private void clearSimulations() {

        // notify all listeners that we're removing all closed loops
        for(ClosedLoop loop : closedLoops) {
            for (IModelListener listener : listeners) {
                listener.onRemoveClosedLoop(loop);
            }
        }
        closedLoops = new ArrayList<>();
    }

    public void simulateAll() {

        // It's not thread safe to call this method while calculators are still active
        if(isSimulationActive()) {
            return;
        }

        clearSimulations();
        notifySimulationStarted();
        threadPool.submit(this::dispatchControllerCalculators);
    }

    public boolean isSimulationActive() {
        int queued = threadPool.getQueue().size();
        int active = threadPool.getActiveCount();
        return (queued + active > 0);
    }

    private void dispatchControllerCalculators() {

        ArrayList<IControllerCalculator> calculators = new ArrayList<>();

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

        for(IControllerCalculator calculator : calculators) {
            calculator.registerListener(this);
            calculator.setParasiticTimeConstantFactor(parasiticTimeConstantFactor);
            calculator.run(); // for some reason, this can't be threaded
        }
    }

    public final void registerListener(IModelListener listener) {
        listeners.add(listener);
    }

    public final void unregisterListener(IModelListener listener) {
        listeners.remove(listener);
    }

    private void notifyAddClosedLoop(ClosedLoop loop) {
        for(IModelListener listener : listeners) {
            listener.onAddClosedLoop(loop);
        }
    }

    private void notifySimulationStarted() {
        listeners.forEach(ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener::onSimulationStarted);
    }

    private void notifySimulationComplete() {
        if(!isSimulationActive()) {
            listeners.forEach(ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener::onSimulationComplete);
        }
    }

    @Override
    public final void onControllerCalculationComplete(IControllerCalculator calculator) {
        ClosedLoop closedLoop = new ClosedLoop(plant, calculator.getController());
        closedLoops.add(closedLoop);
        closedLoop.registerListener(this);
        notifyAddClosedLoop(closedLoop);
        threadPool.submit(() -> closedLoop.calculateStepResponse(4 * 1024));
    }

    @Override
    public void onStepResponseCalculationComplete(ClosedLoop closedLoop) {
        notifySimulationComplete();
    }
}
