package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.TestGlobals;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the ZellwegerPID
 */
public class ZellwegerPIDTest {

	SaniCurves sani = new SaniCurves();

    /**
     * Test the ZellwegerPID
     */
	@Test
	public void testCalculateTu0_1Tg0_3Ks1phi45() throws Exception {
		AbstractControllerCalculator cc = new ZellwegerPID(new Plant(0.1, 0.3, 1, sani), 45);
		cc.run();
		ControllerPID c = (ControllerPID) cc.getController();

		assertEquals(0.1929, c.getTn(), TestGlobals.zellwegerDelta);
		assertEquals(0.0433, c.getTv(), TestGlobals.zellwegerDelta);
		assertEquals(1.7702, c.getKr(), TestGlobals.zellwegerDelta);
		assertEquals(0.0102, c.getTp(), TestGlobals.zellwegerDelta);
		assertEquals(1.0000, ((ZellwegerPID) cc).getBeta(), TestGlobals.zellwegerDelta);
	}

    /**
     * Test the ZellwegerPID
     */
	@Test
	public void testCalculateTu0_1Tg0_3Ks1phi76_3() throws Exception {
		AbstractControllerCalculator cc = new ZellwegerPID(new Plant(0.1, 0.3, 1, sani), 76.3);
		cc.run();
		ControllerPID c = (ControllerPID) cc.getController();

		assertEquals(0.1929, c.getTn(), TestGlobals.zellwegerDelta);
		assertEquals(0.0433, c.getTv(), TestGlobals.zellwegerDelta);
		assertEquals(0.5363, c.getKr(), TestGlobals.zellwegerDelta);
		assertEquals(0.0102, c.getTp(), TestGlobals.zellwegerDelta);
		assertEquals(1.0000, ((ZellwegerPID) cc).getBeta(), TestGlobals.zellwegerDelta);
	}

    /**
     * Test the ZellwegerPID
     */
	@Test
	public void testCalculateTu2Tg6Ks1phi45() throws Exception {
		AbstractControllerCalculator cc = new ZellwegerPID(new Plant(2, 6, 1, sani), 45);
		cc.run();
		ControllerPID c = (ControllerPID) cc.getController();

		assertEquals(3.8606, c.getTn(), TestGlobals.zellwegerDelta);
		assertEquals(0.8677, c.getTv(), TestGlobals.zellwegerDelta);
		assertEquals(1.7702, c.getKr(), TestGlobals.zellwegerDelta);
		assertEquals(0.2000, c.getTp(), TestGlobals.zellwegerDelta);
		assertEquals(1.0000, ((ZellwegerPID) cc).getBeta(), TestGlobals.zellwegerDelta);
	}

    /**
     * Test the ZellwegerPID
     */
	@Test
	public void testCalculateTu2Tg6Ks3phi45() throws Exception {
		AbstractControllerCalculator cc = new ZellwegerPID(new Plant(2, 6, 3, sani), 45);
		cc.run();
		ControllerPID c = (ControllerPID) cc.getController();

		assertEquals(3.8606, c.getTn(), TestGlobals.zellwegerDelta);
		assertEquals(0.8677, c.getTv(), TestGlobals.zellwegerDelta);
		assertEquals(0.5900, c.getKr(), TestGlobals.zellwegerDelta);
		assertEquals(0.2000, c.getTp(), TestGlobals.zellwegerDelta);
		assertEquals(1.0000, ((ZellwegerPID) cc).getBeta(), TestGlobals.zellwegerDelta);
	}

    /**
     * Test the ZellwegerPID
     */
	@Test
	public void testCalculateTu2Tg9Ks1phi45() throws Exception {
		AbstractControllerCalculator cc = new ZellwegerPID(new Plant(2, 9, 1, sani), 45);
		cc.run();
		ControllerPID c = (ControllerPID) cc.getController();

		assertEquals(4.7224, c.getTn(), TestGlobals.zellwegerDelta);
		assertEquals(0.9998, c.getTv(), TestGlobals.zellwegerDelta);
		assertEquals(2.3849, c.getKr(), TestGlobals.zellwegerDelta);
		assertEquals(0.1800, c.getTp(), TestGlobals.zellwegerDelta);
		assertEquals(0.7572, ((ZellwegerPID) cc).getBeta(), TestGlobals.zellwegerDelta);
	}

    /**
     * Test the ZellwegerPID
     */
	@Test
	public void testCalculateTu1_5Tg17Ks1phi45() throws Exception {
		AbstractControllerCalculator cc = new ZellwegerPID(new Plant(1.5, 17, 1, sani), 45);
		cc.run();
		ControllerPID c = (ControllerPID) cc.getController();

		assertEquals(26.0616, c.getTn(), TestGlobals.zellwegerDelta);
		assertEquals(0.1572, c.getTv(), TestGlobals.zellwegerDelta);
		assertEquals(8.3207, c.getKr(), TestGlobals.zellwegerDelta);
		assertEquals(0.0200, c.getTp(), TestGlobals.zellwegerDelta);
		assertEquals(0.083, ((ZellwegerPID) cc).getBeta(), TestGlobals.zellwegerDelta);
	}
}