package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.TransferFunction;
import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

import static org.junit.Assert.*;

public class MathStuffTest {

	private double delta = 1e-8; // default floating point deviation

	@Test
	public void testLinspace0to10() throws Exception {
		double[] l = MathStuff.linspace(0, 10, 11);
		for(int expected = 0; expected < 11; expected++) {
			assertEquals((double)expected, l[expected], delta);
		}
	}

	@Test
	public void testLinspace10to15() throws Exception {
		double[] l = MathStuff.linspace(10, 15, 30);
		int i = 0;
		for(double expected = 10.0; expected < 15 + delta; expected += 5.0/29.0) {
			assertEquals(expected, l[i], delta);
			i++;
		}
	}

	@Test
	public void testOmegaToS() throws Exception {
		double omega = 10;
		Complex actual = MathStuff.omegaToS(omega);
		assertEquals(0.0, actual.getReal(), delta);
		assertEquals(omega, actual.getImaginary(), delta);
	}

	@Test
	public void testFreqs() throws Exception {
		TransferFunction g = new TransferFunction(
				new double[] {1.0, 2.0, 3.0},
				new double[] {4.0, 5.0, 6.0}
		);
		Complex[] results = MathStuff.freqs(g, MathStuff.linspace(0, 10, 11));

		// results copied from matlab's freqs()
		assertEquals(0.500000000000000,  results[0].getReal(), delta);
		assertEquals(0.00000000000000,   results[0].getImaginary(), delta);
		assertEquals(0.482758620689655,  results[1].getReal(), delta);
		assertEquals(-0.206896551724138, results[1].getImaginary(), delta);
		assertEquals(0.250000000000000,  results[2].getReal(), delta);
		assertEquals(-0.150000000000000, results[2].getImaginary(), delta);
		assertEquals(0.240000000000000,  results[3].getReal(), delta);
		assertEquals(-0.0800000000000000,results[3].getImaginary(), delta);
		assertEquals(0.242826780021254,  results[4].getReal(), delta);
		assertEquals(-0.0541976620616365,results[4].getImaginary(), delta);
		assertEquals(0.245005813338971,  results[5].getReal(), delta);
		assertEquals(-0.0412218581545291,results[5].getImaginary(), delta);
		assertEquals(0.246389891696751,  results[6].getReal(), delta);
		assertEquals(-0.0333935018050542,results[6].getImaginary(), delta);
		assertEquals(0.247287340924313,  results[7].getReal(), delta);
		assertEquals(-0.0281312793034159,results[7].getImaginary(), delta);
		assertEquals(0.247893915756630,  results[8].getReal(), delta);
		assertEquals(-0.0243369734789392,results[8].getImaginary(), delta);
		assertEquals(0.248320390890847,  results[9].getReal(), delta);
		assertEquals(-0.0214640956286537,results[9].getImaginary(), delta);
		assertEquals(0.248630623319978,  results[10].getReal(), delta);
		assertEquals(-0.0192093117614242,results[10].getImaginary(), delta);
	}

	@Test
	public void testPolyValNonZeroOmega() throws Exception {
		double[] poly = new double[] {1.0, 2.0, 3.0};
		Complex result = MathStuff.polyVal(poly, MathStuff.omegaToS(5.0));

		// result should be 1*s^2 + 2*s^1 + 3*s^0
		assertEquals(-22.0, result.getReal(), delta);
		assertEquals(10.0, result.getImaginary(), delta);
	}

	@Test
	public void testPolyValZeroOmega() throws Exception {
		double[] poly = new double[] {1.0, 2.0, 3.0};
		Complex result = MathStuff.polyVal(poly, MathStuff.omegaToS(0.0));

		// result should be 1*s^2 + 2*s^1 + 3*s^0 = 3+0j
		assertEquals(3.0, result.getReal(), delta);
		assertEquals(0.0, result.getImaginary(), delta);
	}

