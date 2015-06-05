package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;

/**
 * Represents the plant component in a closed loop system. The plant is the thing to be controlled, and is described by
 * the three parameters Tu, Tg, and Ks.
 *
 * A controller calculator can use the plant to calculate a suitable controller, which can then be used to form a
 * closed loop system.
 * @author Alex Murray
 */
public class Plant {

	private double ks = 0.0;
	private double tu = 0.0;
	private double tg = 0.0;
	private SaniCurves sani;
	private double[] timeConstants;
	private TransferFunction transferFunction = null;

	/**
	 * Constructs a plant using the parameters Tu, Tg, and Ks. Additionally, a SaniCurves object is required to
	 * calculate the time constants array, which is then used to calculate the plant's transfer function.
	 * @param tu Plant parameter Tu.
	 * @param tg Plant parameter Tg.
	 * @param ks Plant parameter ks.
	 * @param sani
	 */
	public Plant(double tu, double tg, double ks, SaniCurves sani) {
		this.sani = sani;
		setParameters(tu, tg, ks);
	}

	/**
	 * Sets the parameters of the plant and causes the time constants array and the transfer function to be
	 * re-calculated.
	 * @param tu Plant parameter Tu.
	 * @param tg Plant parameter Tg.
	 * @param ks Plant parameter Ks.
	 */
	public void setParameters(double tu, double tg, double ks) {
		this.tu = tu;
		this.tg = tg;
		this.ks = ks;
		//timeConstants = sani.calculateTimeConstants(tu, tg);
		timeConstants = sani.calculateTimeConstants(tu, tg);
		calculateTransferFunction();
	}

	/**
	 * Gets the plant parameter Tu.
	 * @return Tu.
	 */
	public double getTu() {
		return this.tu;
	}

	/**
	 * Gets the plant parameter Tg.
	 * @return Tg.
	 */
	public double getTg() {
		return this.tg;
	}

	/**
	 * Gets the plant parameter Ks.
	 * @return Ks.
	 */
	public double getKs() {
		return this.ks;
	}

	/**
	 * Gets the time constants array calculated using SaniCurves.
	 * @return The time constants as a double array.
	 */
	public double[] getTimeConstants() {
		return this.timeConstants;
	}

	/**
	 * Gets the order of the plant. This number will be between 2 and 8.
	 * @return The order of the plant as int
	 */
	public int getOrder() {
		// the order is equal to the number of time constants.
		return timeConstants.length;
	}

	/**
	 * Gets the plant's transfer function.
	 * @return The transfer function of the plant.
	 */
	public TransferFunction getTransferFunction() {
		return transferFunction;
	}

	/**
	 * Calculates the plant's transfer function.
	 */
	private void calculateTransferFunction() {

		// produces the Numerator and Denominator Polynomial of the plant
		//   Bs = Ks; %Numerator
		//   As = %Denominator: poly([-1/T1 -1/T2 -1/T3 -1/T4])*(T1*T2*T3*T4)
		double[] numeratorCoefficients = new double[timeConstants.length];
		double multiplicator = 1;
		for(int k = 0; k < numeratorCoefficients.length; k++) {
			numeratorCoefficients[k] = -1.0 / timeConstants[k];
			multiplicator *= timeConstants[k];
		}

		transferFunction = new TransferFunction(
				new double[] {ks},
				MathStuff.mul(MathStuff.poly(numeratorCoefficients), multiplicator)
		);
	}
}
