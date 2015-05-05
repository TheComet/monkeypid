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
                new double[] {4.0, 5.0, 6.0},
                new double[] {1.0, 2.0, 3.0}
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

    @Test
    public void testConv() throws Exception {
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
}