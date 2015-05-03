package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaRosenbergPID extends AbstractControllerCalculator {

    public FistFormulaRosenbergPID(Plant plant) {
        super(plant);
    }

    @Override
    public void run() {
        this.controller = new PIDController(
                2.0 * plant.getTu(),
                0.44 * plant.getTu(),
                1.2 * plant.getTg() / (plant.getKs() * plant.getTu()),
                0.0
        );
    }
}