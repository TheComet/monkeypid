package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;

public class FistFormulaOppeltPITest {

	@Test
	public void testCalculate() {
		IControllerCalculator c = new FistFormulaOppeltPI(new Plant(1, 1, 1, new SaniCurves()));
		c.calculate();
		// TODO: calc.getController();
		// TODO: assertEquals("OppeltPI Test failed",calc.getController(),); //Insert MathStuff Value
	}

	@Test
	public void testGetController() {
		fail("Not yet implemented");
	}

}
