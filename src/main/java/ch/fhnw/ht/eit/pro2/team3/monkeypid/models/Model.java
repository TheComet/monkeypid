package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

    // have the model own the sani curves, so they don't have to be reloaded from
    // disk every time a new calculation is performed.
    private SaniCurves sani = new SaniCurves();
    private Plant plant = null;

    public void updatePlant(double ks, double tu, double tg) {
        System.out.println("Updating plant, recalculating time constants");
        this.plant = new Plant(tu, tg, ks, sani);
    }

    public void simulateAll() {
/*
        System.out.println("Calculating regulator params...");
        long startTime = System.nanoTime();

        // get all calculators
        ArrayList<IControllerCalculator> calculators = getCalculators();

        // generate a lis of threads for calculating each method
        ArrayList<Thread> threads = new ArrayList<>();
        for(IControllerCalculator calc : calculators) {
            threads.add(new Thread(() -> {
                calc.calculate(plant);
            }));
        }

        // launch all threads and wait for them to finish
        threads.forEach(java.lang.Thread::start);
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch(InterruptedException e) {
        }

        System.out.println("Finished in " + ((System.nanoTime() - startTime) * 0.000001) + "ms");*/
    }

    private ArrayList<IControllerCalculator> getCalculators() {
        ArrayList<IControllerCalculator> methods = new ArrayList<>();

        methods.add(new FistFormulaOppeltPI(plant));
        methods.add(new FistFormulaOppeltPID(plant));
        methods.add(new FistFormulaReswickStoerPI0(plant));
        methods.add(new FistFormulaReswickStoerPI20(plant));
        methods.add(new FistFormulaReswickStoerPID0(plant));
        methods.add(new FistFormulaReswickStoerPID20(plant));
        methods.add(new FistFormulaReswickFuehrungPI0(plant));
        methods.add(new FistFormulaReswickFuehrungPI20(plant));
        methods.add(new FistFormulaReswickFuehrungPID0(plant));
        methods.add(new FistFormulaReswickFuehrungPID20(plant));
        methods.add(new FistFormulaRosenbergPI(plant));
        methods.add(new FistFormulaRosenbergPID(plant));

        return methods;
    }
}
