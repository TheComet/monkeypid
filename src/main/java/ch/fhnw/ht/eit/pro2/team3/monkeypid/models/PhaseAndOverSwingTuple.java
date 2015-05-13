package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

/**
 * This class is used to hold the values in "Tabelle 1" in the document "Pflichtenheft_Technischer_Teil",
 * section 3: "Schritt PI-Regler."
 * The phase margin is mapped to a corresponding overswing value in percent.
 * @author Alex Murray
 */
@Deprecated
public class PhaseAndOverSwingTuple {
    private double phaseMargin;
    private String overswingPercent;

    /**
     * Constructs a tuple object using a phase margin and a corresponding overswing value in percent.
     * @param phaseMargin Phase margin in degrees.
     * @param overswingPercent Overswing in percent.
     */
    public PhaseAndOverSwingTuple(double phaseMargin, String overswingPercent) {
        this.phaseMargin = phaseMargin;
        this.overswingPercent = overswingPercent;
    }

    /**
     * Gets the overswing in percent.
     * @return The overswing.
     */
    public String percent() {
        return this.overswingPercent;
    }

    public double percentDouble() {
        return Double.parseDouble(overswingPercent.replaceAll("%", ""));
    }

    /**
     * Gets the angle in degrees.
     * @return The angle.
     */
    public double angle() {
        return phaseMargin;
    }
}