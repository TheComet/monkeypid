package ch.fhnw.ht.eit.pro2.team3.monkeypid;

public class TestGlobals {
    // default accuracy for floating point numbers
    public static double floatDelta = 1e-7;
    // seems to be the closest accuracy we can get for sani approximation
    public static double saniDelta = 0.00023;
    // seems to be the closest accuracy we can get for zellweger curves
    public static double zellwegerDelta = 0.001;
    // seems to be the closest accuracy we can get for plant transfer function
    public static double plantTransferDelta = 0.008;
}
