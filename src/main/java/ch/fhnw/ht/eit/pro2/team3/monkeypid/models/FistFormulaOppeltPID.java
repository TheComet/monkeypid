package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaOppeltPID extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.controller = new PIDController(
                2.0 * path.getTu(),
                0.42 * path.getTu(),
                1.2 * path.getTg() / (path.getKs() * path.getTu()),
                0.0
        );
    }
}