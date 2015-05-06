package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.TestGlobals;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import org.junit.Test;

public class ClosedLoopTest {
    private SaniCurves sani = new SaniCurves();

    @Test
    public void testCalculateTransferFunctionWithConstructor() {
        Plant plant = new Plant(2, 6, 1, sani);
        IController controller = new PIController("test", 1, 1);

        plant.getTransferFunction().setNumeratortorCoefficients(new double[]{11, 12, 13, 14});
        plant.getTransferFunction().setDenominatorCoefficients(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        controller.getTransferFunction().setNumeratortorCoefficients(new double[]{25, 26, 27, 28, 29});
        controller.getTransferFunction().setDenominatorCoefficients(new double[]{15, 16, 17, 18, 19, 20, 21, 22, 23, 24});

        // this should calculate the closed loop transfer function
        ClosedLoop loop = new ClosedLoop(plant, controller);

        double[] expectedNumerator = new double[] {275, 586, 934, 1320, 1370, 1090, 769, 406};
        double[] expectedDenominator = new double[] {15, 46, 94, 160, 245, 350, 476, 624, 795, 990, 1020, 1299, 1587, 1884, 2190, 2130, 1709, 1215, 646};
        assertArrayEquals(loop.getTransferFunction().getNumeratorCoefficients(), expectedNumerator, TestGlobals.floatDelta);
        assertArrayEquals(loop.getTransferFunction().getDenominatorCoefficients(), expectedDenominator, TestGlobals.floatDelta);
    }

    @Test
    public void testSetPlantAndController() {

        // these are values taken from Simulation_Richard_Gut.m
        Plant plant = new Plant(1.71, 7.6, 1, sani);
        IController controller = new PIController("test", 1.14, 3.58);

        // create close loop using dummy plant and controller
        ClosedLoop loop = new ClosedLoop(new Plant(1, 10, 1, sani), new PIController("", 1, 1));

        // Replace dummy plant and controller with real plant and controller.
        // This should calculate the closed loop transfer function.
        loop.setPlantAndController(plant, controller);

        double delta = 0.2; // matlab script only used very approximate values
        double[] expectedNumerator = new double[] {4.08120000000000, 1.14000000000000};
        double[] expectedDenominator = new double[] {7.49023271808000, 33.6612679040000, 46.4842952000000, 23.2771600000000, 7.66120000000000, 1.14000000000000};
        assertArrayEquals(loop.getTransferFunction().getNumeratorCoefficients(), expectedNumerator, delta);
        assertArrayEquals(loop.getTransferFunction().getDenominatorCoefficients(), expectedDenominator, delta);
    }
}