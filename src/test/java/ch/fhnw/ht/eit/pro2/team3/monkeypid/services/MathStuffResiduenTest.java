package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;

import static org.junit.Assert.*;

import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.TransferFunction;

public class MathStuffResiduenTest {
	double[] B = {4.0812, 1.1400};
	double[] A = {7.4902, 33.6613, 46.4843, 23.2772, 7.6612, 1.1400};
	
	double[] Bzeros = {0.0, 0.0, 0.0, 4.0812, 1.1400};
	
	double[] Bequal = {4.0812, 1.1400};
	double[] Aequal = {7.4902, 33.6613};
	
	double[] BequalExpected = {0,  -17.2011};

	@Test
	public void testResidueSimpleRemoveLeadingZeros() {
				
		double AremovedLeadingZeros[] = MathStuff.removeLeadingZeros(Bzeros);
		
		assertArrayEquals(B, AremovedLeadingZeros, 0.01);
	}
	
	@Test
	public void testResidueSimpleCalculateK() {
		
		//calculate Order of Numerator (Zähler) and Denominator (Nenner)
		int N = Bequal.length -1;
		int M = Aequal.length -1;
		
		assertEquals(1, N);
		assertEquals(1, M);
		
		double K = 0.0;
		
		//Have Numerator and Denominator the same Order? -> calculate K
		if(N==M){
			K = Bequal[0]/Aequal[0];
			for (int i = 0; i < Bequal.length; i++) {
				Bequal[i]  = Bequal[i] - K*Aequal[i];
			}
		}
		else{
			K = 0.0;
		}
		
		assertEquals(0.5449, K, 0.001);
		assertArrayEquals(BequalExpected, Bequal, 0.001);
		
	}

	@Test
	public void testResidueSimpleRoots() {
		Complex[] myRoots =  MathStuff.roots(A);
		
		double delta = 0.001;

		/*
		System.out.println("myRoots");
		for (int i = 0; i < myRoots.length; i++) {
			System.out.println("Real: "+myRoots[i].getReal() +" Imag: " +myRoots[i].getImaginary());
		}
		*/
		
		assertEquals( -2.3190, myRoots[4].getReal(), delta);
		assertEquals(-1.5915, myRoots[3].getReal(), delta);
		assertEquals(-0.1529, myRoots[2].getReal(), delta);
		assertEquals(-0.2777, myRoots[1].getReal(), delta);
		assertEquals(-0.1529, myRoots[0].getReal(), delta);
		
		assertEquals(+ 0.0000, myRoots[4].getImaginary(), delta);
		assertEquals(+ 0.0000, myRoots[3].getImaginary(), delta);
		assertEquals( + 0.3537, myRoots[2].getImaginary(), delta);
		assertEquals(+ 0.0000, myRoots[1].getImaginary(), delta);
		assertEquals(- 0.3537, myRoots[0].getImaginary(), delta);
	}
	
	@Test
	public void testResidueSimpleRoots2() {
		double delta = 0.001;
		
		//Matlab: roots([0.7144, 4.5537, 10.6420, 11.3823, 5.5717, 1.0000]);
		double[] Z = { 0.7144, 4.5537, 10.6420, 11.3823, 5.5717, 1.0000};
		Complex[] myRoots =  MathStuff.roots(Z);
			
		/*
		System.out.println("myRoots");
		for (int i = 0; i < myRoots.length; i++) {
			System.out.println("Real: "+myRoots[i].getReal() +" Imag: " +myRoots[i].getImaginary());
		}
		*/
		
		double[] ZrootsMatlabExpected = {-2.5130, -1.6380, -1.0697, -0.6981, -0.4554};
		assertEquals(ZrootsMatlabExpected[0], myRoots[4].getReal(),delta);
		assertEquals(ZrootsMatlabExpected[1], myRoots[3].getReal(),delta);
		assertEquals(ZrootsMatlabExpected[2], myRoots[2].getReal(),delta);
		assertEquals(ZrootsMatlabExpected[3], myRoots[1].getReal(),delta);
		assertEquals(ZrootsMatlabExpected[4], myRoots[0].getReal(),delta);
		
		//Matlab: roots([1, 2, 5, 7, 8, 2, 3, 1])
		double[] Z2 = {1, 2, 5, 7, 8, 2, 3, 1};
		Complex[] myRoots2 =  MathStuff.roots(Z2);
		
		/*
		System.out.println("myRoots2");
		for (int i = 0; i < myRoots2.length; i++) {
			System.out.println("Real: "+myRoots2[i].getReal() +" Imag: " +myRoots2[i].getImaginary());
		}
		*/
		
		
		assertEquals(0.0930, myRoots2[6].getReal(),delta);
		assertEquals(0.0930, myRoots2[5].getReal(),delta);
		assertEquals(-1.1423, myRoots2[4].getReal(),delta);
		assertEquals(-1.1423, myRoots2[3].getReal(),delta);
		assertEquals(0.2151, myRoots2[2].getReal(),delta);
		assertEquals(0.2151, myRoots2[1].getReal(),delta);
		assertEquals(-0.3317, myRoots2[0].getReal(),delta);

		assertEquals(1.7650, myRoots2[6].getImaginary(),delta);
		assertEquals(-1.7650, myRoots2[5].getImaginary(),delta);
		assertEquals(-0.9540, myRoots2[4].getImaginary(),delta);	// +/- positions is swaped (normaly first +)
		assertEquals(0.9540, myRoots2[3].getImaginary(),delta);		// +/- positions is swaped
		assertEquals(0.6241, myRoots2[2].getImaginary(),delta);
		assertEquals(-0.6241, myRoots2[1].getImaginary(),delta);
		assertEquals(0, myRoots2[0].getImaginary(),delta);
		
	}
	
