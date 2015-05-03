package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaOppeltPI extends AbstractControllerCalculator {

    public FistFormulaOppeltPI(Plant plant) {
        super(plant);
    }

    @Override
    public void run() {
        this.controller = new PIController(
                0.8 * plant.getTg() / (plant.getKs() * plant.getTu()),
                3.0 * plant.getTu()
        );
    }
}