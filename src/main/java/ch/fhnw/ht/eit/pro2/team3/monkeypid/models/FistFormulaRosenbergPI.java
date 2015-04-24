package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaRosenbergPI extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.controller = new PIController(
                0.91 * path.getTg() / (path.getKs() * path.getTu()),
                3.3 * path.getTu()
        );
    }
}