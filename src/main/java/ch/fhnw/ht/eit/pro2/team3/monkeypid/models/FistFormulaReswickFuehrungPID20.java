package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickFuehrungPID20 extends AbstractControllerCalculator {

    public FistFormulaReswickFuehrungPID20(Plant plant) {
        super(plant);
    }

    @Override
    public void run() {
        this.controller = new PIDController(
                1.35 * plant.getTg(),
                0.47 * plant.getTu(),
                0.95 * plant.getTg() / (plant.getKs() * plant.getTu()),
                0.0
        );
    }
}