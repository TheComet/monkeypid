package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.TestGlobals;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlantTest {

    private SaniCurves sani = new SaniCurves();

    @Test
    public void testTimeConstantsTu2Tg6Ks1() throws Exception {
        Plant plant = new Plant(2, 6, 1, sani);
        assertEquals(0.3981, plant.getTimeConstants()[0], TestGlobals.saniDelta);
        assertEquals(0.6101, plant.getTimeConstants()[1], TestGlobals.saniDelta);
        assertEquals(0.9350, plant.getTimeConstants()[2], TestGlobals.saniDelta);
        assertEquals(1.4328, plant.getTimeConstants()[3], TestGlobals.saniDelta);
        assertEquals(2.1957, plant.getTimeConstants()[4], TestGlobals.saniDelta);
    }

    @Test
    public void testTimeConstantsTu2Tg9Ks3() throws Exception {
        Plant plant = new Plant(2, 9, 3, sani);
        assertEquals(0.4664, plant.getTimeConstants()[0], TestGlobals.saniDelta);
        assertEquals(0.9721, plant.getTimeConstants()[1], TestGlobals.saniDelta);
        assertEquals(2.0254, plant.getTimeConstants()[2], TestGlobals.saniDelta);
        assertEquals(4.2207, plant.getTimeConstants()[3], TestGlobals.saniDelta);
    }

    @Test
    public void testConstructorTransferFunctionTu2Tg6Ks1() throws Exception {
        Plant plant = new Plant(2, 6, 1, sani);

        // numerator should be Ks
        assertEquals(1, plant.getTransferFunction().getNumeratorCoefficients().length);
        assertEquals(1.0, plant.getTransferFunction().getNumeratorCoefficients()[0], TestGlobals.plantTransferDelta);

        // compare denominator with results from matlab
        assertEquals(6, plant.getTransferFunction().getDenominatorCoefficients().length);
        assertEquals(0.7144, plant.getTransferFunction().getDenominatorCoefficients()[0], TestGlobals.plantTransferDelta);
        assertEquals(4.5537, plant.getTransferFunction().getDenominatorCoefficients()[1], TestGlobals.plantTransferDelta);
        assertEquals(10.642, plant.getTransferFunction().getDenominatorCoefficients()[2], TestGlobals.plantTransferDelta);
        assertEquals(11.382, plant.getTransferFunction().getDenominatorCoefficients()[3], TestGlobals.plantTransferDelta);
        assertEquals(5.5717, plant.getTransferFunction().getDenominatorCoefficients()[4], TestGlobals.plantTransferDelta);
        assertEquals(1.0000, plant.getTransferFunction().getDenominatorCoefficients()[5], TestGlobals.plantTransferDelta);
    }

    @Test
    public void testConstructorTransferFunctionTu2Tg9Ks3() throws Exception {
        Plant plant = new Plant(2, 9, 3, sani);

        // numerator should be Ks
        assertEquals(1, plant.getTransferFunction().getNumeratorCoefficients().length);
        assertEquals(3.0, plant.getTransferFunction().getNumeratorCoefficients()[0], TestGlobals.plantTransferDelta);

        // compare denominator with results from matlab
        assertEquals(5, plant.getTransferFunction().getDenominatorCoefficients().length);
        assertEquals(3.8748, plant.getTransferFunction().getDenominatorCoefficients()[0], TestGlobals.plantTransferDelta);
        assertEquals(15.126, plant.getTransferFunction().getDenominatorCoefficients()[1], TestGlobals.plantTransferDelta);
        assertEquals(17.985, plant.getTransferFunction().getDenominatorCoefficients()[2], TestGlobals.plantTransferDelta);
        assertEquals(7.6843, plant.getTransferFunction().getDenominatorCoefficients()[3], TestGlobals.plantTransferDelta);
        assertEquals(1.0000, plant.getTransferFunction().getDenominatorCoefficients()[4], TestGlobals.plantTransferDelta);
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
        assertEquals(0.7144, plant.getTransferFunction().getDenominatorCoefficients()[0], TestGlobals.plantTransferDelta);
        assertEquals(4.5537, plant.getTransferFunction().getDenominatorCoefficients()[1], TestGlobals.plantTransferDelta);
        assertEquals(10.641, plant.getTransferFunction().getDenominatorCoefficients()[2], TestGlobals.plantTransferDelta);
        assertEquals(11.382, plant.getTransferFunction().getDenominatorCoefficients()[3], TestGlobals.plantTransferDelta);
        assertEquals(5.5717, plant.getTransferFunction().getDenominatorCoefficients()[4], TestGlobals.plantTransferDelta);
        assertEquals(1.0000, plant.getTransferFunction().getDenominatorCoefficients()[5], TestGlobals.plantTransferDelta);
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
        assertEquals(3.8748, plant.getTransferFunction().getDenominatorCoefficients()[0], TestGlobals.plantTransferDelta);
        assertEquals(15.126, plant.getTransferFunction().getDenominatorCoefficients()[1], TestGlobals.plantTransferDelta);
        assertEquals(17.985, plant.getTransferFunction().getDenominatorCoefficients()[2], TestGlobals.plantTransferDelta);
        assertEquals(7.6844, plant.getTransferFunction().getDenominatorCoefficients()[3], TestGlobals.plantTransferDelta);
        assertEquals(1.0000, plant.getTransferFunction().getDenominatorCoefficients()[4], TestGlobals.plantTransferDelta);
    }
}