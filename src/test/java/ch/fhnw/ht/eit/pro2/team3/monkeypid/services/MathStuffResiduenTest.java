package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;

import static org.junit.Assert.*;

import org.junit.Test;

public class MathStuffResiduenTest {
	double[] B = {4.0812, 1.1400};
	double[] A = {7.4902, 33.6613, 46.4843, 23.2772, 7.6612, 1.1400};
	
	double[] B0 = {0.0, 0.0, 0.0, 4.0812, 1.1400};
	
	double[] Bequal = {4.0812, 1.1400};
	double[] Aequal = {7.4902, 33.6613};
	
	double[] BequalExpected = {0,  -17.2011};

	@Test
	public void testResidueSimpleRemoveLeadingZeros() {
		
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
		
		assertArrayEquals(B, BzerosRemoved, 0.01);
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

}
