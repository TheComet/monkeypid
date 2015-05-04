package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZellwegerPITest {

    double delta = 0.001; // seems to be the closest accuracy we can get
    SaniCurves sani = new SaniCurves();

    @Test
    public void testCalculateTu2Tg6Ks1phi45() throws Exception {
        IControllerCalculator cc = new ZellwegerPI(new Plant(2, 6, 1, sani), 45);
        cc.calculate();
        PIController c = (PIController) cc.getController();

        assertEquals(3.3122, c.getTn(), delta);
        assertEquals(0.984, c.getKr(), delta);
    }

    @Test
    public void testCalculateTu2Tg19Ks3phi45() throws Exception {
        IControllerCalculator cc = new ZellwegerPI(new Plant(2, 19, 3, sani), 45);
        cc.calculate();
        PIController c = (PIController) cc.getController();

        assertEquals(6.541, c.getTn(), delta);
        assertEquals(0.5453, c.getKr(), delta);
    }

    @Test
    public void testCalculateTu2Tg19Ks3phi76_3() throws Exception {
        IControllerCalculator cc = new ZellwegerPI(new Plant(2, 19, 3, sani), 76.3);
        cc.calculate();
        PIController c = (PIController)cc.getController();

        assertEquals(6.541, c.getTn(), delta);
        assertEquals(0.0624, c.getKr(), delta);
    }
}