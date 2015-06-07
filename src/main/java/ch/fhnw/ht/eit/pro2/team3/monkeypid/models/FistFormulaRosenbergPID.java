package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Implements the "Faustformal PID" controller calculator developed by Rosenberg.
 * @author Alex Murray
 */
public class FistFormulaRosenbergPID extends AbstractControllerCalculator {

	/**
	 * Constructs the controller calculator and sets the plant to calculate a controller for.
	 * @param plant The plant to calculate a controller for.
	 */
	public FistFormulaRosenbergPID(Plant plant) {
		super(plant);
	}

	/**
	 * Calculates the appropriate controller for the specified plant.
	 * @return A new PID controller.
	 */
	@Override
	protected final AbstractController calculate() {
		double tn = 2.0 * plant.getTu();
		double tv = 0.44 * plant.getTu();
		double kr = 1.2 * plant.getTg() / (plant.getKs() * plant.getTu());
		double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);

		return new ControllerPID(getName(), tn, tv, kr, tp);
	}

	/**
	 * Gets the name of this calculator. The names are stored in a global class called CalculatorNames.
	 * @return The name of this controller.
	 */
	@Override
	public String getName() {
		return CalculatorNames.ROSENBERG_PID;
	}

	/**
	 * Gets the render colour of this calculator. The colours are stored in a global class called RenderColors.
	 * @return The render color.
	 */
	@Override
	public Color getColor() {
		return RenderColors.ROSENBERG_PID;
	}
}