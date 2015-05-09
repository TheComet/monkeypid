package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import org.junit.Test;

import static org.junit.Assert.fail;

public class FistFormulaOppeltPITest {

	@Test
	public void testCalculate() {
		AbstractControllerCalculator c = new FistFormulaOppeltPI(new Plant(1, 1, 1, new SaniCurves()));
		c.run();
		// TODO: calc.getController();
		// TODO: assertEquals("OppeltPI Test failed",calc.getController(),); //Insert MathStuff Value
		fail("Not yet implemented");
	}

	@Test
	public void testGetController() {
		fail("Not yet implemented");
	}

}