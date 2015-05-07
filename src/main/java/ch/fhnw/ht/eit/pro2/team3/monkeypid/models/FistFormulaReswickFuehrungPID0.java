package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickFuehrungPID0 extends AbstractControllerCalculator {

    public FistFormulaReswickFuehrungPID0(Plant plant) {
        super(plant);
    }

    @Override
    public void calculate() {
        double tn = 1.0 * plant.getTg();
        double tv = 0.5 * plant.getTu();
        double kr = 0.6 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);

        this.controller = new PIDController(getName(), tn, tv, kr, tp);
    }

    @Override
    public String getName() {
        return "Faustformel Reswick PID, 0%, Gute Führung";
    }
}