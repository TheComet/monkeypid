package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickPID0 extends AbstractControllerCalculator {

    @Override
    public void calculate(Plant path) {
        this.controller = new PIController(
                path.getTg() / (path.getKs() * path.getTu()),
                path.getTu() * 4
        );
    }
}