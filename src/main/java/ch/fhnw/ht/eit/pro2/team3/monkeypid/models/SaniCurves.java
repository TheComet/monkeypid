package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.Assets;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.SplineNAK;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.ArrayList;

/**
 * @author Alex Murray
 */
public class SaniCurves {
    private ArrayList<PolynomialSplineFunction> Tu_Tg_ratio = null;
    private ArrayList<PolynomialSplineFunction> Tg_inverse = null;
    
    //interpolation tests
    private  ArrayList<ArrayList<Double>> Tu_Tg_ratio_double = new ArrayList<>();
    private  ArrayList<ArrayList<Double>> Tg_inverse_double = new ArrayList<>();

    //SplineInterpolation
    static final SplineInterpolator interpolatorSpline = new SplineInterpolator();
    private ArrayList<PolynomialSplineFunction> Tu_Tg_ratioSpline = new ArrayList<>();
    private ArrayList<PolynomialSplineFunction> Tg_inverseSpline = new ArrayList<>();
    
    //CubicNAK interpolation
    private ArrayList<double[][]> Tu_Tg_ratioCubicNAK = new ArrayList<>();
    private ArrayList<double[][]> Tg_inverseCubicNAK = new ArrayList<>();
    
    /**
     * Loads
     */
    public SaniCurves() {
        loadMatlabTables();
        
        //interpolation test
        loadMatlabTablesDouble(); //loads the double arrays from the resource files
        calculateSplineFunctions(); //calculates the PolynomialSplineFunctions with Spline interpolation
        calculateCubicNAKFunctions(); //calculates three double arrays for Cubic-NAK interpolation
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
    
    private void loadMatlabTablesDouble(){
    	Tu_Tg_ratio_double = Assets.loadSaniCurvesDoubleValues("math_tables/tu_tg_ratio");
    	Tg_inverse_double = Assets.loadSaniCurvesDoubleValues("math_tables/tg_inverse");
    }
    
    //calculate Spline Functions for all orders
    private void calculateSplineFunctions(){
    	//inverse
    	for(ArrayList<Double> i : Tu_Tg_ratio_double ) {
    		double[] row = new  double[i.size()];
    		for (int j = 0; j < row.length; j++) {
				row[j] = (double) i.get(j);
			}
	    	Tu_Tg_ratioSpline.add(interpolatorSpline.interpolate(
	    			row,
	    			MathStuff.linspace(0, 1, Tu_Tg_ratio_double.get(0).size())	
	    			));
    	}
    	//not inverse
    	for(ArrayList<Double> i : Tg_inverse_double ) {
    		double[] row = new  double[i.size()];
    		for (int j = 0; j < row.length; j++) {
				row[j] = i.get(j);
			}
    		Tg_inverseSpline.add(interpolatorSpline.interpolate(
    				MathStuff.linspace(0, 1, Tg_inverse_double.get(0).size()),
    				row
	    	));
    	}
    }
    
    //calculate double arrays b, c, d of all orders for CubicNAK  
    private void calculateCubicNAKFunctions(){
		int n = Tu_Tg_ratio_double.get(0).size();
		
		//inverse
		for(ArrayList<Double> i : Tu_Tg_ratio_double ) {
    		double[] row = new  double[i.size()];
    		for (int j = 0; j < row.length; j++) {
				row[j] = (double) i.get(j);
			}
    		double[][] bcdRow = new double[3][n]; //b, c, d
    		SplineNAK.cubic_nak(n, row, MathStuff.linspace(0, 1, n), bcdRow[0], bcdRow[1], bcdRow[2]);
    	    Tu_Tg_ratioCubicNAK.add(bcdRow);
    	}
		//not inverse
		for(ArrayList<Double> i : Tg_inverse_double ) {
    		double[] row = new  double[i.size()];
    		for (int j = 0; j < row.length; j++) {
				row[j] = (double) i.get(j);
			}
    		double[][] bcdRow = new double[3][n];
    		SplineNAK.cubic_nak(n, MathStuff.linspace(0, 1, n), row, bcdRow[0], bcdRow[1], bcdRow[2]);
    		Tg_inverseCubicNAK.add(bcdRow);
    	}
		
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

    //Linear Interpolation (used until now)
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
    
    //Spline Interpolation (a bit more accurate than LinearInterpolator)
    public double[] calculateTimeConstantsSpline(double tu, double tg) {

        double TuTgRatio = tu / tg;

        // get power level
        int power = lookupPower(TuTgRatio);

        // prepare return array (it has as many indices as the power, starting at ^2)
        double[] timeConstants = new double[power];

        //get intersection points from Spline PolynomialFunctions
        double r = Tu_Tg_ratioSpline.get(power-2).value(TuTgRatio);
        double w = Tg_inverseSpline.get(power-2).value(r);
        
        // last time constant can now be calculated
        timeConstants[power - 1] = w * tg;

        // calculate the other time constants
        for(int i = power - 2; i >= 0; i--) {
            timeConstants[i] = timeConstants[power - 1] * Math.pow(r, power - i - 1);
        }
		
        return timeConstants;
    }
    
    //Cubic NAK (10e10 times more accurate than LinearInterpolator - very good!)
    public double[] calculateTimeConstantsCubicNAK(double tu, double tg) {

        double TuTgRatio = tu / tg;

        // get power level
        int power = lookupPower(TuTgRatio);

        // prepare return array (it has as many indices as the power, starting at ^2)
        double[] timeConstants = new double[power];

        //get intersection points with Cubic-NAK algorithm 
        
        int n = Tu_Tg_ratioCubicNAK.get(0)[0].length; //should be 50
        //Tu_Tg 
        //get b, c, d
        double[][] bcdRow = new double[3][n];
        bcdRow = Tu_Tg_ratioCubicNAK.get(power-2);
        //get x, y, of Tu_Tg_ratio
        double[] rowTu_Tg_ratio = new double[n];
		for (int j = 0; j < rowTu_Tg_ratio.length; j++) {
			rowTu_Tg_ratio[j] = (double) Tu_Tg_ratio_double.get(power-2).get(j);
		}
		//spline_eval needs again the double arrays x, y, of Tu_Tg_ratio
        double r = SplineNAK.spline_eval(n, rowTu_Tg_ratio, MathStuff.linspace(0, 1, n), bcdRow[0], bcdRow[1], bcdRow[2], TuTgRatio);
        
        //Tg_T
        //get b, c, d;
        double[][] bcdRow2 = new double[3][n];
        bcdRow2 = Tg_inverseCubicNAK.get(power-2);
        //get x, y, of Tg_T
        double[] rowTg_inverse = new double[n];
		for (int j = 0; j < rowTg_inverse.length; j++) {
			rowTg_inverse[j] = (double) Tg_inverse_double.get(power-2).get(j);
		}
		//spline_eval needs again the double arrays x, y, of Tg_T
        double w = SplineNAK.spline_eval(n, MathStuff.linspace(0, 1, n), rowTg_inverse, bcdRow2[0], bcdRow2[1], bcdRow2[2], r);
        
        // last time constant can now be calculated
        timeConstants[power - 1] = w * tg;

        // calculate the other time constants
        for(int i = power - 2; i >= 0; i--) {
            timeConstants[i] = timeConstants[power - 1] * Math.pow(r, power - i - 1);
        }
		
        return timeConstants;
    }

    private PolynomialSplineFunction getTuTgRatioCurve(int power) {
        return Tu_Tg_ratio.get(power - 2);
    }

    private PolynomialSplineFunction getTgInverseCurve(int power) {
        return Tg_inverse.get(power - 2);
    }
}
