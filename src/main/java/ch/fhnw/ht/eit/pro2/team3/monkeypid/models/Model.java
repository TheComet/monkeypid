package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.RegulatorCalculator;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

    private SaniCurves sani = new SaniCurves();
    private ControlPath controlPath = null;

    public void setControlPath(ControlPath controlPath) {
        System.out.println("Updating control path");
        this.controlPath = controlPath;

        // recalculate time constants
        double[] timeConstants = sani.calculateTimeConstants(controlPath.getTu(), controlPath.getTg());
        controlPath.setTimeConstants(timeConstants);
    }

    public void simulateAll() {

        System.out.println("Calculating regulator params...");
        long startTime = System.nanoTime();

        // get all calculators
        ArrayList<RegulatorCalculator> calculators = getCalculators();

        // generate a lis of threads for calculating each method
        ArrayList<Thread> threads = new ArrayList<>();
        for(RegulatorCalculator calc : calculators) {
            threads.add(new Thread(() -> {
                calc.calculate(controlPath);
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

    private ArrayList<RegulatorCalculator> getCalculators() {
        ArrayList<RegulatorCalculator> methods = new ArrayList<>();

        methods.add(new FistFormulaOppeltPI());
        methods.add(new FistFormulaOppeltPID());
        methods.add(new FistFormulaReswickPI0());
        methods.add(new FistFormulaReswickPI20());
        methods.add(new FistFormulaReswickPID0());
        methods.add(new FistFormulaReswickPID20());
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
