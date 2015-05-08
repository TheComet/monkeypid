package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.TestGlobals;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ZellwegerPITest {

    SaniCurves sani = new SaniCurves();

    @Test
    public void testCalculateTu2Tg6Ks1phi45() throws Exception {
        AbstractControllerCalculator cc = new ZellwegerPI(new Plant(2, 6, 1, sani), 45);
        cc.run();
        PIController c = (PIController) cc.getController();

        assertEquals(3.3122, c.getTn(), TestGlobals.zellwegerDelta);
        assertEquals(0.984, c.getKr(), TestGlobals.zellwegerDelta);
    }

    @Test
    public void testCalculateTu2Tg19Ks3phi45() throws Exception {
        AbstractControllerCalculator cc = new ZellwegerPI(new Plant(2, 19, 3, sani), 45);
        cc.run();
        PIController c = (PIController) cc.getController();

        assertEquals(6.541, c.getTn(), TestGlobals.zellwegerDelta);
        assertEquals(0.5453, c.getKr(), TestGlobals.zellwegerDelta);
    }

    @Test
    public void testCalculateTu2Tg19Ks3phi76_3() throws Exception {
        AbstractControllerCalculator cc = new ZellwegerPI(new Plant(2, 19, 3, sani), 76.3);
        cc.run();
        PIController c = (PIController)cc.getController();

        assertEquals(6.541, c.getTn(), TestGlobals.zellwegerDelta);
        assertEquals(0.0624, c.getKr(), TestGlobals.zellwegerDelta);
    }
}