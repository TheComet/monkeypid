package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.TestGlobals;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the saniCurves
 */
public class SaniCurvesTest {

    /**
     * Test the LookupPower
     */
	@Test
	public void testLookupPower() throws Exception {
		SaniCurves c = new SaniCurves();

		// test turning points according to the 6 magic numbers
		assertEquals(2, c.lookupOrder(0.0001));

		assertEquals(2, c.lookupOrder(0.103638));
		assertEquals(3, c.lookupOrder(0.103639));

		assertEquals(3, c.lookupOrder(0.218017));
		assertEquals(4, c.lookupOrder(0.218018));

		assertEquals(4, c.lookupOrder(0.319357));
		assertEquals(5, c.lookupOrder(0.319358));

		assertEquals(5, c.lookupOrder(0.410303));
		assertEquals(6, c.lookupOrder(0.410304));

		assertEquals(6, c.lookupOrder(0.4933));
		assertEquals(7, c.lookupOrder(0.4934));

		assertEquals(7, c.lookupOrder(0.5700));
		assertEquals(8, c.lookupOrder(0.5701));

		assertEquals(8, c.lookupOrder(0.64173));
	}

    /**
     * Test the LookupPower
     */
	@Test(expected=RuntimeException.class)
	public void testLookupPowerInvalidValue() {
		SaniCurves c = new SaniCurves();
		c.lookupOrder(0.64174); // last magic number
	}

    /**
     * Test the TimeConstants calculation
     */
	@Test
	public void testcalculateTimeConstantsLinear() throws Exception {
		SaniCurves c = new SaniCurves();

		// test powers 2 to 8
		double[] tc = c.calculateTimeConstantsLinear(1, 10);
		assertEquals(2.81875934051940, tc[0], TestGlobals.saniDelta);
		assertEquals(4.61187459765845, tc[1], TestGlobals.saniDelta);

		tc = c.calculateTimeConstantsLinear(1, 5);
		assertEquals(0.795152009551174, tc[0], TestGlobals.saniDelta);
		assertEquals(1.26200896793459, tc[1], TestGlobals.saniDelta);
		assertEquals(2.00297127595304, tc[2], TestGlobals.saniDelta);

		tc = c.calculateTimeConstantsLinear(1, 4);
		assertEquals(0.292245372354657, tc[0], TestGlobals.saniDelta);
		assertEquals(0.525498853982710, tc[1], TestGlobals.saniDelta);
		assertEquals(0.944921876135024, tc[2], TestGlobals.saniDelta);
		assertEquals(1.69910428011687, tc[3], TestGlobals.saniDelta);

		tc = c.calculateTimeConstantsLinear(1, 3);
		assertEquals(0.199061019130236, tc[0], TestGlobals.saniDelta);
		assertEquals(0.305052409467644, tc[1], TestGlobals.saniDelta);
		assertEquals(0.467479634780391, tc[2], TestGlobals.saniDelta);
		assertEquals(0.716392338338791, tc[3], TestGlobals.saniDelta);
		assertEquals(1.09784029987021, tc[4], TestGlobals.saniDelta);

		tc = c.calculateTimeConstantsLinear(1, 2.3);
		assertEquals(0.177305373375394, tc[0], TestGlobals.saniDelta);
		assertEquals(0.232441548369716, tc[1], TestGlobals.saniDelta);
		assertEquals(0.304723271381742, tc[2], TestGlobals.saniDelta);
		assertEquals(0.399482247355776, tc[3], TestGlobals.saniDelta);
		assertEquals(0.523708167179986, tc[4], TestGlobals.saniDelta);
		assertEquals(0.686564287115260, tc[5], TestGlobals.saniDelta);

		tc = c.calculateTimeConstantsLinear(1, 2);
		assertEquals(0.134472180292776, tc[0], TestGlobals.saniDelta);
		assertEquals(0.170444700913804, tc[1], TestGlobals.saniDelta);
		assertEquals(0.216040195126938, tc[2], TestGlobals.saniDelta);
		assertEquals(0.273832895128191, tc[3], TestGlobals.saniDelta);
		assertEquals(0.347085663435123, tc[4], TestGlobals.saniDelta);
		assertEquals(0.439934207706506, tc[5], TestGlobals.saniDelta);
		assertEquals(0.557620574687113, tc[6], TestGlobals.saniDelta);

		tc = c.calculateTimeConstantsLinear(1, 1.6);
		assertEquals(0.168569381240628, tc[0], TestGlobals.saniDelta);
		assertEquals(0.184396961114740, tc[1], TestGlobals.saniDelta);
		assertEquals(0.201710648862226, tc[2], TestGlobals.saniDelta);
		assertEquals(0.220649980446818, tc[3], TestGlobals.saniDelta);
		assertEquals(0.241367593361100, tc[4], TestGlobals.saniDelta);
		assertEquals(0.264030456775730, tc[5], TestGlobals.saniDelta);
		assertEquals(0.288821217191768, tc[6], TestGlobals.saniDelta);
		assertEquals(0.315939670441090, tc[7], TestGlobals.saniDelta);
	}

