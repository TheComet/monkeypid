package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * A global list of the colours of each calculator. This makes it easier to have an overview and manage the
 * colours. The calculators will return these in their getColor() methods, and the chart will render each controller
 * according to these colours.
 * @author Alex Murray
 */
public class RenderColors {
    public static final Color OPPELT_PI               = new Color(0, 100, 0);
    public static final Color OPPELT_PID              = new Color(0, 100, 0);
    public static final Color RESWICK_FUEHRUNG_PI_0   = new Color(255, 0, 0);
    public static final Color RESWICK_FUEHRUNG_PID_0  = new Color(255, 0, 0);
    public static final Color RESWICK_FUEHRUNG_PI_20  = new Color(170, 0, 0);
    public static final Color RESWICK_FUEHRUNG_PID_20 = new Color(170, 0, 0);
    public static final Color RESWICK_STOER_PI_0      = new Color(0, 255, 255);
    public static final Color RESWICK_STOER_PID_0     = new Color(0, 255, 255);
    public static final Color RESWICK_STOER_PI_20     = new Color(128, 0, 255);
    public static final Color RESWICK_STOER_PID_20    = new Color(128, 0, 255);
    public static final Color ROSENBERG_PI            = new Color(0, 255, 0);
    public static final Color ROSENBERG_PID           = new Color(0, 255, 0);
    public static final Color ZELLWEGER_I             = new Color(0, 0, 255);
    public static final Color ZELLWEGER_PI            = new Color(0, 0, 255);
    public static final Color ZELLWEGER_PID           = new Color(0, 0, 255);
}
