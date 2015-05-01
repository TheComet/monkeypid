package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickFuehrungPI0 extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.controller = new PIController(
                0.45 * path.getTg() / (path.getKs() * path.getTu()),
                1.2 * path.getTg()
        );
    }
}