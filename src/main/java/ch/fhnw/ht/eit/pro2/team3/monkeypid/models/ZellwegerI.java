package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Implements the Zellweger method for calculating I controllers.
 * @author Alex Murray
 */
public class ZellwegerI extends AbstractZellweger {
	private static final double angleOfInflection = -45.0;

	/**
	 * Constructs a new Zellweger calculator using the specified plant.
	 * @param plant The plant to calculate a controller for.
	 */
	public ZellwegerI(Plant plant) {
		super(plant, 0.0); // phase margin is irrelevant in Zellweger I
		setAngleOfInflection(angleOfInflection);
	}

	@Override
	public void setAngleOfInflectionOffset(double phaseOfInflectionOffset) {
		setAngleOfInflection(angleOfInflection + phaseOfInflectionOffset);
	}

	/**
	 * Constructs a new Zellweger calculator using the specified plant.
	 * Overloads Constructor for additional offset for the angleOfInflection
	 * This can be used to adjust the the rise of the stepResponse of this Controller
	 * @param plant The plant to calculate a controller for.
	 * @param angleOfInflectionOffset The offset for the angleOfInflection.
	 */
	public ZellwegerI(Plant plant, double angleOfInflectionOffset) {
		super(plant, 0.0); // phase margin is irrelevant in Zellweger I
		setAngleOfInflection(-45.0 + angleOfInflectionOffset);
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