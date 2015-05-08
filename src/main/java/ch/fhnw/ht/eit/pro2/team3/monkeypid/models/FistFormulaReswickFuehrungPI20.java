package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

public class FistFormulaReswickFuehrungPI20 extends AbstractControllerCalculator {

    public FistFormulaReswickFuehrungPI20(Plant plant) {
        super(plant);
    }

    @Override
    protected final AbstractController calculate() {
        return new ControllerPI(
                getName(),
                0.6 * plant.getTg() / (plant.getKs() * plant.getTu()),
                1.0 * plant.getTg()
        );
    }

    @Override
    public String getName() {
        return CalculatorNames.RESWICK_FUEHRUNG_PI_20;
    }

    @Override
    public Color getColor() {
        return RenderColors.RESWICK_FUEHRUNG_PI_20;
    }
}