    /**
     * Test the TimeConstants calculation
     */
	@Test(expected=RuntimeException.class)
	public void testcalculateTimeConstantsLinearWithInvalidRatio() {
		SaniCurves c = new SaniCurves();
		c.calculateTimeConstantsLinear(1, 1.5582);
	}

    /**
     * Test the TimeConstants calculation with new spline function
     */
	@Test
	public void testcalculateTimeConstantsSpline() throws Exception {
		SaniCurves c = new SaniCurves();

		// test powers 2 to 8
		double[] tc = c.calculateTimeConstantsSpline(1, 10);
		assertEquals(2.81875934051940, tc[0], TestGlobals.saniDeltaSpline);
		assertEquals(4.61187459765845, tc[1], TestGlobals.saniDeltaSpline);

		tc = c.calculateTimeConstantsSpline(1, 5);
		assertEquals(0.795152009551174, tc[0], TestGlobals.saniDeltaSpline);
		assertEquals(1.26200896793459, tc[1], TestGlobals.saniDeltaSpline);
		assertEquals(2.00297127595304, tc[2], TestGlobals.saniDeltaSpline);

		tc = c.calculateTimeConstantsSpline(1, 4);
		assertEquals(0.292245372354657, tc[0], TestGlobals.saniDeltaSpline);
		assertEquals(0.525498853982710, tc[1], TestGlobals.saniDeltaSpline);
		assertEquals(0.944921876135024, tc[2], TestGlobals.saniDeltaSpline);
		assertEquals(1.69910428011687, tc[3], TestGlobals.saniDeltaSpline);

		tc = c.calculateTimeConstantsSpline(1, 3);
		assertEquals(0.199061019130236, tc[0], TestGlobals.saniDeltaSpline);
		assertEquals(0.305052409467644, tc[1], TestGlobals.saniDeltaSpline);
		assertEquals(0.467479634780391, tc[2], TestGlobals.saniDeltaSpline);
		assertEquals(0.716392338338791, tc[3], TestGlobals.saniDeltaSpline);
		assertEquals(1.09784029987021, tc[4], TestGlobals.saniDeltaSpline);

		tc = c.calculateTimeConstantsSpline(1, 2.3);
		assertEquals(0.177305373375394, tc[0], TestGlobals.saniDeltaSpline);
		assertEquals(0.232441548369716, tc[1], TestGlobals.saniDeltaSpline);
		assertEquals(0.304723271381742, tc[2], TestGlobals.saniDeltaSpline);
		assertEquals(0.399482247355776, tc[3], TestGlobals.saniDeltaSpline);
		assertEquals(0.523708167179986, tc[4], TestGlobals.saniDeltaSpline);
		assertEquals(0.686564287115260, tc[5], TestGlobals.saniDeltaSpline);

		tc = c.calculateTimeConstantsSpline(1, 2);
		assertEquals(0.134472180292776, tc[0], TestGlobals.saniDeltaSpline);
		assertEquals(0.170444700913804, tc[1], TestGlobals.saniDeltaSpline);
		assertEquals(0.216040195126938, tc[2], TestGlobals.saniDeltaSpline);
		assertEquals(0.273832895128191, tc[3], TestGlobals.saniDeltaSpline);
		assertEquals(0.347085663435123, tc[4], TestGlobals.saniDeltaSpline);
		assertEquals(0.439934207706506, tc[5], TestGlobals.saniDeltaSpline);
		assertEquals(0.557620574687113, tc[6], TestGlobals.saniDeltaSpline);

		tc = c.calculateTimeConstantsSpline(1, 1.6);
		assertEquals(0.168569381240628, tc[0], TestGlobals.saniDeltaSpline);
		assertEquals(0.184396961114740, tc[1], TestGlobals.saniDeltaSpline);
		assertEquals(0.201710648862226, tc[2], TestGlobals.saniDeltaSpline);
		assertEquals(0.220649980446818, tc[3], TestGlobals.saniDeltaSpline);
		assertEquals(0.241367593361100, tc[4], TestGlobals.saniDeltaSpline);
		assertEquals(0.264030456775730, tc[5], TestGlobals.saniDeltaSpline);
		assertEquals(0.288821217191768, tc[6], TestGlobals.saniDeltaSpline);
		assertEquals(0.315939670441090, tc[7], TestGlobals.saniDeltaSpline);
	}

