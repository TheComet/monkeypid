package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Implements the "Faustformal PI mit 20% Überschwingen und gutes Führungsverhalten" controller calculator developed by
 * Kun Li Chien, J.A. Hrones, and J.B. Reswick in 1952.
 * @author Alex Murray
 */
public class FistFormulaReswickFuehrungPI20 extends AbstractControllerCalculator {

	/**
	 * Constructs the controller calculator and sets the plant to calculate a controller for.
	 * @param plant The plant to calculate a controller for.
	 */
	public FistFormulaReswickFuehrungPI20(Plant plant) {
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
				0.6 * plant.getTg() / (plant.getKs() * plant.getTu()),
				1.0 * plant.getTg()
		);
	}

	/**
	 * Gets the name of this calculator. The names are stored in a global class called CalculatorNames.
	 * @return The name of this controller.
	 */
	@Override
	public final String getName() {
		return CalculatorNames.RESWICK_FUEHRUNG_PI_20;
	}

	/**
	 * Gets the render colour of this calculator. The colours are stored in a global class called RenderColors.
	 * @return The render color.
	 */
	@Override
	public final Color getColor() {
		return RenderColors.RESWICK_FUEHRUNG_PI_20;
	}
}