package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Implements the Zellweger method for calculating I controllers.
 * @author Alex Murray
 */
public class ZellwegerI extends AbstractZellweger {

    /**
     * Constructs a new Zellweger calculator using the specified plant.
     * @param plant The plant to calculate a controller for.
     */
    public ZellwegerI(Plant plant) {
        super(plant, 0.0); // phase margin is irrelevant in Zellweger I
        setAngleOfInflection(-45.0);
    }
    
    public ZellwegerI(Plant plant, double angleOfInflection) {
        super(plant, 0.0); // phase margin is irrelevant in Zellweger I
        setAngleOfInflection(-45.0 + angleOfInflection);
    }

    /**
     * Calculates the appropriate controller for the specified plant.
     * @return Returns a new I controller.
     */
    @Override
    protected final AbstractController calculate() {

        // Ti parameter of controller
        double ti = 1.0 / findAngleOnPlantPhase();

        return new ControllerI(getName(), ti);
    }

    /**
     * Gets the name of this calculator. The names are stored in a global class called CalculatorNames.
     * @return The name of this controller.
     */
    @Override
    public final String getName() {
        return CalculatorNames.ZELLWEGER_I;
    }

    /**
     * Gets the render colour of this calculator. The colours are stored in a global class called RenderColors.
     * @return The render color.
     */
    @Override
    public final Color getColor() {
        return RenderColors.ZELLWEGER_I;
    }
}