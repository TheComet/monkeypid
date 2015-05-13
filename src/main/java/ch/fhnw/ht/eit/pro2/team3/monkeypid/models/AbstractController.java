package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Represents the controller component in a closed loop system. The controller can have multiple types, e.g. I, PI,
 * or PID. This is a base class defining the common interface.
 *
 * Each controller defines a unique name, a colour, and most importantly, a transfer function. The way the transfer
 * function is calculated depends on the controller type and is taken care of in the derived classes.
 *
 * Once a controller is calculated, it can be connected to a Plant object by using a ClosedLoop object.
 * @author Alex Murray
 */
public abstract class AbstractController {
    private String name;
    private Color color;
    private TransferFunction transferFunction;
    private double minKr = 0, maxKr = 0;

    // -----------------------------------------------------------------------------------------------------------------
    // Interface for derived classes
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * This should calculate the transfer function of the controller and then pass it to setTransferFunction(). There
     * are multiple ways to do this, which is why it must be implemented by the derived classes.
     */
    protected abstract void calculateTransferFunction();

    /**
     * Should return an array of strings to insert into the table of results. The table is designed to contain all
     * result values of a PID controller in the following order:
     * new String[] {"Controller Name", "Kr", "Tn", "Tv", "Tp"};
     * If your controller is missing some of these parameters, then you should specify empty strings in their place.
     *
     * @return The length of the string array must be 5.
     */
    public abstract String[] getTableRowStrings();

    public void setKr(double kr) {}

    // -----------------------------------------------------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Constructor. The name of the controller is required and should be unique among all calculations within a
     * simulation. In this implementation, we set it to the name of the method used to calculate the controller.
     *
     * @param name A string identifying this controller. This is used as a key for many things, so it must be unique.
     */
    public AbstractController(String name) {
        this.name = name;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Public methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Gets the name of this controller.
     *
     * @return A string containing the name of the controller.
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets the colour of this controller. The colour is used for the colour of the curve in the chart, and for
     * colouring the checkboxes and rows in the table.
     *
     * @return A Color object.
     */
    public final Color getColor() {
        return color;
    }

    /**
     * Sets the colour of this controller. The colour is used for the colour of the curve in the chart, and for
     * colouring the checkboxes and rows in the table.
     *
     * @param color A new colour object.
     */
    public final void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the transfer function of this controller. There are multiple ways to calculate the transfer function, as
     * it depends on the controller type (I, PI, PID). See the derived classes for more info.
     *
     * @return The transfer function of this controller.
     */
    public final TransferFunction getTransferFunction() {
        return transferFunction;
    }

    public final double getMinKr() {
        return minKr;
    }

    public final double getMaxKr() {
        return maxKr;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Sets the transfer function of this controller. Only derived classes should be able to set the transfer function,
     * because it doesn't really make sense for anything else outside to know how to calculate it.
     *
     * @param transferFunction
     */
    protected final void setTransferFunction(TransferFunction transferFunction) {
        this.transferFunction = transferFunction;
    }

    protected final void setMinKr(double kr) {
        minKr = kr;
    }

    protected final void setMaxKr(double kr) {
        maxKr=kr;
    }
}