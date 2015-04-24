package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Controller;
import org.jfree.data.xy.XYSeries;

public class ClosedLoop {

    private Plant plant;
    private Controller controller;

    public ClosedLoop(Plant plant, Controller controller) {
        this.plant = plant;
        this.controller = controller;
    }

    public XYSeries calculateStepResponse() {
        return null;
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