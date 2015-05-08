package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

public class FistFormulaOppeltPI extends AbstractControllerCalculator {

    public FistFormulaOppeltPI(Plant plant) {
        super(plant);
    }

    @Override
    protected final AbstractController calculate() {
        return new ControllerPI(
                getName(),
                0.8 * plant.getTg() / (plant.getKs() * plant.getTu()),
                3.0 * plant.getTu()
        );
    }

    @Override
    public String getName() {
        return CalculatorNames.OPPELT_PI;
    }

    @Override
    public Color getColor() {
        return RenderColors.OPPELT_PI;
    }
}