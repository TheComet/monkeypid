package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;


import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.TransferFunction;

import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.ArrayList;

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

    public static double[] conv(double[] a, double[] b) {
        double[] res = new double[a.length + b.length - 1];
        for (int n = 0; n < res.length; n++) {
            for (int i = Math.max(0, n - a.length + 1); i <= Math.min(b.length - 1, n); i++) {
                res[n] += b[i] * a[n - i];
            }
        }
        return res;
    }

    public static double[] ones(int length) {
        double[] array = new double[length];
        for(int i = 0; i < length; i++) {
            array[i] = 1;
        }
        return array;
    }

    public static Complex[] ifft(Complex[] f){
        double log2f = Math.log(f.length)/Math.log(2);
        int minLength =(int)(Math.pow(2, Math.ceil(log2f)));
        Complex[] powerOfTwo = new Complex[minLength];
        System.arraycopy(f, 0, powerOfTwo, 0, f.length);
        for(int i = f.length; i < minLength; i++) {
            powerOfTwo[i] = new Complex(0);
        }

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        return transformer.transform(powerOfTwo, TransformType.INVERSE);
    }

    public static double[] real(Complex[] c) {
        double[] ret = new double[c.length];
        for(int i = 0; i < c.length; i++) {
            ret[i] = c[i].getReal();
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
    
    //m
    public static Object[] residueSimple(TransferFunction g){
		Complex R = new Complex(0);
		Complex P = new Complex(0);
		Complex K = new Complex(0);
		
		double[] B = g.getNumeratorCoefficients();
		double[] A = g.getDenominatorCoefficients();
		
		int startIndex = 0;
		//remove leading Zeros
		for (int i = 0; i < B.length; i++) {
			if(B[i] != 0){
				startIndex = i;
				break;
			}
		}
		
		double[] BzerosRemoved = new double[B.length-startIndex];
		for (int i = 0; i < BzerosRemoved.length; i++) {
			BzerosRemoved[i] = B[startIndex + i];
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
    
    //taken from pdf Fachinput_Schrittantwort.pdf
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
            complexRoots[i] = complexRootsReverse[complexRoots.length - i - 1];
        }
        return complexRootsReverse;
    }
}
