package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Defines the common interface for classes implementing the Zellweger calculator. Zellweger requires some additional
 * information, such as the phase margin and the maximum number of iterations.
 * @author Alex Murray
 */
public abstract class AbstractZellweger extends AbstractControllerCalculator {
	protected double phiDamping;
	protected double angleOfInflection;
	protected double startFreq, endFreq;

	// 18 iterations is enough for a precision of 4 decimal digits (1.0000)
	protected double maxIterations = 18;

	// -----------------------------------------------------------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * A plant object and the phase margin are required for a Zellweger calculator to be able to calculate a controller.
	 * @param plant The plant to calculate a controller for.
	 * @param phaseMargin The phase margin to use.
	 */
	public AbstractZellweger(Plant plant, double phaseMargin) {
		super(plant);
		setPhaseMargin(phaseMargin);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Public methods
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Sets the phase margin. Internally, this is stored as phiDamping, which is -180° + margin.
	 * @param phi The phase margin in degrees.
	 */
	public final void setPhaseMargin(double phi) {
		this.phiDamping = phi - 180;
	}

	/**
	 * Sets the angle of inflection. Depending on whether the resulting controller is I, PI, or PID, the default angle
	 * of inflection will be -45°, -90°, and -135° respectively.
	 * @param angleOfInflection The angle of inflection in degrees.
	 */
	public final void setAngleOfInflection(double angleOfInflection) {
		this.angleOfInflection = angleOfInflection;
	}

	/**
	 * Sets the maximum number of iterations to use in binary search approximations. The default value is 18, which
	 * has been proven through testing to be enough.
	 * @param iterations The number of iterations to use.
	 */
	public final void setMaxIterations(int iterations) {
		maxIterations = iterations;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Private methods
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Calculates the phase of the plant (without the controller).
	 * @param omega Omega
	 * @return -(atan(w*T1)+atan(w*T2)+atan(w*Tc))
	 */
	protected final double calculatePlantPhase(double omega) {
		double phiControlPath = 0.0;
		for(double timeConstant : plant.getTimeConstants()) {
			phiControlPath += Math.atan(omega * timeConstant);
		}

		// convert to degrees and invert sign
		return -phiControlPath * 180.0 / Math.PI;
	}

	/**
	 * Calculates the amplitude of the plant (without the controller)
	 * @param omega Omega
	 * @return Ks/(sqrt(1+(w*T1)^2)*sqrt(1+(w*T2)^2)*sqrt(1+(w*Tc)^2))
	 */
	protected final double calculatePlantAmplitude(double omega) {
		double denominator = 1.0;
		for(double timeConstant : plant.getTimeConstants()) {
			denominator *= Math.sqrt(1.0 + Math.pow(omega * timeConstant, 2));
		}
		return plant.getKs() / denominator;
	}

	/**
	 * Uses a binary search to approximate the angle on the phase of the plant. Accuracy can be set by calling the
	 * method setMaxIterations().
	 * @return Returns the frequency on the phase of the plant.
	 */
	protected final double findAngleOnPlantPhase() {

		// find angleOfInflection on the phase of the control path
		double topFreq = endFreq;
		double bottomFreq = startFreq;
		double actualFreq = (topFreq + bottomFreq) / 2.0;
		double phi;
		for (int i = 0; i != maxIterations; ++i) {
			phi = calculatePlantPhase(actualFreq);
			if (phi < angleOfInflection) {
				topFreq = actualFreq;
				actualFreq = (topFreq + bottomFreq) / 2.0;
			} else if (phi > angleOfInflection) {
				bottomFreq = actualFreq;
				actualFreq = (topFreq + bottomFreq) / 2.0;
			}
		}

		return actualFreq;
	}

	/**
	 * Calculates the minimum and maximum frequency used in look-up functions.
	 */
	private void updateFrequencyRange() {

		// get minimum and maximum time constants
		List timeConstantsList = Arrays.asList(ArrayUtils.toObject(plant.getTimeConstants()));
		double tcMin = (double) Collections.min(timeConstantsList);
		double tcMax = (double) Collections.max(timeConstantsList);

		// calculate the frequency range to use in all following calculations,
		// based on the time constants.
		startFreq = 1.0 / (tcMax * 10.0);
		endFreq = 1.0 / (tcMin / 10.0);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Derived methods
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Because Zellweger has to do some additional calculations whenever the plant changes,
	 * override the setPlant() method.
	 * @param plant The new plant to use for future calculations.
	 */
	@Override
	public void setPlant(Plant plant) {
		this.plant = plant;
		updateFrequencyRange();
	}
}