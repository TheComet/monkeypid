package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickStoerPI0 extends AbstractControllerCalculator {

    public FistFormulaReswickStoerPI0(Plant plant) {
        super(plant);
    }

    @Override
    public void calculate() {
        this.controller = new PIController(
                0.6 * plant.getTg() / (plant.getKs() * plant.getTu()),
                4.0 * plant.getTu()
        );
    }
}