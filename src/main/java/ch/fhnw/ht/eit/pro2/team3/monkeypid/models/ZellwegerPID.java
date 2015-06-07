package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Implements the Zellweger method for calculating PID controllers.
 * @author Alex Murray
 */
public class ZellwegerPID extends AbstractZellweger {
	private static final double angleOfInflection = -135.0;
	private double beta = 0.0;

	@Override
	public void setAngleOfInflectionOffset(double angleOfInflectionOffset) {
		setAngleOfInflection(angleOfInflection + angleOfInflectionOffset);
	}

	/**
	 * Constructs a new Zellweger calculator using the specified plant.
	 * @param plant The plant to calculate a controller for.
	 * @param phaseMargin The phase margin to use during angle lookups on the phase of the open loop.
	 */
	public ZellwegerPID(Plant plant, double phaseMargin) {
		super(plant, phaseMargin);
		setAngleOfInflection(-135.0);
	}

	/**
	 * Constructs a new Zellweger calculator using the specified plant.
	 * Overloads Constructor for additional offset for the angleOfInflection
	 * This can be used to adjust the the rise of the stepResponse of this Controller
	 * @param plant The plant to calculate a controller for.
	 * @param phaseMargin The phase margin to use during angle lookups on the phase of the open loop.
	 * @param angleOfInflectionOffset The offset for the angleOfInflection.
	 */
	public ZellwegerPID(Plant plant, double phaseMargin, double angleOfInflectionOffset) {
		super(plant, phaseMargin);
		setAngleOfInflection(-135.0 + angleOfInflection);
	}

	/**
	 * Gets the calculated beta parameter.
	 * @return Beta.
	 */
	public double getBeta() {
		return beta;
	}

	/**
	 * Uses a binary search to approximate the angle on the phase of the open loop. Accuracy can be set by calling the
	 * method setMaxIterations().
	 * @return Returns the angle at the specified phase of the open loop.
	 */
	private double findAngleOnOpenLoopPhase(double tnk, double tvk, double tp) {

		// find phiDamping on the phase of the open loop
		double topFreq = endFreq;
		double bottomFreq = startFreq;
		double actualFreq = (topFreq + bottomFreq) / 2.0;
		for(int i = 0; i != maxIterations; i++) {
			double phiOpenLoopBuffer = calculatePlantPhase(actualFreq) +
					calculateControllerPhase(actualFreq, tnk, tvk, tp);
			if(phiOpenLoopBuffer < phiDamping) {
				topFreq = actualFreq;
				actualFreq = (topFreq + bottomFreq) / 2.0;
			} else if(phiOpenLoopBuffer > phiDamping) {
				bottomFreq = actualFreq;
				actualFreq = (topFreq + bottomFreq) / 2.0;
			}
		}

		return actualFreq;
	}

	/**
	 * Calculates the phase of a PID controller (without control path)
	 * @param omega Omega
	 * @param tnk Bode parameter Tnk
	 * @param tvk Bode parameter Tvk
	 * @param tp Bode parameter Tp
	 * @return Returns the phase in degrees. atan(w*Tnk)+atan(w*Tvk)-atan(w*Tp)-pi/2.
	 */
	private double calculateControllerPhase(double omega, double tnk, double tvk, double tp) {
		double phi = Math.atan(omega * tnk);
		phi += Math.atan(omega * tvk);
		phi -= Math.atan(omega * tp);
		phi -= Math.PI / 2.0;
		return phi * 180 / Math.PI; // convert to degree
	}

	/**
	 * Calculates the amplitude of a PID controller
	 * @param omega Omega
	 * @param tnk Bode parameter Tnk
	 * @param tvk Bode parameter Tvk
	 * @param tp Bode parameter Tp
	 * @return (sqrt(1+(w*Tnk)^2)*sqrt(1+(w*Tvk)^2))/((w*Tnk)*sqrt(1+(w*Tp)^2))
	 */
	private double calculateControllerAmplitude(double omega, double tnk, double tvk, double tp) {
		double numerator = Math.sqrt(1.0 + Math.pow(omega * tnk, 2.0));
		numerator *= Math.sqrt(1.0 + Math.pow(omega * tvk, 2.0));

		double denominator = omega * tnk * Math.sqrt(1.0 + Math.pow(omega * tp, 2.0));

		return numerator / denominator;
	}

