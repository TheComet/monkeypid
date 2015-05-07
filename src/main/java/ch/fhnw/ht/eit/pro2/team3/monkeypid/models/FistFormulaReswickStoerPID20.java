package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickStoerPID20 extends AbstractControllerCalculator {

    public FistFormulaReswickStoerPID20(Plant plant) {
        super(plant);
    }

    @Override
    public void calculate() {
        double tn = 2.0 * plant.getTu();
        double tv = 0.42 * plant.getTu();
        double kr = 1.2 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = tv * parasiticTimeConstantFactor;

        this.controller = new PIDController(getName(), tn, tv, kr, tp);
    }

    @Override
    public String getName() {
        return "Faustformel Reswick PID, 20%, Gutes Störverhalten";
    }
}