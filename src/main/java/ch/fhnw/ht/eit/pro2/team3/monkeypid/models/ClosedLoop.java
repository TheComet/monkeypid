package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.jfree.data.xy.XYSeries;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClosedLoop {

    private Plant plant;
    private IController controller;

    public ClosedLoop(Plant plant, IController controller) {
        this.plant = plant;
        this.controller = controller;
    }

    public XYSeries calculateStepResponse() {

        TransferFunction tfClosedLoop = calculateCloseLoopTransferFunction();

        List timeConstantsList = Arrays.asList(ArrayUtils.toObject(plant.getTimeConstants()));
        double tcMin = (double) Collections.min(timeConstantsList);
        double tcMax = (double) Collections.max(timeConstantsList);
        double fs = 1.0/(tcMax/400.0);
        int N = 2048;

        double [] omega = MathStuff.linspace(0, fs * Math.PI, N / 2);

        // calculate frequency response
        Complex[] H = MathStuff.freqs(tfClosedLoop, omega);

        // calculate impulse response
        H = MathStuff.symmetricMirrorConjugate(H);
        Complex[] h = MathStuff.ifft(H);

        // calculate step response - note that h doesn't have an
        // imaginary part, so we can use conv as if it were a double
        double[] y = MathStuff.conv(MathStuff.real(h), MathStuff.ones(N+1));

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

    private TransferFunction calculateCloseLoopTransferFunction() {
        double[] numeratorCoefficients = MathStuff.conv(
                plant.getTransferFunction().getNumeratorCoefficients(),
                controller.getTransferFunction().getNumeratorCoefficients()
        );
        double[] denominatorCoefficients = MathStuff.conv(
                plant.getTransferFunction().getDenominatorCoefficients(),
                controller.getTransferFunction().getDenominatorCoefficients()
        );

        for (int i = 0; i < numeratorCoefficients.length ; i++) {
            denominatorCoefficients[denominatorCoefficients.length - numeratorCoefficients.length + i] +=
                    numeratorCoefficients[i];
        }

        return new TransferFunction(numeratorCoefficients, denominatorCoefficients);
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