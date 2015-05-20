package ch.fhnw.ht.eit.pro2.team3.monkeypid;

public class TestGlobals {
    // default accuracy for floating point numbers
    public static double floatDelta = 1e-7;
    // seems to be the closest accuracy we can get for sani approximation
    public static double saniDelta = 2.1e-4;
    public static double saniDeltaSpline = 0.6e-4;
    public static double saniDeltaCubicNAK = 1e-13;
    // seems to be the closest accuracy we can get for zellweger curves
    public static double zellwegerDelta = 0.001;
    // seems to be the closest accuracy we can get for plant transfer function
    public static double plantTransferDelta = 0.008;
    // seems to be the closest accuracy we can get for the fistFormulas
    public static double fistFormulas = 1e-3;
}
