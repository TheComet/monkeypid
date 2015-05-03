package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickStoerPID0 extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.controller = new PIDController(
                2.4 * path.getTu(),
                0.42 * path.getTu(),
                0.95 * path.getTg() / (path.getKs() * path.getTu()),
                0.0
        );
    }
}