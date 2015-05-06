package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import org.junit.Test;

public class ClosedLoopTest {
    private SaniCurves sani = new SaniCurves();
    private double[] bs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private double[] br = {11, 12, 13, 14};
    private double[] as = {15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
    private double[] ar = {25, 26, 27, 28, 29};

    @Test
    public void testTransferFunctionClosedLoop() {
        Plant plant = new Plant(2, 6, 1, sani);
        IController controller = new PIController("test", 1, 1);

        plant.getTransferFunction().setNumeratortorCoefficients(bs);
        plant.getTransferFunction().setDenominatorCoefficients(as);
        controller.getTransferFunction().setNumeratortorCoefficients(br);
        controller.getTransferFunction().setDenominatorCoefficients(ar);

        ClosedLoop loop = new ClosedLoop(plant, controller);
    }
}