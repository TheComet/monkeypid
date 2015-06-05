package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

/**
 * Defines a transfer function's numerator and denominator polynomial coefficients.
 * @author Simon Wyss
 */
public class TransferFunction {
	private double[] denominatorCoefficients;
	private double[] numeratorCoefficients;

	public TransferFunction(double[] numeratorCoefficients, double[] denominatorCoefficients){
		this.denominatorCoefficients = denominatorCoefficients;
		this.numeratorCoefficients = numeratorCoefficients;
	}

	/**
	 * Sets the numerator coefficients.
	 * The coefficients should be in descending order, i.e. the last
	 * coefficient is the constant value, the first coefficient is the product
	 * of the greatest power.
	 * @param coeff A double array containing the coefficients.
	 */
	public void setNumeratortorCoefficients(double[] coeff) {
		this.numeratorCoefficients = coeff;
	}

	/**
	 * Sets the denominator coefficients.
	 * The coefficients should be in descending order, i.e. the last
	 * coefficient is the constant value, the first coefficient is the product
	 * of the greatest power.
	 * @param coeff A double array containing the coefficients.
	 */
	public void setDenominatorCoefficients(double[] coeff) {
		this.denominatorCoefficients = coeff;
	}

	/**
	 *  Gets the array of numerator coefficients.
	 * @return The coefficients are returned in descending order, i.e. the last
	 * coefficient is the constant value, the first coefficient is the product
	 * of the greatest power.
	 */
	public double[] getNumeratorCoefficients(){
		return numeratorCoefficients;
	}

	/**
	 *  Gets the array of denominator coefficients.
	 * @return The coefficients are returned in descending order, i.e. the last
	 * coefficient is the constant value, the first coefficient is the product
	 * of the greatest power.
	 */
	public double[] getDenominatorCoefficients(){
		return denominatorCoefficients;
	}

}
