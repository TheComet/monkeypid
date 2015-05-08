package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

public class FistFormulaRosenbergPI extends AbstractControllerCalculator {

    public FistFormulaRosenbergPI(Plant plant) {
        super(plant);
    }

    @Override
    protected final AbstractController calculate() {
        return new PIController(
                getName(),
                0.91 * plant.getTg() / (plant.getKs() * plant.getTu()),
                3.3 * plant.getTu()
        );
    }

    @Override
    public String getName() {
        return CalculatorNames.ROSENBERG_PI;
    }

    @Override
    public Color getColor() {
        return RenderColors.ROSENBERG_PI;
    }
}