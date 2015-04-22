package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.jfree.data.xy.XYSeries;

public class StepResponse {

    public static XYSeries exampleCalculate() {

        PolynomialSplineFunction function = SaniCurves.get().getTuTgRatioCurve(2);
        // construct XY dataset from the loaded data
        XYSeries series = new XYSeries("Test");
        for(double x = 0.0; x < 0.98; x += 0.01) {
            series.add(x, function.value(x));
        }

        return series;
    }
}