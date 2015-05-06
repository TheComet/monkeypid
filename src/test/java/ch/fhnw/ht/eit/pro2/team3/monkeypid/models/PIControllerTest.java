package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

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
		
		PIController myPIController = new PIController("myPI", 1.2, 5.5);
		myPIController.setParameters(Kr, Tn);
		
		double[] NumeratorExpected = {3.2591, 0.9840};
		double[] DenominatorExpected = {3.3122, 0.0};
		assertArrayEquals(NumeratorExpected, myPIController.getTransferFunction().getNumeratorCoefficients(),delta);
		assertArrayEquals(DenominatorExpected, myPIController.getTransferFunction().getDenominatorCoefficients(),delta);
	}

}
