package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

import java.awt.*;

public class FistFormulaReswickStoerPI20 extends AbstractControllerCalculator {

    public FistFormulaReswickStoerPI20(Plant plant) {
        super(plant);
    }

    @Override
    protected final IController calculate() {
        return new PIController(
                getName(),
                0.7 * plant.getTg() / (plant.getKs() * plant.getTu()),
                2.3 * plant.getTu()
        );
    }

    @Override
    public String getName() {
        return CalculatorNames.RESWICK_STOER_PI_20;
    }

    @Override
    public Color getColor() {
        return RenderColors.RESWICK_STOER_PI_20;
    }
}