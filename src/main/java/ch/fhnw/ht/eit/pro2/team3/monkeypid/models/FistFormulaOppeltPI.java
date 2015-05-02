package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaOppeltPI extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.IController = new PIController(
                0.8 * path.getTg() / (path.getKs() * path.getTu()),
                3.0 * path.getTu()
        );
    }
}