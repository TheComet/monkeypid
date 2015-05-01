package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.ControllerCalculator;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

    private SaniCurves sani = new SaniCurves();
    private Plant plant = null;

    public void setPlant(Plant plant) {
        System.out.println("Updating control path");
        this.plant = plant;

        // recalculate time constants
        double[] timeConstants = sani.calculateTimeConstants(plant.getTu(), plant.getTg());
        plant.setTimeConstants(timeConstants);
    }

    public void simulateAll() {

        System.out.println("Calculating regulator params...");
        long startTime = System.nanoTime();

        // get all calculators
        ArrayList<ControllerCalculator> calculators = getCalculators();

        // generate a lis of threads for calculating each method
        ArrayList<Thread> threads = new ArrayList<>();
        for(ControllerCalculator calc : calculators) {
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

        System.out.println("Finished in " + ((System.nanoTime() - startTime) * 0.000001) + "ms");
    }

    private ArrayList<ControllerCalculator> getCalculators() {
        ArrayList<ControllerCalculator> methods = new ArrayList<>();

        methods.add(new FistFormulaOppeltPI());
        methods.add(new FistFormulaOppeltPID());
        methods.add(new FistFormulaReswickStoerPI0());
        methods.add(new FistFormulaReswickStoerPI20());
        methods.add(new FistFormulaReswickStoerPID0());
        methods.add(new FistFormulaReswickStoerPID20());
        methods.add(new FistFormulaReswickFuehrungPI0());
        methods.add(new FistFormulaReswickFuehrungPI20());
        methods.add(new FistFormulaReswickFuehrungPID0());
        methods.add(new FistFormulaReswickFuehrungPID20());
        methods.add(new FistFormulaRosenbergPI());
        methods.add(new FistFormulaRosenbergPID());
        methods.add(new ZellwegerPI());
        methods.add(new ZellwegerPID());

        return methods;
    }

	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
}
