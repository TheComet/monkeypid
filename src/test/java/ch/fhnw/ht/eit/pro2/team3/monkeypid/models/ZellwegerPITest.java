package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZellwegerPITest {

    double delta = 0.001; // seems to be the closest accuracy we can get
    SaniCurves sani = new SaniCurves();

    @Test
    public void testCalculate() throws Exception {
        IControllerCalculator cc;
        PIController c;

        // calculate zellweger PI with Tu=2, Tg=6, Ks=1, angleOfInflection=45°
        cc = new ZellwegerPI(new Plant(2, 6, 1, sani), 45);
        cc.calculate();
        c = (PIController)cc.getController();

        assertEquals(3.3122, c.getTn(), delta);
        assertEquals(0.984, c.getKr(), delta);

        // calculate zellweger PI with Tu=2, Tg=19, Ks=3, angleOfInflection=45°
        cc.setPlant(new Plant(2, 19, 3, sani));
        cc.calculate();
        c = (PIController)cc.getController();

        assertEquals(6.541, c.getTn(), delta);
        assertEquals(0.5453, c.getKr(), delta);

        // calculate zellweger PI with Tu=2, Tg=19, Ks=3, angleOfInflection=76.3°
        cc = new ZellwegerPI(new Plant(2, 19, 3, sani), 76.3);
        cc.calculate();
        c = (PIController)cc.getController();

        assertEquals(6.541, c.getTn(), delta);
        assertEquals(0.0624, c.getKr(), delta);
    }
}