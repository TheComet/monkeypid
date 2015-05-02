package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickStoerPID20 extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.controller = new PIDController(
                1.2 * path.getTg() / (path.getKs() * path.getTu()),
                2.0 * path.getTu(),
                0.42 * path.getTu()
        );
    }
}