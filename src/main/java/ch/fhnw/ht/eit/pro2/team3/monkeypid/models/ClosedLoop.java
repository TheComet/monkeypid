package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.MathArrays;
import org.jfree.data.xy.XYSeries;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Defines a system consisting of a plant and a controller. The closed loop will calculate its own transfer function
 * based on these two components.
 *
 * The closed loop is primarily used to calculate the step response of a closed loop system. In doing so, it will also
 * measure the overshoot, i.e. find the maximum Y value, of the calculated curve.
 *
 * The closed loop also holds information about where it belongs in the result table and it is able to construct an
 * array of strings to be placed into said table.
 *
 * This class wraps a few methods of the controller, such as getName() and getColor(), to make things a little easier.
 * @author Alex Murray
 */
public class ClosedLoop {

	private TransferFunction transferFunction;
	private Plant plant;
	private AbstractController controller;
	private XYSeries stepResponse = null;
	private double maxOvershoot;

	// stores where the calculated controller will be inserted into the table
	private int tableRowIndex = -1; // see issue #29

	// -----------------------------------------------------------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Constructs a new closed loop object. A closed loop consists of a Plant object and a controller object.
	 * @param plant The plant to use.
	 * @param controller The controller to use.
	 */
	public ClosedLoop(Plant plant, AbstractController controller) {
		setPlantAndController(plant, controller);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Public methods
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Sets the plant and controller object, and calculates the transfer function of the closed loop.
	 * @param plant The plant to set.
	 * @param controller The controller to set.
	 */
	public final void setPlantAndController(Plant plant, AbstractController controller) {
		this.plant = plant;
		this.controller = controller;
		if(controller != null)
			this.transferFunction = calculateCloseLoopTransferFunction(plant, controller);
	}

	/**
	 * Wrapper for the specific methods for calculating the step response. The current default method
	 * is Residue.
	 */
	public final void calculateStepResponse() {
		calculateStepResponseResidue();
	}

	/**
	 * Should return an array of strings to insert into the table of results. The table is designed to contain all
	 * result values of a PID controller in the following order:
	 *	 new String[] {"Controller Name", "Kr", "Tn", "Tv", "Tp", "Overshoot"};
	 * @return The length of the string array must be 6.
	 */
	public final String[] getTableRowStrings() {
		// get the strings the controller wants to insert into the table,
		// and expand the array by 1 to make space for the overshoot value
		String[] controllerRow = getController().getTableRowStrings();
		String[] tableRow = new String[controllerRow.length + 1];
		System.arraycopy(controllerRow, 0, tableRow, 0, controllerRow.length);

		// insert overshoot value
		String str = new DecimalFormat("00.0").format(maxOvershoot).replaceAll("\\G0", " ") + "%";
		str = str.replace(" .", "0."); // this stops regex from removing a 0 before the point
		tableRow[controllerRow.length] = str;

		return tableRow;
	}

	/**
	 * Returns a series of XY data points, representing the calculated step response.
	 * @return A series of XY data points.
	 */
	public final XYSeries getStepResponse() {
		return stepResponse;
	}

	/**
	 * Gets the calculated transfer function of the closed loop.
	 * @return A transfer function.
	 */
	public final TransferFunction getTransferFunction() {
		return transferFunction;
	}

	/**
	 * Gets the plant of the closed loop.
	 * @return A Plant object.
	 */
	public final Plant getPlant() {
		return plant;
	}

	/**
	 * Gets the controller of the closed loop.
	 * @return An AbstractController object.
	 */
	public final AbstractController getController() {
		return controller;
	}

	/**
	 * Gets the name of the closed loop. This is just a wrapper around the controller's getName() method, for
	 * convenience.
	 * @return Returns the name.
	 */
	public final String getName() {
		return controller.getName();
	}

	/**
	 * Gets the colour of the closed loop. This is just a wrapper around the controller's getColor() method, for
	 * convenience.
	 * @return The colour.
	 */
	public final Color getColor() {
		return controller.getColor();
	}

	/**
	 * Returns the maximum measured overshoot of the last step response calculation.
	 * @return The overshoot in percent.
	 */
	public final double getOvershoot() {
		return maxOvershoot;
	}

	/**
	 * Gets the row index in the table of where the resulting controller should be written to. See issue #29
	 * @return The row index of the table.
	 */
	public final int getTableRowIndex() {
		return tableRowIndex;
	}

	/**
	 * Stores where the calculated controller will be inserted into the table. See issue #29
	 * @param index The row index of the table.
	 */
	public final void setTableRowIndex(int index) {
		tableRowIndex = index;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Private methods
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Calculates the step response of the closed loop. This method uses ifft to get the step response.
	 * @param numSamplePoints The number of sample points to use for the inverse fourier transform. Note that if the
	 *                        specified number isn't a power of 2, it will be rounded up to the next power of 2.
	 */
	@SuppressWarnings("unused")
	private void calculateStepResponseIFFT(int numSamplePoints) {

		//calculate fs based on the sum of all timeConstants
		double magicConstant = 400.0; // this was determined by testing
		double timeAllTimeConstants = 0.0;
		for (double aTimeConstantsList : plant.getTimeConstants()) {
			timeAllTimeConstants += aTimeConstantsList;
		}
		double fs = 1.0/(timeAllTimeConstants/magicConstant);

		// round sample points to the next power of two
		int powerOfTwo = 4;
		while(powerOfTwo < numSamplePoints) {
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

		// compute maximum overshoot in percent - see issue #23
		maxOvershoot = MathStuff.max(y);
		maxOvershoot = (maxOvershoot - 1.0) * 100;

		// generate time axis
		double[] t = MathStuff.linspace(0, (y.length-1)/fs, y.length);

		// create XY data series for JFreeChart
		stepResponse = new XYSeries(controller.getName());
		for(int i = 0; i < t.length; i++) {
			stepResponse.add(t[i], y[i]);
		}
	}

	/**
	 * Calculates the step response of the closed loop. This method uses partial fraction decomposition.
	 */
	private void calculateStepResponseResidue() {
		// determine the optimal time window and compute fs
		// this is achieved by calculating the roots of the closed loop's transfer function and searching for the
		// largest imaginary part. fs = magicFactor * largestImag / (2*pi)
		double magicConstant = 50.0; // This was determined through testing
		Complex[] roots = MathStuff.roots(MathStuff.removeLeadingZeros(transferFunction.getDenominatorCoefficients()));
		double largestImag = MathStuff.max(MathStuff.imag(roots));
		double largestReal  = MathStuff.max(MathStuff.real(roots));
		double fs = magicConstant * largestImag  / (2.0 * Math.PI);

		// determine number of sample points now that fs is known
		double numSamplePoints = fs * Math.log(0.001) / largestReal;

        // If this is a zellweger, adjust the number of sample points. For some reason the time window is always a
		// little bit too short when compared to fist formulas.
        if (controller.getName().equals(CalculatorNames.ZELLWEGER_I)){
            numSamplePoints = 1.5*numSamplePoints;
        }

		//calculates the step response with residues
		Object[] residueResult = MathStuff.stepResidue(transferFunction.getNumeratorCoefficients(), transferFunction.getDenominatorCoefficients(), fs, (int) numSamplePoints);
		double[] y = (double[]) residueResult[0]; //the y-values of the step-response
		double[] t = (double[]) residueResult[1]; //the x-values/time-axis of the step-response

        // compute maximum overshoot in percent - see issue #23
		maxOvershoot = MathStuff.max(y);
		maxOvershoot = (maxOvershoot - 1.0) * 100;

		// create XY data series for JFreeChart
		stepResponse = new XYSeries(controller.getName());
		for(int i = 0; i < t.length; i++) {
			stepResponse.add(t[i], y[i]);
		}
	}

	/**
	 * Calculates the transfer function of the closed loop from a plant and a controller.
	 * @param plant The plant.
	 * @param controller The controller.
	 * @return The resulting transfer function.
	 */
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
		// E.g. numerator   = 1 2 3 4 5 6 7
		//      denominator =         1 2 3
		//      result      = 1 2 3 4 6 8 10
		int index = denominatorCoefficients.length - numeratorCoefficients.length;
		for(double numeratorCoefficient : numeratorCoefficients) {
			denominatorCoefficients[index] += numeratorCoefficient;
			index++;
		}

		return new TransferFunction(numeratorCoefficients, denominatorCoefficients);
	}
}