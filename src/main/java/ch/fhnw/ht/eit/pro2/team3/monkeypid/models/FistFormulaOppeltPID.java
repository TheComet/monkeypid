package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaOppeltPID extends AbstractControllerCalculator {

    public FistFormulaOppeltPID(Plant plant) {
        super(plant);
    }

    @Override
    public void calculate() {
        this.controller = new PIDController(
                getName(),
                2.0 * plant.getTu(),
                0.42 * plant.getTu(),
                1.2 * plant.getTg() / (plant.getKs() * plant.getTu()),
                0.0
        );
    }

    @Override
    public String getName() {
        return "Faustformel Oppelt PID";
    }
}