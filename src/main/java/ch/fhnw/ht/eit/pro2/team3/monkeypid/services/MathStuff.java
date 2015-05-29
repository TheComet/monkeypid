package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.TransferFunction;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 * Contains various common math functions we need.
 * @author Alex Murray
 */
public class MathStuff {

    /**
     * Returns a copy of the array with every element multiplied by the constant.
     * @param arr Array to multiply.
     * @param constant Constant to multiply.
     * @return New array.
     */
    public static double[] mul(double[] arr, double constant) {
        double[] ret = new double[arr.length];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = arr[i] * constant;
        }
        return ret;
    }

    /**
     * Returns the largest value in an array of doubles
     * @param arr The array to find the largest value of.
     * @return The largest value.
     */
    public static double max(double[] arr) {
        double largest = Double.MIN_VALUE;
        for(double value : arr) {
            if(value > largest) {
                largest = value;
            }
        }
        return largest;
    }
    
    public static double maxFromNegativeInfinity(double[] arr) {
        double largest = Double.NEGATIVE_INFINITY;
        for(double value : arr) {
            if(value != 0.0  && value > largest) {
                largest = value;
            }
        }
        return largest;
    }

    /**
     * Generates an array of linearly spaced values ranging from the start value to the end value with the specified
     * number of points.
     * @param startValue The start value.
     * @param endValue The end value.
     * @param nValues How many values should be generated.
     * @return Returns an array of doubles.
     */
    public static double[] linspace(double startValue, double endValue, int nValues){
        double step = (endValue - startValue)/(nValues-1);

        double[] res = new double[nValues];

        for (int i = 0; i < nValues; i++) {
            res[i] = step * i + startValue;
        }
        return res;
    }

    /**
     * Converts omega to the imaginary number s. (s = jw).
     * @param omega Omega.
     * @return Returns s.
     */
    public static Complex omegaToS(double omega) {
        return new Complex(0.0, omega);
    }

    /**
     * Computes the complex frequency response of a given transfer function.
     * @param g The transfer function.
     * @param omega Omega.
     * @return Complex frequency response.
     */
    public static Complex[] freqs(TransferFunction g, double[] omega) {
        Complex[] res = new Complex[omega.length];

        for (int i = 0; i < res.length; i++) {
            Complex s = omegaToS(omega[i]);
            Complex zaehler = polyVal(g.getNumeratorCoefficients(), s);
            Complex nenner = polyVal(g.getDenominatorCoefficients(), s);
            res[i] = zaehler.divide(nenner);
        }
        return res;
    }

    /**
     * Calculates the value of the polynomial poly  at the given X-Value of s
     * The coefficients of poly start with the highest order x^n.
     * The last element of poly ist x^0
     * @param poly The polynomial, for which the value is calculated
     * @param s The X-Value, which is inserted as X into the polynomial
     * @return the value of the polynomial poly at s
     */
    public static Complex polyVal(double[] poly, Complex s) {
        // If s is zero, the result will be 0. Apparently, apache commons
        // cannot raise the complex number 0+0j to any power without
        // resulting in NaN. For these reasons, we cannot rely on the
        // algorithm below to return the correct result.
        if(s.equals(new Complex(0))) {
            // since the calculation is a*s^n + b*s^(n-1) + ... + b*s^0
            // and we know that s = 0, the result will be b*s^0 = b
            return new Complex(poly[poly.length - 1]);
        }

        Complex res = new Complex(0);

        for (int i = 0; i < poly.length; i++) {
            Complex raised = s.pow(poly.length - i - 1);
            raised = raised.multiply(poly[i]);
            res=res.add(raised);
        }

        return res;
    }
    
    /**
     * Calculates the value of the polynomial poly  at the given X-Value of s
     * The coefficients of poly start with the highest order x^n.
     * The last element of poly ist x^0
     * Overloads the Method, the parameter poly, the polynomial-function
     * is now an array of complex-numbers
     * @param poly The polynomial, for which the value is calculated
     * @param s The X-Value, which is inserted as X into the polynomial
     * @return the value of the polynomial poly at s
     */
    public static Complex polyVal(Complex[] poly, Complex s) {
        // If s is zero, the result will be 0. Apparently, apache commons
        // cannot raise the complex number 0+0j to any power without
        // resulting in NaN. For these reasons, we cannot rely on the
        // algorithm below to return the correct result.
        if(s.equals(new Complex(0))) {
            // since the calculation is a*s^n + b*s^(n-1) + ... + b*s^0
            // and we know that s = 0, the result will be b*s^0 = b
            return poly[poly.length - 1];
        }

        Complex res = new Complex(0);

        for (int i = 0; i < poly.length; i++) {
            Complex raised = s.pow(poly.length - i - 1);
            raised = raised.multiply(poly[i]);
            res=res.add(raised);
        }

        return res;
    }

    /* WARNING This appears to be broken when used with two arrays with different sizes.
     * Use MathArrays.convolve from apache commons.
    public static double[] conv(double[] a, double[] b) {
        double[] res = new double[a.length + b.length - 1];
        for (int n = 0; n < res.length; n++) {
            for (int i = Math.max(0, n - a.length + 1); i <= Math.min(b.length - 1, n); i++) {
                res[n] += b[i] * a[n - i];
            }
        }
        return res;
    }*/

    /**
     * Returns a double array filled with ones.
     * @param length The number of ones to fill.
     * @return A double array.
     */
    public static double[] ones(int length) {
        double[] array = new double[length];
        for(int i = 0; i < length; i++) {
            array[i] = 1;
        }
        return array;
    }

    /**
     * Computes the inverse fast fourier transform of a function.
     * @param f A function in the frequency domain.
     * @return A function in the time domain.
     */
    public static Complex[] ifft(Complex[] f){
        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        return transformer.transform(f, TransformType.INVERSE);
    }

    /**
     * Returns the real parts of an array of complex numbers.
     * @param c A list of complex numbers.
     * @return A list of only the real parts.
     */
    public static double[] real(Complex[] c) {
        double[] ret = new double[c.length];
        for(int i = 0; i < c.length; i++) {
            ret[i] = c[i].getReal();
        }
        return ret;
    }

    /**
     * Returns the imaginary parts of an array of complex numbers.
     * @param c A list of complex numbers.
     * @return A list of only the imaginary parts.
     */
    public static double[] imag(Complex[] c) {
        double[] ret = new double[c.length];
        for(int i = 0; i < c.length; i++) {
            ret[i] = c[i].getImaginary();
        }
        return ret;
    }

    /**
     * Doubles the size of the array, then copies, mirrors, and conjugates all values into the second half of the array.
     * The middle element N/2 will be 0. This is required to compute the fourier transform and get a real result.
     * @param capitalH The function to prepare.
     * @return A new function ready for fft/ifft.
     */
    public static Complex[] symmetricMirrorConjugate(Complex[] capitalH) {
        Complex[] symmetric = new Complex[capitalH.length * 2];

        // fill first half with original array
        System.arraycopy(capitalH, 0, symmetric, 0, capitalH.length);

        // middle is 0
        symmetric[capitalH.length] = new Complex(0);

        // second half is the original array conjugated and mirrored
        int sourceIndex = capitalH.length - 1;
        for(int targetIndex = capitalH.length + 1; targetIndex < symmetric.length; targetIndex++) {
            symmetric[targetIndex] = capitalH[sourceIndex].conjugate();
            sourceIndex--;
        }

        return symmetric;
    }
    
    /**
     * Calculates the Step-Response of a Transfer-Function with the Numerator B and the Denominator A.
     * 1/fs is and the Number of sampling Points N is used to calculate the time-axis of the Step-Response.
     * The time-axis has N number of points.
     * See Matlab file "schrittResidue".
     * Uses the method residueSimple() to do a Partial-Fraction decomposition
     * @param B The Numerator-Polynomial of the Transfer-Function (for which the Step-Response is calculated).
     * @param A the Denominator-Polynomial of the Transfer-Function (for which the Step-Response is calculated).
     * @param fs The fs of the Transfer-Function.
     * @param N The number of sampling/x-axis points.
     * @return {y,t} An array with the two double arrays y, the y-values and t, the time-values of the Step-Response.
     */
    public static Object[] stepResidue(double[] B, double[] A, double fs, int N){
    	//Matlab-Function in comments with Brackets()
    	//get the time from fs
    	double T = 1/fs;
    	//Partial-Fraction decomposition
    	Object[] resdiueResult = residueSimple(new TransferFunction(B, A));
    	Complex[] residues = (Complex[]) resdiueResult[0];
		Complex[] pole = (Complex[]) resdiueResult[1];
		double constantK = (double) resdiueResult[2];

        int numOfPoints = 1024;

		//y-values
		//zeros()
		double[] y = new double[numOfPoints]; //initial all elements of the double array are zero
		/*
		for (int i = 0; i < y.length; i++) {
			y[i] = 0.0;
		}
		*/
		
		//if constant terme K available, use it (as initial y-value)
		//if K not empty, here tested with not 0.0, <- should be improved, better solution searched
		if(constantK != 0.0){
			y[0] = constantK;
		}
		
		//time-axis, maximum-time-value depends on N and T
		double[] t = linspace(0, (N-1)*T, numOfPoints);
		double fsFactor = (N*T)/numOfPoints;

		//Calculate impulseResponse (stepResponse)
		for(int k = 0; k < residues.length; k++){
			for(int m = 0; m < y.length; m++){
				y[m] = y[m] + ((new Complex(pole[k].getReal(), pole[k].getImaginary()).multiply(t[m])).exp().multiply(new Complex(residues[k].getReal(),residues[k].getImaginary()))).getReal()*fsFactor; // /fs;
			}
		}
		
		//sume up all values from the impulseResponse -> this is the stepResponse
		for (int i = 1; i < y.length; i++) {
			y[i] = y[i] + y[i-1];
		}
		
    	return new Object[]{y, t};    	
    }
    

    /**
     * Converts a partial-fraction g into the residues R, poles P and constant-term K
     * See Matlab file "residueSimple.m" or Matlab buildt in function residue()
     * @param g The TransferFunction which is the partial-fraction which is converted
     * @return R, P, K Residues P, Poles P and Constant-Term K
     */
    public static Object[] residueSimple(TransferFunction g){
		double constantK = 0.0;
		
		//Get the Numerator and Denominator from the TransferFunction
		double[] Numerator = g.getNumeratorCoefficients();
		double[] Denominator = g.getDenominatorCoefficients();
		
		//remove leading Zeros of both polynomial functions
		Numerator = removeLeadingZeros(Numerator);
		Denominator = removeLeadingZeros(Denominator);
		
		//calculate the degree of the polynomial functions
		int degreeNumerator = Numerator.length -1;
		int degreeDenominator = Denominator.length -1;
		
		//Have Numerator and Denominator the same degree? if yes -> calculate the constant factor K
		if(degreeNumerator==degreeDenominator){
			constantK = Numerator[0]/Denominator[0];
			for (int i = 0; i < Numerator.length; i++) {
				Numerator[i]  = Numerator[i] - constantK*Denominator[i];
			}
		}
		else{
			constantK = 0.0; 
		}
		
		//get the roots of the Denominator polynomial function
		Complex[] poles = roots(Denominator);
		//remove imaginary part if imaginary part is smaller than 1e-15, 
		//probably the trigger value 1e-15 should be lowered
        /*
		for (int i = 0; i < poles.length; i++) {
			if(Math.abs(poles[i].getImaginary()) < 1e-50){
				poles[i] = new Complex(poles[i].getReal(), 0);
			}
		}
		*/
		//Attention: order of the roots is not alwayse the same as in Matlab, but no problem here
		
		//create an array of empty/zero residues
		//zeros()
		Complex[] residues = new Complex[degreeDenominator];
		for (int i = 0; i < residues.length; i++) {
			residues[i] = new Complex(0);
		}
		
		//calculate the residues
		for (int m = 0; m < degreeDenominator; m++) {
			//Calculate Denominator polynomial without m-th root
			
			//copy P in smallP
			Complex[] tempPoles = new Complex[poles.length];
			
			//copy every element from second of P in smallP
			System.arraycopy(poles, 0, tempPoles, 0, poles.length);
			//shift the elements of tempPoles to the left (not all elements, uses some magic[see Matlab-Files])
			if((degreeDenominator-m-1) > 0){
				System.arraycopy(tempPoles, m+1, tempPoles, m, degreeDenominator-m-1);
			}
			//removes the last elemenet of the tempPolse, after left-shifting, this is no longer used
			tempPoles = ArrayUtils.remove(tempPoles, tempPoles.length-1);
			//generate the aTile
			Complex[] aTilde = poly(tempPoles); 
			
			/*
			//wrong decision: aTilde has sometimes imaginary-part, which can't be ignored
			double[] aTildeReal = new double[aTilde.length];
			//aTilde is real (no imaginary part) -> get only real from aTilde
			for (int i = 0; i < aTildeReal.length; i++) {
				aTildeReal[i] = aTilde[i].getReal();
			}
			*/
			
			//calculate Residue at position m of the residues-array
			Complex pvB = polyVal(Numerator, poles[m]);
			Complex pvA = polyVal(aTilde, poles[m]);
			Complex pvD = pvB.divide(pvA);
			residues[m] = pvD.divide(Denominator[0]);
		}
		
		//remove imaginary part if imaginary part is smaller than 1e-15
		/*
        for (int i = 0; i < residues.length; i++) {
			if(Math.abs(residues[i].getImaginary()) < 1e-50){
				residues[i] = new Complex(residues[i].getReal(), 0);
			}
		}
		*/
		
    	return new Object[]{residues,poles,constantK};    	
    }
    
    

    /**
     * Computes the polynomial coefficients with the specified roots.
     * This was ported from matlab's poly() function
     * Type ">> edit poly" and scroll to line 35.
     * @param roots Roots.
     * @return Polynomial coefficients.
     */
    public static double[] poly(double[] roots) {
        // this was ported from matlab's poly() function
        // type ">> edit poly" and scroll to line 35.
        double[] coefficients = new double[roots.length + 1];
        coefficients[0] = 1.0;
        double[] temp = new double[roots.length + 1];

        for (double root : roots) {
            // multiply coefficients with current root and store in temp buffer
            for (int i = 0; i < coefficients.length; i++) {
                temp[i] = root * coefficients[i];
            }
            // subtract temp buffer from coefficients
            for (int i = 1; i < coefficients.length; i++) { // from 1 to j+1
                coefficients[i] -= temp[i - 1];
            }
        }

        return coefficients;
    }

    /**
     * Computes the polynomial coefficients with the specified roots.
     * This was ported from matlab's poly() function
     * Type ">> edit poly" and scroll to line 35.
     * Method overloaded to calculate also the polynomial coefficients of complex roots
     * @param roots Roots.
     * @return Polynomial coefficients.
     */
    public static Complex[] poly(Complex[] roots) {
        // this was ported from matlab's poly() function
        // type ">> edit poly" and scroll to line 35.
        Complex[] coefficients = new Complex[roots.length + 1];
        for (int i = 0; i < coefficients.length; i++) {
			coefficients[i] = new Complex(0.0);
		}
        coefficients[0] = new Complex(1.0);
        
        Complex[] temp = new Complex[roots.length + 1];
        for (int i = 0; i < temp.length; i++) {
			temp[i] = new Complex(0.0);
		}

        for (Complex root : roots) {
            // multiply coefficients with current root and store in temp buffer
            for (int i = 0; i < coefficients.length; i++) {
                temp[i] = root.multiply(coefficients[i]);
            }
            // subtract temp buffer from coefficients
            for (int i = 1; i < coefficients.length; i++) { // from 1 to j+1
                coefficients[i] = coefficients[i].subtract(temp[i - 1]);
            }
        }

        return coefficients;
    }

    /**
     * Removes the leading zeros from an array.
     * @param array The array.
     * @return A new array with leading zeros removed.
     */
    public static double[] removeLeadingZeros(double[] array){
    	int startIndex = 0;
		//remove leading Zeros
		for (int i = 0; i < array.length; i++) {
			if(array[i] != 0){
				startIndex = i;
				break;
			}
		}
		
		double[] polynomLeadingZerosRemoved = new double[array.length - startIndex];
        System.arraycopy(array, startIndex, polynomLeadingZerosRemoved, 0, polynomLeadingZerosRemoved.length);
		return polynomLeadingZerosRemoved;
    }

    /**
     * taken from pdf Fachinput_Schrittantwort.pdf
     * @param p Polynomial coefficients
     * @return Roots Attention the Roots have sometimes not the same order as in Matlab
     * 			(but no problem, they are only roots ;-))
     */
    public static Complex[] roots(double[] p) {
    	final LaguerreSolver solver = new LaguerreSolver();
    	double[] flip = new double[p.length];
    	// To be conform with Matlab ...
    	for (int i = 0; i < flip.length; i++) {
    	flip[p.length - i - 1] = p[i];
    	}
    	Complex[] complexRootsReverse = solver.solveAllComplex(flip, 1e10);
    	Complex[] complexRoots = new Complex[complexRootsReverse.length];
        //Flip order of roots
    	for (int i = 0; i < complexRoots.length; i++) {
			complexRoots[i] = complexRootsReverse[complexRoots.length - i -1];
		}
    	return complexRoots;
    }
    
}
