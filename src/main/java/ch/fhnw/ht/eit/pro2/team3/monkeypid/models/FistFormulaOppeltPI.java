package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Implements the "Faustformal PI" controller calculator developed by Dr. Ing. Winfried Oppelt.
 * @author Alex Murray
 */
public class FistFormulaOppeltPI extends AbstractControllerCalculator {

	/**
	 * Constructs the controller calculator and sets the plant to calculate a controller for.
	 * @param plant The plant to calculate a controller for.
	 */
	public FistFormulaOppeltPI(Plant plant) {
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
				0.8 * plant.getTg() / (plant.getKs() * plant.getTu()),
				3.0 * plant.getTu()
		);
	}

	/**
	 * Gets the name of this calculator. The names are stored in a global class called CalculatorNames.
	 * @return The name of this controller.
	 */
	@Override
	public final String getName() {
		return CalculatorNames.OPPELT_PI;
	}

	/**
	 * Gets the render colour of this calculator. The colours are stored in a global class called RenderColors.
	 * @return The render color.
	 */
	@Override
	public final Color getColor() {
		return RenderColors.OPPELT_PI;
	}
}