	/**
	 * Calculates calculateBeta for Tnk and Twk
	 *
	 * Calculation is:
	 * Z = -omegaInflection * (-(atan(w*T1)+atan(w*T2)+atan(w*Tc)));
	 * Beta = 1/Z - (+) sqrt(1/Z^2 - 1);
	 * eventually use positive or negative solution of sqrt()
	 *   ATTENTION: Beta has to be in the interval ]0,1]
	 *   For Z > 1 -> set Z to 1 -> Beta is 1
	 * @param omegaInflection Omega of the found angle phiPID
	 * @return Returns calculateBeta.
	 */
	private double calculateBeta(double omegaInflection) {
		double phiBuffer = 0;
		for(double timeConstant : plant.getTimeConstants()) {
			phiBuffer -= timeConstant / (1.0 + Math.pow(omegaInflection * timeConstant, 2.0));
		}

		double z = -omegaInflection * phiBuffer - 0.5;
		z = Math.min(z, 1.0); // clamp z to 1.0 so no complex betas are calculated

		return 1.0 / z - Math.sqrt(1.0 / Math.pow(z, 2.0) - 1.0);
	}

	/**
	 * Converts the bode parameters to the controller parameters
	 * @param tnk Bode parameter Tnk
	 * @param tvk Bode parameter Tvk
	 * @param tp Bode parameter Tp
	 * @param krk Bode parameter Krk
	 * @return Returns a double array of Tn, Tv, Kr (in that order), where Tn, Tv, and Kr are
	 * calculated as follows:
	 *   Tn = Tnk+Tvk-Tp;
	 *   Tv = (Tnk*Tvk)/(Tnk+Tvk-Tp) - Tp;
	 *   Kr = Krk*(1+Tvk/Tnk);
	 */
	private double[] bodeToController(double tnk, double tvk, double tp, double krk) {
		double[] tntvkr = new double[3]; // return Tn, Tv, and Kr in an array
		tntvkr[0] = tnk + tvk - tp;
		tntvkr[1] = (tnk * tvk) / tntvkr[0] - tp;
		tntvkr[2] = krk * (tnk + tvk - tp) / tnk;
		return tntvkr;
	}

	/**
	 * Gets the name of this calculator. The names are stored in a global class called CalculatorNames.
	 * @return The name of this controller.
	 */
	@Override
	public String getName() {
		return CalculatorNames.ZELLWEGER_PID;
	}

	/**
	 * Gets the render colour of this calculator. The colours are stored in a global class called RenderColors.
	 * @return The render color.
	 */
	@Override
	public Color getColor() {
		return RenderColors.ZELLWEGER_PID;
	}

	/**
	 * Calculates the appropriate controller for the specified plant.
	 * @return Returns a new PID controller.
	 */
	@Override
	protected final AbstractController calculate() {

		// find angleOfInflection on the phase of the control path
		double omegaInflection = findAngleOnPlantPhase();

		beta = calculateBeta(omegaInflection);

		double tnk = 1.0 / (omegaInflection * beta);
		double tvk = beta / omegaInflection;
		double tp = tvk * parasiticTimeConstantFactor; // Tp is one decade higher than Tvk
		tp = beautifyTpSoNiklausIsHappy(tp);

		// calculate Tn, Tv, Kr and create controller
		double[] tntvkr = calculateTnTvKr(tnk, tvk, tp);
		AbstractController controller = new ControllerPID(getName(), tntvkr[0], tntvkr[1], tntvkr[2], tp);

		// see issue #7 - calculate minimum and maximum Kr for iterative approximation of overswing
		double oldPhiDamping = phiDamping; // we're modifying phiDamping by setting a new phase margin. This has to be
										   // restored once we're done
		setPhaseMargin(30); // high overswing
		controller.setMaxKr(calculateTnTvKr(tnk, tvk, tp)[2]);
		setPhaseMargin(90); // low overswing
		controller.setMinKr(calculateTnTvKr(tnk, tvk, tp)[2]);
		phiDamping = oldPhiDamping; // restore

		return controller;
	}

	private double[] calculateTnTvKr(double tnk, double tvk, double tp) {

		// find phiDamping on the phase of the open loop
		double omegaDamping = findAngleOnOpenLoopPhase(tnk, tvk, tp);

		// amplitude of the open loop at the omegaDamping frequency
		double ampOpenLoopKr = calculatePlantAmplitude(omegaDamping) * calculateControllerAmplitude(omegaDamping, tnk, tvk, tp);

		// Kr is the reciprocal of the amplitude at omegaDamping
		double krk = 1.0 / ampOpenLoopKr;

		return bodeToController(tnk, tvk, tp, krk);
	}
}