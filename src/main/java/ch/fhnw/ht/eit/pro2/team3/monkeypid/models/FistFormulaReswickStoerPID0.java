package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickStoerPID0 extends AbstractControllerCalculator {

    public FistFormulaReswickStoerPID0(Plant plant) {
        super(plant);
    }

    @Override
    public void calculate() {
        this.controller = new PIDController(
                2.4 * plant.getTu(),
                0.42 * plant.getTu(),
                0.95 * plant.getTg() / (plant.getKs() * plant.getTu()),
                0.0
        );
    }
}