package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.ControllerCalculatorListener;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Defines the common interface for a class capable of calculating controllers. The class takes a plant as an input,
 * and spits out new controller objects as its output. There are many different ways to calculate a controller, see
 * the derived classes for more detailed information on various methods.
 *
 * Each calculator defines a unique name, colour, and holds various other values required for various methods being
 * implemented.
 *
 * The class was design with threading in mind, which is why it implements Runnable.
 * @author Alex Murray
 */
public abstract class AbstractControllerCalculator implements Runnable {

    private ArrayList<ControllerCalculatorListener> listeners = new ArrayList<>();
    protected Plant plant = null;
    protected double parasiticTimeConstantFactor = 0.1;

    // the resulting controller from a calculation
    private AbstractController controller = null;

    // stores where the calculated controller will be inserted into the table
    private int tableRowIndex = -1; // see issue #29

    // -----------------------------------------------------------------------------------------------------------------
    // Interface for derived classes
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Should return a unique name of the calculation method being implemented. The name is used as a unique identifier,
     * and must be unique among all calculations in a simulation.
     * @return A string of the method name.
     */
    public abstract String getName();

    /**
     * Gets the colour of this controller. The colour is used for the colour of the curve in the chart, and for
     * colouring the checkboxes and rows in the table.
     * @return A Color object
     */
    public abstract Color getColor();

    /**
     * This is invoked internally by the run() method when it is time to calculate a controller. Since there are many
     * ways to calculate a controller, it must be overridden by a derived class.
     * @return Should return the result of the calculation in the form of a new controller object. Depending on the
     * implementation, you should use either ControllerI, ControllerPI, or ControllerPID.
     */
    protected abstract AbstractController calculate();

    // -----------------------------------------------------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A plant is required in order to be able to calculate a controller.
     * @param plant The plant to calculate a controller for.
     */
    public AbstractControllerCalculator(Plant plant) {
        setPlant(plant);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Public methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Sets the plant, for which the calculator will calculate a controller. This method gets overridden by the
     * Zellweger calculator, as it needs to perform some additional preparations whenever the plant changes.
     * @param plant The plant object to set.
     */
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    /**
     * Sets the parasitic time constant factor. This is used to calculate Tp. Depending on the implementation, this
     * factor is either multiplied with Tvk or Tv to get Tp.
     * @param parasiticTimeConstantFactor The factor to use. Should be absolute, **not** in percent.
     */
    public final void setParasiticTimeConstantFactor(double parasiticTimeConstantFactor) {
        this.parasiticTimeConstantFactor = parasiticTimeConstantFactor;
    }

    /**
     * This can get used to get the resulting controller after calculation completes.
     * @return The resulting controller object. Will be null if the calculation wasn't performed.
     */
    public final AbstractController getController() {
        return controller;
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

    /**
     * Register as a listener to this object in order to receive notifications of events.
     * @param listener The object to register.
     */
    public final void registerListener(ControllerCalculatorListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregister as a listener from this object.
     * @param listener The object to register.
     */
    public final void unregisterListener(ControllerCalculatorListener listener) {
        listeners.remove(listener);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Rounds the value Tp to two decimal places - Requested by Peter Niklaus, See issue #10.
     * @param value The value to round.
     * @return The newly rounded value.
     */
    protected final double beautifyTpSoNiklausIsHappy(double value) {
        DecimalFormat f = new DecimalFormat("###.##");
        return Double.parseDouble(f.format(value));
    }

    /**
     * This gets called once a calculation has completed, so listeners can pick up the resulting controller.
     */
    private synchronized void notifyCalculationComplete() {
        for (ControllerCalculatorListener listener : listeners) {
            listener.onControllerCalculationComplete(this);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Derived methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Entry point for threaded execution.
     */
    @Override
    public final void run() {
        controller = calculate();
        controller.setColor(getColor());
        notifyCalculationComplete();
    }
}