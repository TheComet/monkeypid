package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickStoerPID20 extends AbstractControllerCalculator {

    public FistFormulaReswickStoerPID20(Plant plant) {
        super(plant);
    }

    @Override
    public void run() {
        this.controller = new PIDController(
                2.0 * plant.getTu(),
                0.42 * plant.getTu(),
                1.2 * plant.getTg() / (plant.getKs() * plant.getTu()),
                0.0
        );
    }
}