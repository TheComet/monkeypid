package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.MathArrays;
import org.jfree.data.xy.XYSeries;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClosedLoop {

    private TransferFunction transferFunction;
    private Plant plant;

    public ClosedLoop(Plant plant, IController controller) {
        setPlantAndController(plant, controller);
    }

    public void setPlantAndController(Plant plant, IController controller) {
        this.plant = plant;
        this.transferFunction = calculateCloseLoopTransferFunction(plant, controller);
    }

    public XYSeries calculateStepResponse() {

        List timeConstantsList = Arrays.asList(ArrayUtils.toObject(plant.getTimeConstants()));
        double tcMin = (double) Collections.min(timeConstantsList);
        double tcMax = (double) Collections.max(timeConstantsList);
        double fs = 1.0/(tcMax/400.0);
        int N = 2048;

        double [] omega = MathStuff.linspace(0, fs * Math.PI, N / 2);

        // calculate frequency response
        Complex[] H = MathStuff.freqs(transferFunction, omega);

        // calculate impulse response
        H = MathStuff.symmetricMirrorConjugate(H);
        Complex[] h = MathStuff.ifft(H);

        // calculate step response - note that h doesn't have an
        // imaginary part, so we can use conv as if it were a double
        double[] y = MathArrays.convolve(MathStuff.real(h), MathStuff.ones(N + 1));

        // cut away mirrored part
        y = Arrays.copyOfRange(y, 0, y.length / 2);

        // generate time axis
        double[] t = MathStuff.linspace(0, (y.length-1)/fs, y.length);

        // create XY data series for jfreechart
        XYSeries series = new XYSeries(""); // TODO name?
        for(int i = 0; i < t.length; i++) {
            series.add(t[i], y[i]);
        }

        return series;
    }

    private static TransferFunction calculateCloseLoopTransferFunction(Plant plant, IController controller) {
        double[] numeratorCoefficients = MathArrays.convolve(
                plant.getTransferFunction().getNumeratorCoefficients(),
                controller.getTransferFunction().getNumeratorCoefficients()
        );
        double[] denominatorCoefficients = MathArrays.convolve(
                plant.getTransferFunction().getDenominatorCoefficients(),
                controller.getTransferFunction().getDenominatorCoefficients()
        );

        // add denominator coefficients to the end of the numerator coefficients.
        // E.g. numerator = 1 2 3 4 5 6 7
        //    denominator =         1 2 3
        //         result = 1 2 3 4 6 8 10
        int index = denominatorCoefficients.length - numeratorCoefficients.length;
        for(double numeratorCoefficient : numeratorCoefficients) {
            denominatorCoefficients[index] += numeratorCoefficient;
            index++;
        }

        return new TransferFunction(numeratorCoefficients, denominatorCoefficients);
    }

    public TransferFunction getTransferFunction() {
        return transferFunction;
    }

    public XYSeries exampleCalculate() {

        double[][] data = {{0, 1, 2, 3}, {3, 5, 4, 6}};

        // construct XY dataset from the loaded data
        XYSeries series = new XYSeries("Test");
        for(int i = 0; i < data[0].length; i++) {
            series.add(data[0][i], data[1][i]);
        }

        return series;
    }
}