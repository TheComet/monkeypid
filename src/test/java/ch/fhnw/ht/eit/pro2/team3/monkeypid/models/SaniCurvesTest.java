package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.junit.Assert.*;

public class SaniCurvesTest {

    @Test
    public void testLookupPower() throws Exception {
        SaniCurves c = new SaniCurves();

        // test turning points according to the 6 magic numbers
        assertEquals(2, c.lookupPower(0.0001));

        assertEquals(2, c.lookupPower(0.103638));
        assertEquals(3, c.lookupPower(0.103639));

        assertEquals(3, c.lookupPower(0.218017));
        assertEquals(4, c.lookupPower(0.218018));

        assertEquals(4, c.lookupPower(0.319357));
        assertEquals(5, c.lookupPower(0.319358));

        assertEquals(5, c.lookupPower(0.410303));
        assertEquals(6, c.lookupPower(0.410304));

        assertEquals(6, c.lookupPower(0.4933));
        assertEquals(7, c.lookupPower(0.4934));

        assertEquals(7, c.lookupPower(0.5700));
        assertEquals(8, c.lookupPower(0.5701));

        assertEquals(8, c.lookupPower(0.64173));
    }

    @Test(expected=RuntimeException.class)
    public void testLookupPowerInvalidValue() {
        SaniCurves c = new SaniCurves();
        c.lookupPower(0.64174); // last magic number
    }

    @Test
    public void testCalculateTimeConstants() throws Exception {
        SaniCurves c = new SaniCurves();
        double delta = 0.00025; // seems to be the closest we can get

        // test powers 2 to 8
        double[] tc = c.calculateTimeConstants(1, 10);
        assertEquals(2.8188, tc[0], delta);
        assertEquals(4.6119, tc[1], delta);

        tc = c.calculateTimeConstants(1, 5);
        assertEquals(0.7952, tc[0], delta);
        assertEquals(1.2620, tc[1], delta);
        assertEquals(2.0030, tc[2], delta);

        tc = c.calculateTimeConstants(1, 4);
        assertEquals(0.2922, tc[0], delta);
        assertEquals(0.5255, tc[1], delta);
        assertEquals(0.9449, tc[2], delta);
        assertEquals(1.6991, tc[3], delta);

        tc = c.calculateTimeConstants(1, 3);
        assertEquals(0.1991, tc[0], delta);
        assertEquals(0.3051, tc[1], delta);
        assertEquals(0.4675, tc[2], delta);
        assertEquals(0.7164, tc[3], delta);
        assertEquals(1.0978, tc[4], delta);

        tc = c.calculateTimeConstants(1, 2.3);
        assertEquals(0.1773, tc[0], delta);
        assertEquals(0.2324, tc[1], delta);
        assertEquals(0.3047, tc[2], delta);
        assertEquals(0.3995, tc[3], delta);
        assertEquals(0.5237, tc[4], delta);
        assertEquals(0.6866, tc[5], delta);

        tc = c.calculateTimeConstants(1, 2);
        assertEquals(0.1345, tc[0], delta);
        assertEquals(0.1704, tc[1], delta);
        assertEquals(0.2160, tc[2], delta);
        assertEquals(0.2738, tc[3], delta);
        assertEquals(0.3471, tc[4], delta);
        assertEquals(0.4399, tc[5], delta);
        assertEquals(0.5576, tc[6], delta);

        tc = c.calculateTimeConstants(1, 1.6);
        assertEquals(0.1686, tc[0], delta);
        assertEquals(0.1844, tc[1], delta);
        assertEquals(0.2017, tc[2], delta);
        assertEquals(0.2206, tc[3], delta);
        assertEquals(0.2414, tc[4], delta);
        assertEquals(0.2640, tc[5], delta);
        assertEquals(0.2888, tc[6], delta);
        assertEquals(0.3159, tc[7], delta);
    }

    @Test(expected=RuntimeException.class)
    public void testCalculateTimeConstantsWithInvalidRatio() {
        SaniCurves c = new SaniCurves();
        c.calculateTimeConstants(1, 1.5582);
    }
}