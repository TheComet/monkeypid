package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.TransferFunction;

import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

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

    public static double[] linspace(double startValue, double endValue, int nValues){
        double step = (endValue - startValue)/(nValues-1);

        double[] res = new double[nValues];

        for (int i = 0; i < nValues; i++) {
            res[i] = step * i + startValue;
        }
        return res;
    }

    public static Complex omegaToS(double omega) {
        return new Complex(0.0, omega);
    }

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

    public static double[] ones(int length) {
        double[] array = new double[length];
        for(int i = 0; i < length; i++) {
            array[i] = 1;
        }
        return array;
    }

    public static Complex[] ifft(Complex[] f){
        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        return transformer.transform(f, TransformType.INVERSE);
    }

    public static double[] real(Complex[] c) {
        double[] ret = new double[c.length];
        for(int i = 0; i < c.length; i++) {
            ret[i] = c[i].getReal();
        }
        return ret;
    }

    public static double[] imag(Complex[] c) {
        double[] ret = new double[c.length];
        for(int i = 0; i < c.length; i++) {
            ret[i] = c[i].getImaginary();
        }
        return ret;
    }

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
    
    //residue help function
    public static Object[] residueSimple(TransferFunction g){
		double K = 0.0;
		
		double[] Numerator = g.getNumeratorCoefficients();
		double[] Denominator = g.getDenominatorCoefficients();
		
		Numerator = removeLeadingZeros(Numerator);
		Denominator = removeLeadingZeros(Denominator);
		
		int N = Numerator.length -1;
		int M = Denominator.length -1;
		
		//Have Numerator and Denominator the same Order? if yes -> calculate K
		if(N==M){
			K = Numerator[0]/Denominator[0];
			for (int i = 0; i < Numerator.length; i++) {
				Numerator[i]  = Numerator[i] - K*Denominator[i];
			}
		}
		else{
			K = 0.0;
		}
		
		Complex[] P = roots(Denominator);
		//zeros(M,1)
		Complex[] R = new Complex[M];
		for (int i = 0; i < R.length; i++) {
			R[i] = new Complex(0);
		}
		
		for (int m = 0; m < M; m++) {
			//Calculate Denominator polynominal wighout m-th root
			
			//copy P in smallP
			Complex[] smallP = new Complex[P.length-1];
			
			/*
			for (int i = 0; i < smallP.length; i++) {
				smallP[i] = P[i];
			}
			//shift smallP left one cell
			for (int j = m; j < M; j++) {
				smallP[j] = smallP[j+1];
			}
			//remove last array cell
			smallP = ArrayUtils.remove(smallP, smallP.length-1); //works?
			*/
			
			//copy every element from second of P in smallP
			for (int i = 0; i < smallP.length; i++) {
				smallP[i] = P[i+1];
			}
			
			Complex[] pa = poly(smallP);
			double[] paReal = new double[pa.length];
			//pa is real (no imaginary part) -> get only real from pa
			for (int i = 0; i < paReal.length; i++) {
				paReal[i] = pa[i].getReal();
			}
			
			//calculate Residues
			Complex pvB = polyVal(Numerator, P[m]);
			Complex pvA = polyVal(paReal, P[m]);
			Complex pvD = pvB.divide(pvA);
			R[m] = pvD.divide(Denominator[0]);
		}
		
		
    	return new Object[]{R,P,K};    	
    }

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
    
    //complex version
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
    
    //remove leading zeros
    public static final double[] removeLeadingZeros(double[] polynom){
    	int startIndex = 0;
		//remove leading Zeros
		for (int i = 0; i < polynom.length; i++) {
			if(polynom[i] != 0){
				startIndex = i;
				break;
			}
		}
		
		double[] polynomLeadingZerosRemoved = new double[polynom.length-startIndex];
		for (int i = 0; i < polynomLeadingZerosRemoved.length; i++) {
			polynomLeadingZerosRemoved[i] = polynom[startIndex + i];
		}
		return polynomLeadingZerosRemoved;
    }
    
    // taken from pdf Fachinput_Schrittantwort.pdf
    public static final Complex[] roots(double[] p) {
    	final LaguerreSolver solver = new LaguerreSolver();
    	double[] flip = new double[p.length];
    	// To be conform with Matlab ...
    	for (int i = 0; i < flip.length; i++) {
    	flip[p.length - i - 1] = p[i];
    	}
    	Complex[] complexRootsReverse = solver.solveAllComplex(flip, 0.0);
    	Complex[] complexRoots = new Complex[complexRootsReverse.length];
    	for (int i = 0; i < complexRoots.length; i++) {
			complexRoots[i] = complexRootsReverse[complexRoots.length - i -1];
		}
    	return complexRoots;
    }
    
}
