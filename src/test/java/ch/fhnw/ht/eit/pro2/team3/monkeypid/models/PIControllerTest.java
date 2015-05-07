package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

public class PIControllerTest {
	double delta = 1.5e-4;
	
	@Test
    public void testPIControllerConstructor()  throws Exception {
		//[Tn, Kr] = p2_zellweger_pi_tu_tg(45-180,2,6,1)
		//Tn = 3.3122, Kr =    0.9840
		double Kr = 0.9840;
		double Tn = 3.3122;
		PIController myPIController = new PIController("myPI", Kr, Tn);
		
		//expected values
		//Br = Kr*[Tn 1];
		//Ar = [Tn 0];
		double[] NumeratorExpected = {3.2591, 0.9840};
		double[] DenominatorExpected = {3.3122, 0.0};
		assertArrayEquals(NumeratorExpected, myPIController.getTransferFunction().getNumeratorCoefficients(),delta);
		assertArrayEquals(DenominatorExpected, myPIController.getTransferFunction().getDenominatorCoefficients(),delta);
		
		
	}
	
	@Test
    public void testPIControllerSetParameters()  throws Exception {
	    //[Tn, Kr] = p2_zellweger_pi_tu_tg(45-180,2,6,1)
		//Tn = 3.3122, Kr =    0.9840
		double Kr = 0.9840;
		double Tn = 3.3122;
		
		PIController myPIController = new PIController("myPI", 1.2, 5.5); //set some random values
		myPIController.setParameters(Kr, Tn);
		
		//expected values
		//Br = Kr*[Tn 1];
		//Ar = [Tn 0];
		double[] NumeratorExpected = {3.2591, 0.9840};
		double[] DenominatorExpected = {3.3122, 0.0};
		assertArrayEquals(NumeratorExpected, myPIController.getTransferFunction().getNumeratorCoefficients(),delta);
		assertArrayEquals(DenominatorExpected, myPIController.getTransferFunction().getDenominatorCoefficients(),delta);
		
		Complex[] test = new Complex[2];
		test[0] = new Complex(3.0000 ,  + 4.0000);
		test[1] = new Complex(35.0000,  + 0.0000);
	}

}
