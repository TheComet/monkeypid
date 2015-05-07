package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickStoerPID0 extends AbstractControllerCalculator {

    public FistFormulaReswickStoerPID0(Plant plant) {
        super(plant);
    }

    @Override
    public void calculate() {
        double tn = 2.4 * plant.getTu();
        double tv = 0.42 * plant.getTu();
        double kr = 0.95 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);

        this.controller = new PIDController(getName(), tn, tv, kr, tp);
    }

    @Override
    public String getName() {
        return "Faustformel Reswick PID, 0%, Gutes Störverhalten";
    }
}