package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.TestGlobals;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlantTest {

    private SaniCurves sani = new SaniCurves();

    @Test
    public void testTimeConstantsTu2Tg6Ks1() throws Exception {
        Plant plant = new Plant(2, 6, 1, sani);
        assertEquals(0.398122038260471, plant.getTimeConstants()[0], TestGlobals.saniDelta);
        assertEquals(0.610104818935287, plant.getTimeConstants()[1], TestGlobals.saniDelta);
        assertEquals(0.934959269560781, plant.getTimeConstants()[2], TestGlobals.saniDelta);
        assertEquals(1.43278467667758, plant.getTimeConstants()[3], TestGlobals.saniDelta);
        assertEquals(2.19568059974042, plant.getTimeConstants()[4], TestGlobals.saniDelta);
    }

    @Test
    public void testTimeConstantsTu2Tg9Ks3() throws Exception {
        Plant plant = new Plant(2, 9, 3, sani);
        assertEquals(0.466382178287074, plant.getTimeConstants()[0], TestGlobals.saniDelta);
        assertEquals(0.971902999577522, plant.getTimeConstants()[1], TestGlobals.saniDelta);
        assertEquals(2.02536778754516, plant.getTimeConstants()[2], TestGlobals.saniDelta);
        assertEquals(4.22070379102517, plant.getTimeConstants()[3], TestGlobals.saniDelta);
    }

    @Test
    public void testConstructorTransferFunctionTu2Tg6Ks1() throws Exception {
        Plant plant = new Plant(2, 6, 1, sani);

        // numerator should be Ks
        assertEquals(1, plant.getTransferFunction().getNumeratorCoefficients().length);
        assertEquals(1.0, plant.getTransferFunction().getNumeratorCoefficients()[0], TestGlobals.plantTransferDelta);

        // compare denominator with results from matlab
        assertEquals(6, plant.getTransferFunction().getDenominatorCoefficients().length);
        assertEquals(0.714436211262539, plant.getTransferFunction().getDenominatorCoefficients()[0], TestGlobals.plantTransferDelta);
        assertEquals(4.55367479337852, plant.getTransferFunction().getDenominatorCoefficients()[1], TestGlobals.plantTransferDelta);
        assertEquals(10.6419570918724, plant.getTransferFunction().getDenominatorCoefficients()[2], TestGlobals.plantTransferDelta);
        assertEquals(11.3822681247620, plant.getTransferFunction().getDenominatorCoefficients()[3], TestGlobals.plantTransferDelta);
        assertEquals(5.57165140317454, plant.getTransferFunction().getDenominatorCoefficients()[4], TestGlobals.plantTransferDelta);
        assertEquals(1.00000000000000, plant.getTransferFunction().getDenominatorCoefficients()[5], TestGlobals.plantTransferDelta);
    }

    @Test
    public void testConstructorTransferFunctionTu2Tg9Ks3() throws Exception {
        Plant plant = new Plant(2, 9, 3, sani);

        // numerator should be Ks
        assertEquals(1, plant.getTransferFunction().getNumeratorCoefficients().length);
        assertEquals(3.0, plant.getTransferFunction().getNumeratorCoefficients()[0], TestGlobals.plantTransferDelta);

        // compare denominator with results from matlab
        assertEquals(5, plant.getTransferFunction().getDenominatorCoefficients().length);
        assertEquals(3.87483881860849, plant.getTransferFunction().getDenominatorCoefficients()[0], TestGlobals.plantTransferDelta);
        assertEquals(15.1263568000050, plant.getTransferFunction().getDenominatorCoefficients()[1], TestGlobals.plantTransferDelta);
        assertEquals(17.9853879084779, plant.getTransferFunction().getDenominatorCoefficients()[2], TestGlobals.plantTransferDelta);
        assertEquals(7.68435675643493, plant.getTransferFunction().getDenominatorCoefficients()[3], TestGlobals.plantTransferDelta);
        assertEquals(1.00000000000000, plant.getTransferFunction().getDenominatorCoefficients()[4], TestGlobals.plantTransferDelta);
    }

    @Test
    public void testSetParametersTransferFunctionTu2Tg6Ks1() throws Exception {
        Plant plant = new Plant(0, 1, 14, sani); // some garbage values that don't throw an exception

        // this is what's being tested
        plant.setParameters(2, 6, 1);

        // numerator should be Ks
        assertEquals(1, plant.getTransferFunction().getNumeratorCoefficients().length);
        assertEquals(1.0, plant.getTransferFunction().getNumeratorCoefficients()[0], TestGlobals.plantTransferDelta);

        // compare denominator with results from matlab
        assertEquals(6, plant.getTransferFunction().getDenominatorCoefficients().length);
        assertEquals(0.714436211262539, plant.getTransferFunction().getDenominatorCoefficients()[0], TestGlobals.plantTransferDelta);
        assertEquals(4.55367479337852, plant.getTransferFunction().getDenominatorCoefficients()[1], TestGlobals.plantTransferDelta);
        assertEquals(10.6419570918724, plant.getTransferFunction().getDenominatorCoefficients()[2], TestGlobals.plantTransferDelta);
        assertEquals(11.3822681247620, plant.getTransferFunction().getDenominatorCoefficients()[3], TestGlobals.plantTransferDelta);
        assertEquals(5.57165140317454, plant.getTransferFunction().getDenominatorCoefficients()[4], TestGlobals.plantTransferDelta);
        assertEquals(1.00000000000000, plant.getTransferFunction().getDenominatorCoefficients()[5], TestGlobals.plantTransferDelta);
    }

    @Test
    public void testSetParametersTtransferFunctionTu2Tg9Ks3() throws Exception {
        Plant plant = new Plant(0, 1, 18, sani); // some garbage values that don't throw an exception

        // this is what's being tested
        plant.setParameters(2, 9, 3);

        // numerator should be Ks
        assertEquals(1, plant.getTransferFunction().getNumeratorCoefficients().length);
        assertEquals(3.0, plant.getTransferFunction().getNumeratorCoefficients()[0], TestGlobals.plantTransferDelta);

        // compare denominator with results from matlab
        assertEquals(5, plant.getTransferFunction().getDenominatorCoefficients().length);
        assertEquals(3.87483881860849, plant.getTransferFunction().getDenominatorCoefficients()[0], TestGlobals.plantTransferDelta);
        assertEquals(15.1263568000050, plant.getTransferFunction().getDenominatorCoefficients()[1], TestGlobals.plantTransferDelta);
        assertEquals(17.9853879084779, plant.getTransferFunction().getDenominatorCoefficients()[2], TestGlobals.plantTransferDelta);
        assertEquals(7.68435675643493, plant.getTransferFunction().getDenominatorCoefficients()[3], TestGlobals.plantTransferDelta);
        assertEquals(1.00000000000000, plant.getTransferFunction().getDenominatorCoefficients()[4], TestGlobals.plantTransferDelta);
    }
}