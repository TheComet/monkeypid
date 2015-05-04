package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.Assets;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import java.util.ArrayList;

/**
 * @author Alex Murray
 */
public class SaniCurves {
    private ArrayList<PolynomialSplineFunction> Tu_Tg_ratio = null;
    private ArrayList<PolynomialSplineFunction> Tg_inverse = null;

    /**
     * Loads
     */
    public SaniCurves() {
        loadMatlabTables();
    }

    /**
     * Loads both matlab tables Tu/Tg and 1/Tg.
     *
     * The tables *should* contain 6 rows worth of data points, each row corresponding to
     * a power. The powers start at n=2 and increment up to n=8. Therefore, the first
     * element in the array of rows is power 2, and the last is power 8.
     */
    private void loadMatlabTables() {
        Tu_Tg_ratio = Assets.loadSaniCurves("math_tables/tu_tg_ratio", true); // x and y data points are swapped
        Tg_inverse = Assets.loadSaniCurves("math_tables/tg_inverse", false);
    }

    public int lookupPower(double TuTgRatio) {

        // These are magic numbers extracted from the matlab file "p2_sani.m", based on
        // the document "Buergi, Solenicki - V3.pdf", page 5.
        double[] orderThresholds = {
                0.103638,
                0.218017,
                0.319357,
                0.410303,
                0.4933,
                0.5700,
                0.64173
        };

        // Find the order of the function based on the ratio of Tu to Tg.
        // order of 1 is invalid. Orders 2 through 8 are decided depending on the
        // magic numbers compared to the ratio. If the ratio is larger than all of
        // the magic numbers, then throw an exception.
        int power = 2;
        while(TuTgRatio > orderThresholds[power - 2]) {
            power++;

            // ratio is larger than all thresholds
            if(power - 2 >= orderThresholds.length) {
                throw new RuntimeException("Order is larger than 8, don't have a lookup table for that");
            }
        }

        return power;
    }

    public double[] calculateTimeConstants(double tu, double tg) {

        double TuTgRatio = tu / tg;

        // get power level
        int power = lookupPower(TuTgRatio);

        // prepare return array (it has as many indices as the power, starting at ^2)
        double[] timeConstants = new double[power];

        // look up intersection points in interpolated matlab tables
        double r = getTuTgRatioCurve(power).value(TuTgRatio);
        double w = getTgInverseCurve(power).value(r);

        // last time constant can now be calculated
        timeConstants[power - 1] = w * tg;

        // calculate the other time constants
        for(int i = power - 2; i >= 0; i--) {
            timeConstants[i] = timeConstants[power - 1] * Math.pow(r, power - i - 1);
        }

        return timeConstants;
    }

    public PolynomialSplineFunction getTuTgRatioCurve(int power) {
        return Tu_Tg_ratio.get(power - 2);
    }

    public PolynomialSplineFunction getTgInverseCurve(int power) {
        return Tg_inverse.get(power - 2);
    }
}
