package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IClosedLoopListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.MathArrays;
import org.jfree.data.xy.XYSeries;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Defines a system consisting of a plant and a controller. The closed loop will calculate its own transfer function
 * based on these two components, and is capable of calculating a step response.
 * @author Alex Murray
 */
public class ClosedLoop {

	private TransferFunction transferFunction;
	private Plant plant;
	private AbstractController controller;
	private XYSeries stepResponse = null;
	private ArrayList<IClosedLoopListener> listeners = new ArrayList<>();
	private double maxOverSwing;

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
		this.transferFunction = calculateCloseLoopTransferFunction(plant, controller);
	}

	/**
	 * Wrapper around the specific methods for calculating the step response. The current default method
	 * is Residue.
	 * @param numSamplePoints The number of sample points to use. Read the docstring of calculateStepResponseResidue
	 *						for more information on what this does.
	 */
	public final void calculateStepResponse(int numSamplePoints) {
		calculateStepResponseResidue(numSamplePoints);
	}

	/**
	 * Should return an array of strings to insert into the table of results. The table is designed to contain all
	 * result values of a PID controller in the following order:
	 *	 new String[] {"Controller Name", "Kr", "Tn", "Tv", "Tp", "Overswing"};
	 * @return The length of the string array must be 6.
	 */
	public final String[] getTableRowStrings() {
		// get the strings the controller wants to insert into the table,
		// and expand the array by 1 to make space for the overswing value
		String[] controllerRow = getController().getTableRowStrings();
		String[] tableRow = new String[controllerRow.length + 1];
		System.arraycopy(controllerRow, 0, tableRow, 0, controllerRow.length);

		// insert overswing value
		String str = new DecimalFormat("00.0").format(maxOverSwing).replaceAll("\\G0", " ") + "%";
		str = str.replace(" .", "0."); // this stops regex from removing a 0 before the point
		tableRow[controllerRow.length] = str;

		return tableRow;
	}

	/**
	 * Register as a listener to this class in order to receive notifications.
	 * @param listener The object to register.
	 */
	public final void registerListener(IClosedLoopListener listener) {
		listeners.add(listener);
	}

	/**
	 * Unregister as a listener from this class.
	 * @param listener The object to unregister.
	 */
	public final void unregisterListener(IClosedLoopListener listener) {
		listeners.remove(listener);
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
	 * Returns the maximum measured overswing of the last step response calculation.
	 * @return The overswing in percent.
	 */
	public final double getOverswing() {
		return maxOverSwing;
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
	 *					 specified number isn't a power of 2, it will be rounded up to the next power of 2.
	 */
	private void calculateStepResponseIFFT(int numSamplePoints) {

		// determine the optimal time window and compute fs
		// this is achieved by calculating the roots of the closed loop's transfer function and searching for the
		// largest imaginary part. fs = magicFactor * largestImag / (2*pi)
		/*Complex[] roots = MathStuff.roots(transferFunction.getDenominatorCoefficients());
		double largestImag = MathStuff.max(MathStuff.imag(roots));*/

		/*
		double magicConstant = 1000.0;
		double fs = largestImag * magicConstant / (2.0 * Math.PI);
		System.out.println("largest Imaga: "+largestImag + " fs: "+fs);
		*/

		//calculate fs based on the sum of all timeConstants
		List timeConstantsList = Arrays.asList(ArrayUtils.toObject(plant.getTimeConstants()));
		double timeAllTimeConstants = 0.0;
		for (Object aTimeConstantsList : timeConstantsList) {
			timeAllTimeConstants += (double) aTimeConstantsList;
		}
		double fs = 1.0/(timeAllTimeConstants/400.0);


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

		// compute maximum overswing in percent - see issue #23
		maxOverSwing = MathStuff.max(y);
		maxOverSwing = (maxOverSwing - 1.0) * 100;

		// generate time axis
		double[] t = MathStuff.linspace(0, (y.length-1)/fs, y.length);

		// create XY data series for JFreeChart
		stepResponse = new XYSeries(controller.getName());
		for(int i = 0; i < t.length; i++) {
			stepResponse.add(t[i], y[i]);
		}

		// done, notify
		notifyCalculationComplete();
	}

	/**
	 * Calculates the step response of the closed loop. This method uses Residue to get the step response.
	 * @param numSamplePoints The number of sample points to use for the linspace of the time-axis.
	 * 							The max time-value is internally calculated by fs (the fs of the transfer-function,
	 * 							also internally calculated) and the numSamplePoints.
	 */
	private void calculateStepResponseResidue(int numSamplePoints) {


		//calculate fs based on the sum of all timeConstants
		List timeConstantsList = Arrays.asList(ArrayUtils.toObject(plant.getTimeConstants()));
		double timeAllTimeConstants = 0.0;
		for (Object aTimeConstantsList : timeConstantsList) {
			timeAllTimeConstants += (double) aTimeConstantsList;
		}
		//double fs = 45;
		//double fs = 1.0/(timeAllTimeConstants/400.0);
		//double fs = timeAllTimeConstants/0.05;


		//TODO:
		/*
		 * Limit the range of the num-of sampling points from 1024 to 2048
		 * -> change eventually the fs and N calculation to optimize the speed of the residue calculation
		 * the calculation of fs and N was developed by Richard Gut for the ifft method, so probably for the
		 * residue method, fs an N should be calculated different
		 *
		 * React to the parameter numOfSampling points. At the moment it is overwritten by this method -> the model
		 * can't switch between normal accurate calculation and fast calculation (because the parameter is unused
		 * at the moment)
		 */

		//Following Plant-Parameters work (PI-Controller, 10% overshoot)
		/*
		Tu = 0.01 Tg = 0.02
		0.01 0.017
		0.01 0.018

		 */
		//Following Plant-Parameters don't work
		/*
		Tu = 0.01 Tg = 0.017
		0.01 0.016
		 */

		//display debug info only, if zellweger:
		boolean d = false;
		if(controller.getName().equals("Zellweger") && numSamplePoints == 8*1024){
			d = true;
		}

		// determine the optimal time window and compute fs
		// this is achieved by calculating the roots of the closed loop's transfer function and searching for the
		// largest imaginary part. fs = magicFactor * largestImag / (2*pi)


		Complex[] roots = MathStuff.roots(MathStuff.removeLeadingZeros(transferFunction.getDenominatorCoefficients()));
		double largestImag = MathStuff.maxFromNegativeInfinity(MathStuff.imag(roots)); //MathStuff.max(MathStuff.imag(roots));
		double largestReal  = MathStuff.maxFromNegativeInfinity(MathStuff.real(roots));

		double fs = 50.0*largestImag/(2.0*Math.PI);

		double numberOfPoints = fs*Math.log(0.0001)/largestReal;


		//numSamplePoints = (int) Math.ceil(Math.log(numberOfPoints)/Math.log(2.0));
		//numSamplePoints = (int) Math.pow(2, numSamplePoints);
		//System.out.println("numOfPoints: "+numberOfPoints+ " numSamplePoints: "+numSamplePoints);
		numberOfPoints = Math.round(numberOfPoints);

		if(d) {
			//System.out.println("largestReal: "+largestReal+ " largestImag: "+largestImag);
			//System.out.println("numOfPoints: " + numberOfPoints);
			//System.out.println("fs: " + fs);
			//System.out.println("time: " + (numberOfPoints - 1) / fs);
		}


		/*
		if(numSamplePoints > 4096){
			numSamplePoints = 4096;
		}
		*/

		//fs = 1.0/(timeAllTimeConstants/400.0);
		//numSamplePoints = 4096;

		//calculates the step-response with residues
		Object[] residueResult = MathStuff.stepResidue(transferFunction.getNumeratorCoefficients(), transferFunction.getDenominatorCoefficients(), fs, (int) numberOfPoints);
		double[] y = (double[]) residueResult[0]; //the y-values of the step-response
		double[] t = (double[]) residueResult[1]; //the x-values/time-axis of the step-response

		// compute maximum overswing in percent - see issue #23
		maxOverSwing = MathStuff.max(y);
		maxOverSwing = (maxOverSwing - 1.0) * 100;

		// create XY data series for JFreeChart
		stepResponse = new XYSeries(controller.getName());
		for(int i = 0; i < t.length; i++) {
			stepResponse.add(t[i], y[i]);
		}

		// done, notify
		notifyCalculationComplete();
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
		// E.g. numerator = 1 2 3 4 5 6 7
		//	denominator =		 1 2 3
		//		 result = 1 2 3 4 6 8 10
		int index = denominatorCoefficients.length - numeratorCoefficients.length;
		for(double numeratorCoefficient : numeratorCoefficients) {
			denominatorCoefficients[index] += numeratorCoefficient;
			index++;
		}

		return new TransferFunction(numeratorCoefficients, denominatorCoefficients);
	}

	/**
	 * Called when the step response calculation completes, so listeners can pick up the results.
	 */
	private synchronized void notifyCalculationComplete() {
		for (IClosedLoopListener listener : listeners) {
			listener.onStepResponseCalculationComplete(this);
		}
	}
}