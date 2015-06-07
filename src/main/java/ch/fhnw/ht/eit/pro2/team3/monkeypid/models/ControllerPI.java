package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.text.DecimalFormat;

/**
 * Implements a PI controller. This consists of the controller parameters Kr and Tn, and a transfer function.
 * @author Alex Murray
 */
public class ControllerPI extends AbstractController {

	private double kr = 0.0;
	private double tn = 0.0;

	/**
	 * Constructs a new PI controller from the parameters Kr and Tn and calculates the transfer function.
	 * @param name A unique name for this controller. Must be unique for all controllers in a simulation.
	 * @param kr The controller parameter Kr.
	 * @param tn The controller parameter Tn.
	 */
	public ControllerPI(String name, double kr, double tn) {
		super(name);
		setParameters(kr, tn);
	}

	/**
	 * Sets the parameters Kr and Tn for the PI controller and calculates the transfer function.
	 * @param kr The controller parameter Kr.
	 * @param tn The controller parameter Tn.
	 */
	public final void setParameters(double kr, double tn) {
		this.kr = kr;
		this.tn = tn;
		calculateTransferFunction();
	}

	/**
	 * Gets the controller parameter Kr.
	 * @return Kr.
	 */
	public final double getKr() {
		return kr;
	}

	/**
	 * Gets the controller parameter Tn.
	 * @return Tn.
	 */
	public final double getTn() {
		return tn;
	}

	/**
	 * Sets the controller parameter Kr.
	 */
	@Override
	public final void setKr(double kr) {
		setParameters(kr, tn);
	}

	/**
	 * Calculates the transfer function for a PI controller.
	 */
	@Override
	protected final void calculateTransferFunction() {
		// Numerator and Denominator Poly of the pi-controller:
		// Kr (1 + 1/(s*Tn)) = Kr * (s*Tn + 1)/(s*Tn)
		//   Br = Kr*[Tn 1];
		//   Ar = [Tn 0];
		double[] numeratorCoefficients = new double[] {tn * kr, kr};
		double[] denominatorCoefficients = new double[] {tn, 0};
		setTransferFunction(
				new TransferFunction(numeratorCoefficients, denominatorCoefficients)
		);
	}

	/**
	 * Creates an array of strings to be inserted into the table in the GUI.
	 * @return Array of strings of the length 5.
	 */
	@Override
	public final String[] getTableRowStrings() {
		return new String[]{
				getName(),
				new DecimalFormat("0.000E0").format(getKr()),
				new DecimalFormat("0.000E0").format(getTn()),
						"", // Tv  (PID only)	 -- need to pad so overswing value has the correct offset in the table
						""  // Tp  (PID only)
		};
	}
}
