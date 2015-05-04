package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.io.Serializable;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class TransferFunctionClosedLoop {

	private double[] B;
	private double[] A;
	
	static final FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
	
	
	public TransferFunctionClosedLoop(TransferFunction hS, TransferFunction hR) {
		B = MathStuff.conv(hS.getB(), hR.getB());
		A = MathStuff.conv(hS.getA(), hR.getA());
		
		/*System.out.println("As"+hS.getA()[0]);
		System.out.println("Bs"+hS.getB()[0]);
		System.out.println("Br"+hR.getB()[0]);
		System.out.println("Ar"+hR.getA()[0]);*/
		
		System.out.println("laenge von A " + A.length + " laenge von B " + B.length);
		
		for (int i = 0; i < B.length ; i++) {
			A[A.length - B.length + i] += B[i];
		}
		
		//Test 
		/*for (int i = 0; i < 10; i++) {
			System.out.println(linspace(0,11, 10)[i]);
		}*/
		
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
		
		
		Complex[] HConcat = new Complex[H.length];
		
		for (int i = 0; i < H.length/2; i++) {
			HConcat[i]=H[i];
		}
		HConcat[H.length/2]=new Complex(0, 0);
		for (int j = HConcat.length/2+1; j < HConcat.length; j++) {
			HConcat[j] = HmirrorConjugate[j-(HConcat.length/2+1)];
		}
		
		

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
	
	
	
	public static final Complex[] ifft(Complex[] f){
		double log2f = Math.log(f.length)/Math.log(2);
		int minLength =(int)(Math.pow(2, Math.ceil(log2f)));
		int difLength = minLength - f.length;
		
		//Complex[] res = transformer.transform(f, TransformType.INVERSE);
		//return res;
		return null;
	}
	

}
