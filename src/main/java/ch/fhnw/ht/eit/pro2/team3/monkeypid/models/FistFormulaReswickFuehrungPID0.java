package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickFuehrungPID0 extends AbstractControllerCalculator {

    public FistFormulaReswickFuehrungPID0(Plant plant) {
        super(plant);
    }

    @Override
    public void calculate() {
        this.controller = new PIDController(
                getName(),
                1.0 * plant.getTg(),
                0.5 * plant.getTu(),
                0.6 * plant.getTg() / (plant.getKs() * plant.getTu()),
                0.0
        );
    }

    @Override
    public String getName() {
        return "Faustformel Reswick PID, 0%, Gute Führung";
    }
}