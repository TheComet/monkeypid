package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaOppeltPI extends AbstractControllerCalculator {

    public FistFormulaOppeltPI(Plant plant) {
        super(plant);
    }

    @Override
    public void calculate() {
        this.controller = new PIController(
                getName(),
                0.8 * plant.getTg() / (plant.getKs() * plant.getTu()),
                3.0 * plant.getTu()
        );
    }

    @Override
    public String getName() {
        return "Faustformel Oppelt PI";
    }
}