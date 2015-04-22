package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.jfree.data.xy.XYSeries;

public class StepResponse {

    public static XYSeries exampleCalculate() {

        double[][] data = {{0, 1, 2, 3}, {2, 7, 3, 8}};

        SplineInterpolator interpolator = new SplineInterpolator();
        PolynomialSplineFunction function = interpolator.interpolate(data[0], data[1]);

        // construct XY dataset from the loaded data
        XYSeries series = new XYSeries("Test");
        for(double x = 0.0; x < 3.0; x += 0.01) {
            series.add(x, function.value(x));
        }

        return series;
    }
}