package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;

import java.util.ArrayList;

public class Model {

    // have the model own the sani curves, so they don't have to be reloaded from
    // disk every time a new calculation is performed.
    private SaniCurves sani = new SaniCurves();
    private Plant plant = null;
    private double phaseMargin = 0.0;
    private ArrayList<IControllerCalculatorListener> controllerCalculatorListeners = new ArrayList<>();

    public void updatePlant(double ks, double tu, double tg) {
        this.plant = new Plant(tu, tg, ks, sani);
    }

    public void updatePhaseMargin(double phaseMargin) {
        this.phaseMargin = phaseMargin;
    }

    public void clearSimulations() {

    }

    public void simulateAll() {
        ArrayList<IControllerCalculator> ccs = getCalculators();

        for(IControllerCalculator cc : ccs) {
            cc.run();
        }
    }

    private ArrayList<IControllerCalculator> getCalculators() {
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

        // register any listeners that want to know about the results of the calculators
        for(IControllerCalculatorListener listener : controllerCalculatorListeners) {
            for(IControllerCalculator c : calculators) {
                c.registerListener(listener);
            }
        }

        return calculators;
    }

    public void registerControllerCalculatorListener(IControllerCalculatorListener listener) {
        controllerCalculatorListeners.add(listener);
    }
}
