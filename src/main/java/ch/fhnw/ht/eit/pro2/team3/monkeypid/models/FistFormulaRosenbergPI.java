package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaRosenbergPI extends AbstractControllerCalculator {

    public FistFormulaRosenbergPI(Plant plant) {
        super(plant);
    }

    @Override
    public void run() {
        this.controller = new PIController(
                0.91 * plant.getTg() / (plant.getKs() * plant.getTu()),
                3.3 * plant.getTu()
        );
    }
}