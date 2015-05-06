package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IClosedLoopListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;

import java.util.ArrayList;

public class Model implements IControllerCalculatorListener, IClosedLoopListener {

    // have the model own the sani curves, so they don't have to be reloaded from
    // disk every time a new calculation is performed.
    private SaniCurves sani = new SaniCurves();

    // current plant to use for controller calculations
    private Plant plant = null;

    private double phaseMargin = 0.0;

    // list of closed loops to be displayed on the graph
    private ArrayList<ClosedLoop> closedLoops = new ArrayList<>();

    // list of Model listeners
    private ArrayList<IModelListener> listeners = new ArrayList<>();

    public void updatePlant(double ks, double tu, double tg) {
        this.plant = new Plant(tu, tg, ks, sani);
    }

    public void updatePhaseMargin(double phaseMargin) {
        this.phaseMargin = phaseMargin;
    }

    public void clearSimulations() {
        closedLoops = new ArrayList<>();
    }

    public void simulateAll() {
        dispatchControllerCalculators();
    }

    public void dispatchControllerCalculators() {
        ArrayList<IControllerCalculator> calculators = new ArrayList<>();

        calculators.add(new FistFormulaOppeltPI(plant));
        calculators.add(new FistFormulaOppeltPID(plant));
        calculators.add(new FistFormulaReswickStoerPI0(plant));
        calculators.add(new FistFormulaReswickStoerPI20(plant));
        calculators.add(new FistFormulaReswickStoerPID0(plant));
        calculators.add(new FistFormulaReswickStoerPID20(plant));
        calculators.add(new FistFormulaReswickFuehrungPI0(plant));
        calculators.add(new FistFormulaReswickFuehrungPI20(plant));
        calculators.add(new FistFormulaReswickFuehrungPID0(plant));
        calculators.add(new FistFormulaReswickFuehrungPID20(plant));
        calculators.add(new FistFormulaRosenbergPI(plant));
        calculators.add(new FistFormulaRosenbergPID(plant));
        calculators.add(new ZellwegerPI(plant, phaseMargin));
        calculators.add(new ZellwegerPID(plant, phaseMargin));

        for(IControllerCalculator calculator : calculators) {
            calculator.registerListener(this);
            calculator.run();
        }
    }

    public final void registerListener(IModelListener listener) {
        listeners.add(listener);
    }

    public final void unregisterListener(IModelListener listener) {
        listeners.remove(listener);
    }

    @Override
    public final void notifyControllerCalculationComplete(IControllerCalculator calculator) {
        ClosedLoop closedLoop = new ClosedLoop(plant, calculator.getController());
        closedLoop.registerListener(this);
        closedLoops.add(closedLoop);
    }

    @Override
    public void notifyStepResponseCalculationComplete(ClosedLoop closedLoop) {

    }
}