	/*
	@Test
	public void testConvDoubles() throws Exception {
		double[] result = MathStuff.conv(
				new double[] {1, 2, 3, 4},
				new double[] {5, 6, 7, 8}
		);
		assertEquals(5,  result[0], delta);
		assertEquals(16, result[1], delta);
		assertEquals(34, result[2], delta);
		assertEquals(60, result[3], delta);
		assertEquals(61, result[4], delta);
		assertEquals(52, result[5], delta);
		assertEquals(32, result[6], delta);
	}

	@Test
	public void testConvDifferentArrayLengths() throws Exception {
		double[] result = MathStuff.conv(
				new double[] {1, 2, 3, 4},
				new double[] {26, 6}
		);
		double[] expected = new double[] {2, 30, 58, 86, 104};
		assertArrayEquals(expected, result, delta);
	}*/

	@Test
	public void testOnes() throws Exception {
		double[] result = MathStuff.ones(10);
		assertEquals(10, result.length);
		for(double value : result) {
			assertEquals(1.0, value, delta);
		}
	}

	@Test
	public void testReal() throws Exception {
		Complex[] cs = new Complex[5];
		for(int i = 0; i < 5; i++) {
			cs[i] = new Complex(3*i - 5, 7*i - 20);
		}

		double[] result = MathStuff.real(cs);
		for(int i = 0; i < 5; i++) {
			assertEquals(3*i - 5, result[i], delta);
		}
	}

	@Test
	public void testImag() throws Exception {
		Complex[] cs = new Complex[5];
		for(int i = 0; i < 5; i++) {
			cs[i] = new Complex(3*i - 5, 7*i - 20);
		}

		double[] result = MathStuff.imag(cs);
		for(int i = 0; i < 5; i++) {
			assertEquals(7*i - 20, result[i], delta);
		}
	}

	@Test
	public void testSymmetricMirrorConjugate() throws Exception {
		Complex[] values = new Complex[4];
		values[0] = new Complex(1, 5);
		values[1] = new Complex(4, 2);
		values[2] = new Complex(8, 3);
		values[3] = new Complex(-5, 8);

		Complex[] result = MathStuff.symmetricMirrorConjugate(values);
		assertEquals(8, result.length);
		assertEquals(1,  result[0].getReal(), delta);
		assertEquals(5,  result[0].getImaginary(), delta);
		assertEquals(4,  result[1].getReal(), delta);
		assertEquals(2,  result[1].getImaginary(), delta);
		assertEquals(8,  result[2].getReal(), delta);
		assertEquals(3,  result[2].getImaginary(), delta);
		assertEquals(-5, result[3].getReal(), delta);
		assertEquals(8,  result[3].getImaginary(), delta);

		assertEquals(0,  result[4].getReal(), delta);
		assertEquals(0,  result[4].getImaginary(), delta);

		assertEquals(-5, result[5].getReal(), delta);
		assertEquals(-8, result[5].getImaginary(), delta);
		assertEquals(8,  result[6].getReal(), delta);
		assertEquals(-3, result[6].getImaginary(), delta);
		assertEquals(4,  result[7].getReal(), delta);
		assertEquals(-2, result[7].getImaginary(), delta);
	}

	@Test
	public void testPoly() throws Exception {
		double[] res = MathStuff.poly(new double[]{1, 2, 4});
		assertEquals(1.0,  res[0], delta);
		assertEquals(-7.0, res[1], delta);
		assertEquals(14.0, res[2], delta);
		assertEquals(-8.0, res[3], delta);
	}

	@Test
	public void testIfft() throws Exception {
		Complex[] values = new Complex[4];
		values[0] = new Complex(1, 5);
		values[1] = new Complex(4, 2);
		values[2] = new Complex(8, 3);
		values[3] = new Complex(-5, 8);

		Complex[] results =MathStuff.ifft(values);
		assertEquals(2.0,  results[0].getReal(), delta);
		assertEquals(4.5,   results[0].getImaginary(), delta);
		assertEquals(-0.25,  results[1].getReal(), delta);
		assertEquals(2.75,   results[1].getImaginary(), delta);
		assertEquals(2.5,  results[2].getReal(), delta);
		assertEquals(-0.5,   results[2].getImaginary(), delta);
		assertEquals(-3.25,  results[3].getReal(), delta);
		assertEquals(-1.75,   results[3].getImaginary(), delta);
	}



}