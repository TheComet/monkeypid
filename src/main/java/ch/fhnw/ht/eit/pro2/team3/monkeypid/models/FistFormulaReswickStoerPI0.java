package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickStoerPI0 extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.controller = new PIController(
                0.6 * path.getTg() / (path.getKs() * path.getTu()),
                4.0 * path.getTu()
        );
    }
}