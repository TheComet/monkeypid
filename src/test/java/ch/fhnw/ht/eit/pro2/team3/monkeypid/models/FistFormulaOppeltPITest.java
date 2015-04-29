package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.ControllerCalculator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.FistFormulaOppeltPI;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;

public class FistFormulaOppeltPITest {

	@Test
	public void testCalculate() {
		ControllerCalculator calc=new FistFormulaOppeltPI();
		Plant plant=new Plant(1,1,1);
		calc.calculate(plant);
		// TODO: calc.getController();
		// TODO: assertEquals("OppeltPI Test failed",calc.getController(),); //Insert Matlab Value
	}

	@Test
	public void testGetController() {
		fail("Not yet implemented");
	}

}
