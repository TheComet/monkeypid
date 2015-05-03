package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class FistFormulaReswickFuehrungPI0 extends AbstractControllerCalculator {

    public FistFormulaReswickFuehrungPI0(Plant plant) {
        super(plant);
    }

    @Override
    public void run() {
        this.controller = new PIController(
                0.45 * plant.getTg() / (plant.getKs() * plant.getTu()),
                1.2 * plant.getTg()
        );
    }
}