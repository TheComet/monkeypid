package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickPI20 extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.controller = new PIController(
                0.7 * path.getTg() / (path.getKs() * path.getTu()),
                4.0 * path.getTu()
        );
    }
}