package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.TestGlobals;

/**
 * Test the FistFormulas
 */
public class FistFormulaTest {

	private SaniCurves sani = new SaniCurves();
	Plant plant = new Plant(2, 6, 3, sani);
	//Tu = 2, Tg = 6, Ks = 3

    /**
     * Test all FistFormulas
     */
	@Test
	public void testFistFormulas() {
		AbstractControllerCalculator cc = new FistFormulaOppeltPI(plant);
		cc.run();
		ControllerPI cPI = (ControllerPI) cc.getController();
		assertEquals(0.8, cPI.getKr(), TestGlobals.fistFormulas);
		assertEquals(6, cPI.getTn(), TestGlobals.fistFormulas);

		cc = new FistFormulaOppeltPID(plant);
		cc.run();
		ControllerPID cPID = (ControllerPID) cc.getController();
		assertEquals(1.2, cPID.getKr(), TestGlobals.fistFormulas);
		assertEquals(4, cPID.getTn(), TestGlobals.fistFormulas);
		assertEquals(0.84, cPID.getTv(), TestGlobals.fistFormulas);

		cc = new FistFormulaRosenbergPI(plant);
		cc.run();
		cPI = (ControllerPI) cc.getController();
		assertEquals(0.91, cPI.getKr(), TestGlobals.fistFormulas);
		assertEquals(6.6, cPI.getTn(), TestGlobals.fistFormulas);

		cc = new FistFormulaRosenbergPID(plant);
		cc.run();
		cPID = (ControllerPID) cc.getController();
		assertEquals(1.2, cPID.getKr(), TestGlobals.fistFormulas);
		assertEquals(4, cPID.getTn(), TestGlobals.fistFormulas);
		assertEquals(0.88, cPID.getTv(), TestGlobals.fistFormulas);

		cc = new FistFormulaReswickStoerPI0(plant);
		cc.run();
		cPI = (ControllerPI) cc.getController();
		assertEquals(0.6, cPI.getKr(), TestGlobals.fistFormulas);
		assertEquals(8, cPI.getTn(), TestGlobals.fistFormulas);

		cc = new FistFormulaReswickStoerPID0(plant);
		cc.run();
		cPID = (ControllerPID) cc.getController();
		assertEquals(0.95, cPID.getKr(), TestGlobals.fistFormulas);
		assertEquals(4.8, cPID.getTn(), TestGlobals.fistFormulas);
		assertEquals(0.84, cPID.getTv(), TestGlobals.fistFormulas);

		cc = new FistFormulaReswickFuehrungPI0(plant);
		cc.run();
		cPI = (ControllerPI) cc.getController();
		assertEquals(0.45, cPI.getKr(), TestGlobals.fistFormulas);
		assertEquals(7.2, cPI.getTn(), TestGlobals.fistFormulas);

		cc = new FistFormulaReswickFuehrungPID0(plant);
		cc.run();
		cPID = (ControllerPID) cc.getController();
		assertEquals(0.6, cPID.getKr(), TestGlobals.fistFormulas);
		assertEquals(6, cPID.getTn(), TestGlobals.fistFormulas);
		assertEquals(1, cPID.getTv(), TestGlobals.fistFormulas);

		cc = new FistFormulaReswickStoerPI20(plant);
		cc.run();
		cPI = (ControllerPI) cc.getController();
		assertEquals(0.7, cPI.getKr(), TestGlobals.fistFormulas);
		assertEquals(4.6, cPI.getTn(), TestGlobals.fistFormulas);

		cc = new FistFormulaReswickStoerPID20(plant);
		cc.run();
		cPID = (ControllerPID) cc.getController();
		assertEquals(1.2, cPID.getKr(), TestGlobals.fistFormulas);
		assertEquals(4, cPID.getTn(), TestGlobals.fistFormulas);
		assertEquals(0.84, cPID.getTv(), TestGlobals.fistFormulas);

		cc = new FistFormulaReswickFuehrungPI20(plant);
		cc.run();
		cPI = (ControllerPI) cc.getController();
		assertEquals(0.6, cPI.getKr(), TestGlobals.fistFormulas);
		assertEquals(6, cPI.getTn(), TestGlobals.fistFormulas);

		cc = new FistFormulaReswickFuehrungPID20(plant);
		cc.run();
		cPID = (ControllerPID) cc.getController();
		assertEquals(0.95, cPID.getKr(), TestGlobals.fistFormulas);
		assertEquals(8.1, cPID.getTn(), TestGlobals.fistFormulas);
		assertEquals(0.94, cPID.getTv(), TestGlobals.fistFormulas);
	}
}
