package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Implements the "Faustformal PI" controller calculator developed by Rosenberg.
 * @author Alex Murray
 */
public class FistFormulaRosenbergPI extends AbstractControllerCalculator {

    /**
     * Constructs the controller calculator and sets the plant to calculate a controller for.
     * @param plant The plant to calculate a controller for.
     */
    public FistFormulaRosenbergPI(Plant plant) {
        super(plant);
    }

    /**
     * Calculates the appropriate controller for the specified plant.
     * @return A new PI controller.
     */
    @Override
    protected final AbstractController calculate() {
        return new ControllerPI(
                getName(),
                0.91 * plant.getTg() / (plant.getKs() * plant.getTu()),
                3.3 * plant.getTu()
        );
    }

    /**
     * Gets the name of this calculator. The names are stored in a global class called CalculatorNames.
     * @return The name of this controller.
     */
    @Override
    public String getName() {
        return CalculatorNames.ROSENBERG_PI;
    }

    /**
     * Gets the render colour of this calculator. The colours are stored in a global class called RenderColors.
     * @return The render color.
     */
    @Override
    public Color getColor() {
        return RenderColors.ROSENBERG_PI;
    }
}