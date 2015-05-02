package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickFuehrungPID20 extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.IController = new PIDController(
                0.95 * path.getTg() / (path.getKs() * path.getTu()),
                1.35 * path.getTg(),
                0.47 * path.getTu()
        );
    }
}