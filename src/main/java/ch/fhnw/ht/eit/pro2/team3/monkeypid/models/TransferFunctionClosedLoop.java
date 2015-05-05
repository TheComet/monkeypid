package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;

public class TransferFunctionClosedLoop {

	private double[] B;
	private double[] A;
	
	static final FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
	
	
	public TransferFunctionClosedLoop(TransferFunction hS, TransferFunction hR) {
		B = MathStuff.conv(hS.getB(), hR.getB());
		A = MathStuff.conv(hS.getA(), hR.getA());
		
		for (int i = 0; i < B.length ; i++) {
			A[A.length - B.length + i] += B[i];
		}
		
		schrittIfft(new TransferFunction(A, B), 100, 10);

	}
	
	
	public double[][] calculate(){
		return null;
		
	}
	
	public double[] getA(){
		return A;
	}
	public double[] getB(){
		return B;
	}
	
	public double[][] schrittIfft(TransferFunction g, double fs, int N){
		double t = 1/fs;
		
		double [] omega = MathStuff.linspace(0, fs * Math.PI, N / 2);
		
		Complex[] H = MathStuff.freqs(g, omega);
		
		Complex[] HmirrorConjugate = new Complex[H.length];
		
		//copy H in HmirrCon 
		for (int i = 0; i < HmirrorConjugate.length; i++) {
			HmirrorConjugate[i] = new Complex(H[i].getReal(), H[i].getImaginary());
		}

		//mirror
		for (int i = 0; i < HmirrorConjugate.length/2; i++) {
			Complex temp = HmirrorConjugate[i];
			HmirrorConjugate[i] = HmirrorConjugate[HmirrorConjugate.length - i -1];
			HmirrorConjugate[HmirrorConjugate.length - i -1] = temp;
		}
		
		//conj
		for (int i = 0; i < HmirrorConjugate.length; i++) {
			HmirrorConjugate[i] = new Complex(HmirrorConjugate[i].getReal(),-HmirrorConjugate[i].getImaginary());
		}
				
		//H2 = ArrayUtils.remove(H2, H2.length-1);
		//H2 = ArrayUtils.remove(H2, H2.length-1);
		
		//Complex[] H0 = new Complex[1];
		//H0[0] = new Complex(0);
		
		//Complex[] Hall = ArrayUtils.<Complex>addAll(H, H0, HmirrCon);
		//Complex[] Hall = ObjectA (H, H0, HmirrCon);
		
		
		Complex[] HConcat = new Complex[H.length * 2];

        System.arraycopy(H, 0, HConcat, 0, H.length);
        System.arraycopy(HmirrorConjugate, 0, HConcat, H.length + 1, HmirrorConjugate.length - 1);
		HConcat[H.length] = new Complex(0, 0);

		Complex[] h = MathStuff.ifft(HConcat);

		/*System.out.println("H");
		for (int i = 0; i < H.length; i++) {
			System.out.println("Real " + H[i].re + " Imag " + H[i].im);
		}
		System.out.println("HmirrCon");
		for (int i = 0; i < HmirrCon.length; i++) {
			System.out.println("Real " + HmirrCon[i].re + " Imag " + HmirrCon[i].im);
		}*/
		
		//Complex[] transferDomain = ifft(HConcat);
		
		/*
		Complex[] y = new Complex[transferDomain.length];
		Complex temp = new Complex(0,0);
		for (int i = 0; i < transferDomain.length; i++) {
			temp = transferDomain[i].add(temp);
			y[i] = temp;
		}

		for (int i = 0; i < y.length; i++) {
			System.out.println("conv " + y);
		}		
		*/
		
		return null;
	}
	

}
