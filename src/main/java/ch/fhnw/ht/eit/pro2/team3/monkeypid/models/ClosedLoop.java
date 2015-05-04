package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import org.jfree.data.xy.XYSeries;

public class ClosedLoop {

    private Plant plant;
    private IController IController;

    public ClosedLoop(Plant plant, IController IController) {
        this.plant = plant;
        this.IController = IController;
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