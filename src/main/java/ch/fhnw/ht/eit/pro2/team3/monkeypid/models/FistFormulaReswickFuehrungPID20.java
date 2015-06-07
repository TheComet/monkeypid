package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Implements the "Faustformal PID mit 20% Überschwingen und gutes Führungsverhalten" controller calculator developed by
 * Kun Li Chien, J.A. Hrones, and J.B. Reswick in 1952.
 * @author Alex Murray
 */
public class FistFormulaReswickFuehrungPID20 extends AbstractControllerCalculator {

	/**
	 * Constructs the controller calculator and sets the plant to calculate a controller for.
	 * @param plant The plant to calculate a controller for.
	 */
	public FistFormulaReswickFuehrungPID20(Plant plant) {
		super(plant);
	}

	/**
	 * Calculates the appropriate controller for the specified plant.
	 * @return A new PID controller.
	 */
	@Override
	protected final AbstractController calculate() {
		double tn = 1.35 * plant.getTg();
		double tv = 0.47 * plant.getTu();
		double kr = 0.95 * plant.getTg() / (plant.getKs() * plant.getTu());
		double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);
		
		return new ControllerPID(getName(), tn, tv, kr, tp);
	}

	/**
	 * Gets the name of this calculator. The names are stored in a global class called CalculatorNames.
	 * @return The name of this controller.
	 */
	@Override
	public final String getName() {
		return CalculatorNames.RESWICK_FUEHRUNG_PID_20;
	}

	/**
	 * Gets the render colour of this calculator. The colours are stored in a global class called RenderColors.
	 * @return The render color.
	 */
	@Override
	public final Color getColor() {
		return RenderColors.RESWICK_FUEHRUNG_PID_20;
	}
}