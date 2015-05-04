package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZellwegerPIDTest {

    double delta = 0.001; // seems to be the closest accuracy we can get

    @Test
    public void testCalculate() throws Exception {
        SaniCurves sani = new SaniCurves();
        IControllerCalculator cc;
        PIDController c;

        // calculate zellweger PID with Tu=0.1, Tg=0.3, Ks=1, angleOfInflection=45°
        cc = new ZellwegerPID(new Plant(0.1, 0.3, 1, sani), 45);
        cc.calculate();
        c = (PIDController)cc.getController();

        assertEquals(0.1929, c.getTn(), delta);
        assertEquals(0.0433, c.getTv(), delta);
        assertEquals(1.7656, c.getKr(), delta);
        assertEquals(0.0102, c.getTp(), delta);
        assertEquals(1.0000, ((ZellwegerPID)cc).getBeta(), delta);
    }
}