    /**
     * Test the TimeConstants calculation with new spline function
     */
	@Test
	public void testcalculateTimeConstantsCubicNAK() throws Exception {
		SaniCurves c = new SaniCurves();

		// test powers 2 to 8
		double[] tc = c.calculateTimeConstantsCubicNAK(1, 10);
		assertEquals(2.81875934051940, tc[0], TestGlobals.saniDeltaCubicNAK);
		assertEquals(4.61187459765845, tc[1], TestGlobals.saniDeltaCubicNAK);

		tc = c.calculateTimeConstantsCubicNAK(1, 5);
		assertEquals(0.795152009551174, tc[0], TestGlobals.saniDeltaCubicNAK);
		assertEquals(1.26200896793459, tc[1], TestGlobals.saniDeltaCubicNAK);
		assertEquals(2.00297127595304, tc[2], TestGlobals.saniDeltaCubicNAK);

		tc = c.calculateTimeConstantsCubicNAK(1, 4);
		assertEquals(0.292245372354657, tc[0], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.525498853982710, tc[1], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.944921876135024, tc[2], TestGlobals.saniDeltaCubicNAK);
		assertEquals(1.69910428011687, tc[3], TestGlobals.saniDeltaCubicNAK);

		tc = c.calculateTimeConstantsCubicNAK(1, 3);
		assertEquals(0.199061019130236, tc[0], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.305052409467644, tc[1], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.467479634780391, tc[2], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.716392338338791, tc[3], TestGlobals.saniDeltaCubicNAK);
		assertEquals(1.09784029987021, tc[4], TestGlobals.saniDeltaCubicNAK);

		tc = c.calculateTimeConstantsCubicNAK(1, 2.3);
		assertEquals(0.177305373375394, tc[0], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.232441548369716, tc[1], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.304723271381742, tc[2], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.399482247355776, tc[3], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.523708167179986, tc[4], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.686564287115260, tc[5], TestGlobals.saniDeltaCubicNAK);

		tc = c.calculateTimeConstantsCubicNAK(1, 2);
		assertEquals(0.134472180292776, tc[0], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.170444700913804, tc[1], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.216040195126938, tc[2], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.273832895128191, tc[3], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.347085663435123, tc[4], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.439934207706506, tc[5], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.557620574687113, tc[6], TestGlobals.saniDeltaCubicNAK);

		tc = c.calculateTimeConstantsCubicNAK(1, 1.6);
		assertEquals(0.168569381240628, tc[0], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.184396961114740, tc[1], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.201710648862226, tc[2], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.220649980446818, tc[3], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.241367593361100, tc[4], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.264030456775730, tc[5], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.288821217191768, tc[6], TestGlobals.saniDeltaCubicNAK);
		assertEquals(0.315939670441090, tc[7], TestGlobals.saniDeltaCubicNAK);
	}
}