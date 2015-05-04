package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickStoerPI20 extends AbstractControllerCalculator {

    public FistFormulaReswickStoerPI20(Plant plant) {
        super(plant);
    }

    @Override
    public void calculate() {
        this.controller = new PIController(
                getName(),
                0.7 * plant.getTg() / (plant.getKs() * plant.getTu()),
                2.3 * plant.getTu()
        );
    }

    @Override
    public String getName() {
        return "Faustformel Reswick PI, 20%, Gutes Störverhalten";
    }
}