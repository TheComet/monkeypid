package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Implements the "Faustformal PID mit 20% Überschwingen und gutes Störverhalten" controller calculator developed by
 * Kun Li Chien, J.A. Hrones, and J.B. Reswick in 1952.
 * @author Alex Murray
 */
public class FistFormulaReswickStoerPID20 extends AbstractControllerCalculator {

    /**
     * Constructs the controller calculator and sets the plant to calculate a controller for.
     * @param plant The plant to calculate a controller for.
     */
    public FistFormulaReswickStoerPID20(Plant plant) {
        super(plant);
    }

    /**
     * Calculates the appropriate controller for the specified plant.
     * @return A new PID controller.
     */
    @Override
    protected final AbstractController calculate() {
        double tn = 2.0 * plant.getTu();
        double tv = 0.42 * plant.getTu();
        double kr = 1.2 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);

        return new ControllerPID(getName(), tn, tv, kr, tp);
    }

    /**
     * Gets the name of this calculator. The names are stored in a global class called CalculatorNames.
     * @return The name of this controller.
     */
    @Override
    public final String getName() {
        return CalculatorNames.RESWICK_STOER_PID_20;
    }

    /**
     * Gets the render colour of this calculator. The colours are stored in a global class called RenderColors.
     * @return The render color.
     */
    @Override
    public final Color getColor() {
        return RenderColors.RESWICK_STOER_PID_20;
    }
}