package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickFuehrungPID20 extends AbstractControllerCalculator {

    public FistFormulaReswickFuehrungPID20(Plant plant) {
        super(plant);
    }

    @Override
    public void calculate() {
        double tn = 1.35 * plant.getTg();
        double tv = 0.47 * plant.getTu();
        double kr = 0.95 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = tv * parasiticTimeConstantFactor;
        
        this.controller = new PIDController(getName(), tn, tv, kr, tp);
    }

    @Override
    public String getName() {
        return "Faustformel Reswick PID, 20%, Gute Führung";
    }
}