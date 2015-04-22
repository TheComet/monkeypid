package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IPlant;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IRegulator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.jfree.data.xy.XYSeries;

public class ClosedLoop {

    private IPlant plant;
    private IRegulator regulator;

    public ClosedLoop(IPlant plant, IRegulator regulator) {
        this.plant = plant;
        this.regulator = regulator;
    }

    public XYSeries calculateStepResponse() {
        return null;
    }

    public XYSeries exampleCalculate() {

        PolynomialSplineFunction function = SaniCurves.get().getTgInverseCurve(2);
        // construct XY dataset from the loaded data
        XYSeries series = new XYSeries("Test");
        for(double x = 0.0; x < 0.98; x += 0.01) {
            series.add(x, function.value(x));
        }

        return series;
    }
}