package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.Assets;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.SplineNAK;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author Alex Murray
 */
public class SaniCurves {

    public class TuTgRatioTooLargeException extends RuntimeException {
        public TuTgRatioTooLargeException(String message) {
            super(message);
        }
    }

    private int numTableColumns;

    private ArrayList<double[]> tu_tg_ratio = null;
    private ArrayList<double[]> tg_reciprocal = null;

    // Linear interpolation
    private ArrayList<PolynomialSplineFunction> tu_tg_ratio_linear = null;
    private ArrayList<PolynomialSplineFunction> tg_reciprocal_linear = null;

    // SplineInterpolation
    private ArrayList<PolynomialSplineFunction> tu_tg_ratio_spline = new ArrayList<>();
    private ArrayList<PolynomialSplineFunction> tg_reciprocal_spline = new ArrayList<>();
    
    // CubicNAK interpolation
    private ArrayList<double[][]> tu_tg_ratio_cubic_nak = new ArrayList<>();
    private ArrayList<double[][]> tg_reciprocal_cubic_nak = new ArrayList<>();
    
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
        tu_tg_ratio = Assets.loadMatlabTable("math_tables/tu_tg_ratio");
        tg_reciprocal = Assets.loadMatlabTable("math_tables/tg_reciprocal");
        tu_tg_ratio_linear = Assets.loadSaniCurves("math_tables/tu_tg_ratio", true);
        tg_reciprocal_linear = Assets.loadSaniCurves("math_tables/tg_reciprocal", false);
        numTableColumns = tu_tg_ratio.get(0).length;

        calculateLinearFunctions();
        calculateSplineFunctions();
        calculateCubicNAKFunctions();
    }

    private void calculateLinearFunctions() {

    }
    
    //calculate Spline Functions for all orders
    private void calculateSplineFunctions() {

        SplineInterpolator splineInterpolator = new SplineInterpolator();
        double[] r = MathStuff.linspace(0, 1, numTableColumns);

        // Tu/Tg spline has "row" and "r" swapped
        tu_tg_ratio_spline.addAll(tu_tg_ratio.stream().map(row -> splineInterpolator.interpolate(
                row,
                r
        )).collect(Collectors.toList()));
        tg_reciprocal_spline.addAll(tg_reciprocal.stream().map(row -> splineInterpolator.interpolate(
                r,
                row
        )).collect(Collectors.toList()));
    }
    
    //calculate double arrays b, c, d of all orders for CubicNAK  
    private void calculateCubicNAKFunctions(){

        double[] r = MathStuff.linspace(0, 1, numTableColumns);
        
        // Tu/Tg spline has "row" and "r" swapped
        for(double[] row : tu_tg_ratio ) {
            double[][] bcdRow = new double[3][numTableColumns]; // b, c, d
            SplineNAK.cubic_nak(numTableColumns,
                                row,
                                r,
                                bcdRow[0], bcdRow[1], bcdRow[2]);
            tu_tg_ratio_cubic_nak.add(bcdRow);
        }

        for(double[] row : tg_reciprocal ) {
            double[][] bcdRow = new double[3][numTableColumns]; // b, c, d
            SplineNAK.cubic_nak(numTableColumns,
                                r,
                                row,
                                bcdRow[0], bcdRow[1], bcdRow[2]);
            tg_reciprocal_cubic_nak.add(bcdRow);
        }
    }

    public int lookupOrder(double TuTgRatio) {

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
                throw new TuTgRatioTooLargeException("Tu/Tg zu gross n > 8  => Verhältnis kleiner wählen");
            }
        }

        return power;
    }

    //Linear Interpolation (used until now)
    public double[] calculateTimeConstants(double tu, double tg) {

        double TuTgRatio = tu / tg;

        // get order
        int order = lookupOrder(TuTgRatio);

        // prepare return array (it has as many indices as the power, starting at ^2)
        double[] timeConstants = new double[order];

        // look up intersection points in interpolated matlab tables
        double r = getTuTgRatioCurve(order).value(TuTgRatio);
        double w = getTgInverseCurve(order).value(r);

        // last time constant can now be calculated
        timeConstants[order - 1] = w * tg;

        // calculate the remaining time constants
        for(int i = order - 2; i >= 0; i--) {
            timeConstants[i] = timeConstants[order - 1] * Math.pow(r, order - i - 1);
        }

        return timeConstants;
    }
    
    //Spline Interpolation (a bit more accurate than LinearInterpolator)
    public double[] calculateTimeConstantsSpline(double tu, double tg) {

        double TuTgRatio = tu / tg;

        // get order
        int order = lookupOrder(TuTgRatio);

        // prepare return array (it has as many indices as the power, starting at ^2)
        double[] timeConstants = new double[order];

        //get intersection points from Spline PolynomialFunctions
        double r = tu_tg_ratio_spline.get(order-2).value(TuTgRatio);
        double w = tg_reciprocal_spline.get(order-2).value(r);
        
        // last time constant can now be calculated
        timeConstants[order - 1] = w * tg;

        // calculate the remaining time constants
        for(int i = order - 2; i >= 0; i--) {
            timeConstants[i] = timeConstants[order - 1] * Math.pow(r, order - i - 1);
        }
        
        return timeConstants;
    }
    
    // Cubic NAK (10e10 times more accurate than LinearInterpolator - very good!)
    public double[] calculateTimeConstantsCubicNAK(double tu, double tg) {

        double TuTgRatio = tu / tg;

        // get order
        int order = lookupOrder(TuTgRatio);

        // prepare return array (it has as many indices as the power, starting at ^2)
        double[] timeConstants = new double[order];

        //get intersection points with Cubic-NAK algorithm

        //Tu_Tg 
        //get b, c, d
        double[][] bcdRow = tu_tg_ratio_cubic_nak.get(order-2);
        //get x, y, of tu_tg_ratio_linear
        double[] rowTu_Tg_ratio = new double[numTableColumns];
        for (int j = 0; j < rowTu_Tg_ratio.length; j++) {
            rowTu_Tg_ratio[j] = tu_tg_ratio.get(order-2)[j];
        }
        //spline_eval needs again the double arrays x, y, of tu_tg_ratio_linear
        double r = SplineNAK.spline_eval(numTableColumns,
            rowTu_Tg_ratio,
            MathStuff.linspace(0, 1, numTableColumns),
            bcdRow[0], bcdRow[1], bcdRow[2],
            TuTgRatio);
        
        //Tg_T
        //get b, c, d;
        double[][] bcdRow2 = tg_reciprocal_cubic_nak.get(order-2);
        //get x, y, of Tg_T
        double[] rowTg_inverse = new double[numTableColumns];
        for (int j = 0; j < rowTg_inverse.length; j++) {
            rowTg_inverse[j] = tg_reciprocal.get(order-2)[j];
        }
        //spline_eval needs again the double arrays x, y, of Tg_T
        double w = SplineNAK.spline_eval(numTableColumns, MathStuff.linspace(0, 1, numTableColumns), rowTg_inverse, bcdRow2[0], bcdRow2[1], bcdRow2[2], r);
        
        // last time constant can now be calculated
        timeConstants[order - 1] = w * tg;

        // calculate the remaining time constants
        for(int i = order - 2; i >= 0; i--) {
            timeConstants[i] = timeConstants[order - 1] * Math.pow(r, order - i - 1);
        }
        
        return timeConstants;
    }

    private PolynomialSplineFunction getTuTgRatioCurve(int order) {
        return tu_tg_ratio_linear.get(order - 2);
    }

    private PolynomialSplineFunction getTgInverseCurve(int order) {
        return tg_reciprocal_linear.get(order - 2);
    }
}
