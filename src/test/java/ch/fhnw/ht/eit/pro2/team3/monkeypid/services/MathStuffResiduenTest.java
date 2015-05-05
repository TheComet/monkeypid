package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;

import static org.junit.Assert.*;

import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

public class MathStuffResiduenTest {
	double[] B = {4.0812, 1.1400};
	double[] A = {7.4902, 33.6613, 46.4843, 23.2772, 7.6612, 1.1400};
	
	double[] Bzeros = {0.0, 0.0, 0.0, 4.0812, 1.1400};
	
	double[] Bequal = {4.0812, 1.1400};
	double[] Aequal = {7.4902, 33.6613};
	
	double[] BequalExpected = {0,  -17.2011};

	@Test
	public void testResidueSimpleRemoveLeadingZeros() {
		
		/*
		int startIndex = 0;
		//remove leading Zeros
		for (int i = 0; i < B0.length; i++) {
			if(B0[i] != 0){
				startIndex = i;
				break;
			}
		}
		
		double[] BzerosRemoved = new double[B0.length-startIndex];
		for (int i = 0; i < BzerosRemoved.length; i++) {
			BzerosRemoved[i] = B0[startIndex + i];
		}
		*/
		
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

		
		System.out.println("myRoots");
		for (int i = 0; i < myRoots.length; i++) {
			System.out.println("Real: "+myRoots[i].getReal() +" Imag: " +myRoots[i].getImaginary());
		}
		
		
		
		assertEquals( -2.3190, myRoots[0].getReal(), delta);
		assertEquals(-1.5915, myRoots[1].getReal(), delta);
		assertEquals(-0.1529, myRoots[2].getReal(), delta);
		assertEquals(-0.2777, myRoots[3].getReal(), delta);
		assertEquals(-0.1529, myRoots[4].getReal(), delta);
		
		
		assertEquals(+ 0.0000, myRoots[0].getImaginary(), delta);
		assertEquals(+ 0.0000, myRoots[1].getImaginary(), delta);
		assertEquals( + 0.3537, myRoots[2].getImaginary(), delta);
		assertEquals(+ 0.0000, myRoots[3].getImaginary(), delta);
		assertEquals(- 0.3537, myRoots[4].getImaginary(), delta);
		
	}
	
}
