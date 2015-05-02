package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickFuehrungPID0 extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.IController = new PIDController(
                0.6 * path.getTg() / (path.getKs() * path.getTu()),
                1.0 * path.getTg(),
                0.5 * path.getTu()
        );
    }
}