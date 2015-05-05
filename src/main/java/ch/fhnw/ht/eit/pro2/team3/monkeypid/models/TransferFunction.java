package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class TransferFunction {
	private double[] denominatorCoefficients;
	private double[] numeratorCoefficients;

	public TransferFunction(double[] numeratorCoefficients, double[] denominatorCoefficients){
		this.denominatorCoefficients = denominatorCoefficients;
		this.numeratorCoefficients = numeratorCoefficients;
	}

    /**
     * @brief Gets the array of denominator coefficients.
     * @return The coefficients are returned in descending order, i.e. the last
     * coefficient is the constant value, the first coefficient is the product
     * of the greatest power.
     */
	public double[] getDenominatorCoefficients(){
		return denominatorCoefficients;
	}

    /**
     * @brief Gets the array of numerator coefficients.
     * @return The coefficients are returned in descending order, i.e. the last
     * coefficient is the constant value, the first coefficient is the product
     * of the greatest power.
     */
	public double[] getNumeratorCoefficients(){
		return numeratorCoefficients;
	}

}
