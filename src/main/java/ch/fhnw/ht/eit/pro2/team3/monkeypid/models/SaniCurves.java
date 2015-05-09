package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.Assets;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.SplineNAK;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This class is used to calculate the time constants required by the plant. The plant uses these to calculate its
 * transfer function.
 *
 * The time constants are calculated using the Sani approximation method.
 *
 * We use pre-defined curves exported from Matlab. These are loaded and interpolated for higher precision. There are
 * multiple interpolations available, the default being cubic NAK, which is what matlab uses. This class also offers
 * Apache's cubic spline interpolator and Apache's linear interpolator.
 * @author Alex Murray
 */
public class SaniCurves {

    public class TuTgRatioTooLargeException extends RuntimeException {
        public TuTgRatioTooLargeException(String message) {
            super(message);
        }
    }

    private int numTableColumns;

    // raw tables from matlab
    private ArrayList<double[]> tu_tg_ratio = null;
    private ArrayList<double[]> tg_reciprocal = null;

    // Linear interpolation
    private ArrayList<PolynomialSplineFunction> tu_tg_ratio_linear = new ArrayList<>();
    private ArrayList<PolynomialSplineFunction> tg_reciprocal_linear = new ArrayList<>();

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
        loadAndInterpolate();
    }

    /**
     * Returns the order of the plant as an integer value between 2 and 8. If the plant's order is higher than 8, an
     * exception will be thrown.
     * @param TuTgRatio The ratio of the plant parameters Tu to Tg (Tu/Tg).
     * @return The order between 2 and 8.
     */
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
        int order = 2;
        while(TuTgRatio > orderThresholds[order - 2]) {
            order++;

            // ratio is larger than all thresholds
            if(order - 2 >= orderThresholds.length) {
                throw new TuTgRatioTooLargeException("Tu/Tg zu gross n > 8  => Verhältnis kleiner wählen");
            }
        }

        return order;
    }

    /**
     * Calculates the time constants of a given plant using the Sani approximation method and default interpolation.
     * @param tu The plant parameter Tu.
     * @param tg The plant parameter Tg.
     * @return Returns a double array of time constants. Depending on the complexity of the plant, this array can have
     * up to 8 time constants.
     */
    public double[] calculateTimeConstants(double tu, double tg) {
        return calculateTimeConstantsCubicNAK(tu, tg);
    }

    /**
     * Calculates the Sani time constants of a plant with the parameters Tu and Tg by linearly interpolating the matlab
     * tables. The time constants are required for calculating the plant's transfer function later on.
     * @param tu The plant parameter Tu.
     * @param tg The plant parameter Tg.
     * @return Returns a double array of time constants. Depending on the complexity of the plant, this array can have
     * up to 8 time constants.
     */
    @Deprecated
    public double[] calculateTimeConstantsLinear(double tu, double tg) {

        double TuTgRatio = tu / tg;

        // get order
        int order = lookupOrder(TuTgRatio);

        // prepare return array (it has as many indices as the power, starting at ^2)
        double[] timeConstants = new double[order];

        // look up intersection points in interpolated matlab tables
        double r = tu_tg_ratio_linear.get(order - 2).value(TuTgRatio);
        double w = tg_reciprocal_linear.get(order - 2).value(r);

        // last time constant can now be calculated
        timeConstants[order - 1] = w * tg;

        // calculate the remaining time constants
        for(int i = order - 2; i >= 0; i--) {
            timeConstants[i] = timeConstants[order - 1] * Math.pow(r, order - i - 1);
        }

        return timeConstants;
    }

    /**
     * Calculates the Sani time constants of a plant with the parameters Tu and Tg by spline interpolating the matlab
     * tables with Apache's cubic spline. The time constants are required for calculating the plant's transfer function
     * later on.
     * @param tu The plant parameter Tu.
     * @param tg The plant parameter Tg.
     * @return Returns a double array of time constants. Depending on the complexity of the plant, this array can have
     * up to 8 time constants.
     */
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

    /**
     * Calculates the Sani time constants of a plant with the parameters Tu and Tg by spline interpolating the matlab
     * tables with the cubic NAK interpolator. The time constants are required for calculating the plant's transfer
     * function later on.
     * @param tu The plant parameter Tu.
     * @param tg The plant parameter Tg.
     * @return Returns a double array of time constants. Depending on the complexity of the plant, this array can have
     * up to 8 time constants.
     */
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

    /**
     * Loads both matlab tables Tu/Tg and 1/Tg.
     *
     * The tables *should* contain 6 rows worth of data points, each row corresponding to
     * a power. The powers start at n=2 and increment up to n=8. Therefore, the first
     * element in the array of rows is power 2, and the last is power 8.
     */
    private void loadAndInterpolate() {
        tu_tg_ratio = Assets.loadMatlabTable("math_tables/tu_tg_ratio");
        tg_reciprocal = Assets.loadMatlabTable("math_tables/tg_reciprocal");
        numTableColumns = tu_tg_ratio.get(0).length;

        calculateLinearFunctions();
        calculateSplineFunctions();
        calculateCubicNAKFunctions();
    }

    /**
     * Prepares the linear polynomial functions so there's less overhead when calculating the time constants later by
     * interpolating all 8 sani curves using Apache's linear interpolation.
     */
    private void calculateLinearFunctions() {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        double[]  r = MathStuff.linspace(0, 1, numTableColumns);

        // Tu/Tg interpolation swaps "row" and "r"
        tu_tg_ratio_linear.addAll(tu_tg_ratio.stream().map(row -> linearInterpolator.interpolate(
                row,
                r
        )).collect(Collectors.toList()));
        tg_reciprocal_linear.addAll(tg_reciprocal.stream().map(row -> linearInterpolator.interpolate(
                r,
                row
        )).collect(Collectors.toList()));
    }

    /**
     * Prepares the spline polynomial functions so there's less overhead when calculating the time constants later by
     * interpolating all 8 sani curves using Apache's cubic spline interpolation.
     */
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

    /**
     * Prepares the cubic NAK b, c, and d coefficients so there's less overhead when calculating the time constants
     * later.
     */
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
}
