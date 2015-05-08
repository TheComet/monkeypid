package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.ClosedLoopListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.MathArrays;
import org.jfree.data.xy.XYSeries;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClosedLoop {

    private TransferFunction transferFunction;
    private Plant plant;
    private AbstractController controller;
    private XYSeries stepResponse = null;
    private ArrayList<ClosedLoopListener> listeners = new ArrayList<>();
    private Color color = null;
    private double maxOverSwing;

    // stores where the calculated controller will be inserted into the table
    private int tableRowIndex = -1; // see issue #29

    public ClosedLoop(Plant plant, AbstractController controller) {
        setPlantAndController(plant, controller);
    }

    public void setPlantAndController(Plant plant, AbstractController controller) {
        this.plant = plant;
        this.controller = controller;
        this.transferFunction = calculateCloseLoopTransferFunction(plant, controller);
    }

    public void calculateStepResponse(int samplePoints) {

        List timeConstantsList = Arrays.asList(ArrayUtils.toObject(plant.getTimeConstants()));
        double tcMax = (double) Collections.max(timeConstantsList);
        double fs = 1.0/(tcMax/400.0);

        // round sample points to the next power of two
        int powerOfTwo = 4;
        while(powerOfTwo < samplePoints) {
            powerOfTwo <<= 1;
        }

        double [] omega = MathStuff.linspace(0, fs * Math.PI, powerOfTwo / 2);

        // calculate frequency response
        Complex[] H = MathStuff.freqs(transferFunction, omega);

        // calculate impulse response
        H = MathStuff.symmetricMirrorConjugate(H);
        Complex[] h = MathStuff.ifft(H);

        // calculate step response - note that h doesn't have an
        // imaginary part, so we can use conv as if it were a double
        double[] y = MathArrays.convolve(MathStuff.real(h), MathStuff.ones(powerOfTwo + 1));

        // cut away mirrored part
        y = Arrays.copyOfRange(y, 0, y.length / 2);

        // compute maximum overswing in percent - see issue #23
        maxOverSwing = MathStuff.max(y);
        maxOverSwing = (maxOverSwing - 1.0) * 100;

        // generate time axis
        double[] t = MathStuff.linspace(0, (y.length-1)/fs, y.length);

        // create XY data series for jfreechart
        stepResponse = new XYSeries(controller.getName());
        for(int i = 0; i < t.length; i++) {
            stepResponse.add(t[i], y[i]);
        }

        // done, notify
        notifyCalculationComplete();
    }

    private static TransferFunction calculateCloseLoopTransferFunction(Plant plant, AbstractController controller) {
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

    public String[] getTableRowStrings() {
        // get the strings the controller wants to insert into the table,
        // and expand the array by 1 to make space for the overswing value
        String[] controllerRow = getController().getTableRowStrings();
        String[] tableRow = new String[controllerRow.length + 1];
        System.arraycopy(controllerRow, 0, tableRow, 0, controllerRow.length);

        // insert overswing value
        String str = new DecimalFormat("00.0").format(maxOverSwing).replaceAll("\\G0", " ") + "%";
        str = str.replace(" .", "0."); // this stops regex from removing a 0 before the point
        tableRow[controllerRow.length] = str;

        return tableRow;
    }

    public final void registerListener(ClosedLoopListener listener) {
        listeners.add(listener);
    }

    public final void unregisterListener(ClosedLoopListener listener) {
        listeners.remove(listener);
    }

    private synchronized void notifyCalculationComplete() {
        for (ClosedLoopListener listener : listeners) {
            listener.onStepResponseCalculationComplete(this);
        }
    }

    public XYSeries getStepResponse() {
        return stepResponse;
    }

    public TransferFunction getTransferFunction() {
        return transferFunction;
    }

    public Plant getPlant() {
        return plant;
    }

    public AbstractController getController() {
        return controller;
    }

    public String getName() {
        return controller.getName();
    }

    public Color getColor() {
        return controller.getColor();
    }

    public void setColor(Color color) {
        this.color = color;
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

    public void setTableRowIndex(int index) {
        tableRowIndex = index;
    }

    public int getTableRowIndex() {
        return tableRowIndex;
    }
}