	@Test
	public void testResidueSimpleComplexPoly() {
		double delta = 0.001;
		Complex[] p = new Complex[5];
		p[0] = new Complex(-2.319036260779738, 0.000000000000000);
		p[1] = new Complex(-1.591503908353926, 0.000000000000000);
		p[2] = new Complex(-0.152921730093296, 0.353741723772405);
		p[3] = new Complex(-0.152921730093296, -0.353741723772405);
		p[4] = new Complex(-0.277661923535474, 0.000000000000000);
		
		Complex[] pa = MathStuff.poly(p);
		
		/*
		System.out.println("myPolyComplex");
		for (int i = 0; i < pa.length; i++) {
			System.out.println("Real: "+pa[i].getReal() +" Imag: " +pa[i].getImaginary());
		}
		*/
		
		assertEquals( 1.0, pa[0].getReal(), delta);
		assertEquals(4.4940, pa[1].getReal(), delta);
		assertEquals(6.2060, pa[2].getReal(), delta);
		assertEquals(3.1077, pa[3].getReal(), delta);
		assertEquals(1.0228, pa[4].getReal(), delta);
		assertEquals(0.1522, pa[5].getReal(), delta);
		
		
		assertEquals(0.0, pa[0].getImaginary(), delta);
		assertEquals(0.0, pa[1].getImaginary(), delta);
		assertEquals(0.0, pa[2].getImaginary(), delta);
		assertEquals(0.0, pa[3].getImaginary(), delta);
		assertEquals(0.0, pa[4].getImaginary(), delta);
		assertEquals(0.0, pa[5].getImaginary(), delta);
		
	}
	
	@Test
	public void testResidueSimpleItSelfe(){
		double delta = 0.001;
		
		Object[] myObs = MathStuff.residueSimple(new TransferFunction(B, A));
		Complex[] R = (Complex[]) myObs[0];
		Complex[] P = (Complex[]) myObs[1];
		double K = (double) myObs[2];
		/*
		System.out.println("ResidueSimple:");
		//only value of first cell correct
		System.out.println("R:");
		for (int i = 0; i < R.length; i++) {
			System.out.println("Real: "+R[i].getReal() +" Imag: " +R[i].getImaginary());
		}
		//only some cell values correct
		System.out.println("P:");
		for (int i = 0; i < P.length; i++) {
			System.out.println("Real: "+P[i].getReal() +" Imag: " +P[i].getImaginary());
		}
		*/
		
		assertEquals(-2.3190,P[4].getReal(),delta);
		assertEquals(-1.5915 ,P[3].getReal(),delta);
		assertEquals(-0.1529,P[2].getReal(),delta);
		assertEquals(-0.2777,P[1].getReal(),delta);
		assertEquals(-0.1529,P[0].getReal(),delta);
		
		assertEquals(0.0,P[4].getImaginary(),delta);
		assertEquals(0.0 ,P[3].getImaginary(),delta);
		assertEquals(0.3537,P[2].getImaginary(),delta);
		assertEquals(0.0,P[1].getImaginary(),delta);
		assertEquals(-0.3537,P[0].getImaginary(),delta);
		
		assertEquals(-0.1553,R[4].getReal(),delta);
		assertEquals(0.3408 ,R[3].getReal(),delta);
		assertEquals(-0.0939,R[2].getReal(),delta);
		assertEquals(0.0024,R[1].getReal(),delta);
		assertEquals(-0.0939,R[0].getReal(),delta);
		
		assertEquals(0.0,R[4].getImaginary(),delta);
		assertEquals(0.0 ,R[3].getImaginary(),delta);
		assertEquals(-0.2178,R[2].getImaginary(),delta);
		assertEquals(0.0,R[1].getImaginary(),delta);
		assertEquals(0.2178,R[0].getImaginary(),delta);
		
	}
	
	@Test
	public void testResiduestepResidue(){
		double delta = 0.001;
		
		Object[] myObs = MathStuff.stepResidue(B, A, 1, 10);
		double[] y = (double[]) myObs[0];
		double[] t = (double[]) myObs[1];
		
		/*
		System.out.println("stepResidue");
		for (int i = 0; i < y.length; i++) {
			System.out.println("y: "+y[i]);
		}
		*/
		
		double[] yMatlabExpected = {0.0000, 0.0342, 0.1516 ,0.3378, 0.5568, 0.7735, 0.9616, 1.1048, 1.1970, 1.2400};
		assertArrayEquals(yMatlabExpected,y,delta);
		
	}
	
}
