package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerITest {
	double delta = 1.5e-4;
	
	@Test
	public void testPIControllerConstructor()  throws Exception {
		//[Ti] = p2_zellweger_i_tu_tg(2,6,1)
		//Ti = 6.9718
		double Ti = 6.9718;
		ControllerI myControllerI = new ControllerI("myI", Ti);
		
		//expected values
		//Br = [1];
		//Ar = [Ti 0];
		double[] NumeratorExpected = {1.0};
		double[] DenominatorExpected = {6.9718, 0.0};
		assertArrayEquals(NumeratorExpected, myControllerI.getTransferFunction().getNumeratorCoefficients(),delta);
		assertArrayEquals(DenominatorExpected, myControllerI.getTransferFunction().getDenominatorCoefficients(),delta);
	}

	@Test
	public void testPIControllerConstructorSetParams()  throws Exception {
		//[Ti] = p2_zellweger_i_tu_tg(2,6,1)
		//Ti = 6.9718
		double Ti = 6.9718;
		ControllerI myControllerI = new ControllerI("myI", 3.5684);
		myControllerI.setParameters(Ti);
		
		//expected values
		//Br = [1];
		//Ar = [Ti 0];
		double[] NumeratorExpected = {1.0};
		double[] DenominatorExpected = {6.9718, 0.0};
		assertArrayEquals(NumeratorExpected, myControllerI.getTransferFunction().getNumeratorCoefficients(),delta);
		assertArrayEquals(DenominatorExpected, myControllerI.getTransferFunction().getDenominatorCoefficients(),delta);
	}

}
