package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

public class FistFormulaReswickStoerPI0 extends AbstractControllerCalculator {

    public FistFormulaReswickStoerPI0(Plant plant) {
        super(plant);
    }

    @Override
    protected final AbstractController calculate() {
        return new PIController(
                getName(),
                0.6 * plant.getTg() / (plant.getKs() * plant.getTu()),
                4.0 * plant.getTu()
        );
    }

    @Override
    public String getName() {
        return CalculatorNames.RESWICK_STOER_PI_0;
    }

    @Override
    public Color getColor() {
        return RenderColors.RESWICK_STOER_PI_0;
    }
}