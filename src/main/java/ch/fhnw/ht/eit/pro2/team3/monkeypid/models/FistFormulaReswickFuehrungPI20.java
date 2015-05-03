package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickFuehrungPI20 extends AbstractControllerCalculator {

    public FistFormulaReswickFuehrungPI20(Plant plant) {
        super(plant);
    }

    @Override
    public void run() {
        this.controller = new PIController(
                0.6 * plant.getTg() / (plant.getKs() * plant.getTu()),
                1.0 * plant.getTg()
        );
    }
}