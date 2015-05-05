package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.MathStuff;

public class Plant {

    private double ks = 0.0;
    private double tu = 0.0;
    private double tg = 0.0;
    private SaniCurves sani;
    private double[] timeConstants;
    private TransferFunction transferFunction = null;

    public Plant(double tu, double tg, double ks, SaniCurves sani) {
        this.sani = sani;
        setParameters(tu, tg, ks);
    }

    private void calculateTransferFunction() {

        // produces the Nominator and Denominator Polynom of the control-path
        //   Bs = Ks; %Nominator
        //   As = %Denominator: poly([-1/T1 -1/T2 -1/T3 -1/T4])*(T1*T2*T3*T4)
        double[] numeratorCoefficients = new double[timeConstants.length];
        double multiplicator = 1;
        for(int k = 0; k < numeratorCoefficients.length; k++) {
            numeratorCoefficients[k] = -1.0 / timeConstants[k];
            multiplicator *= timeConstants[k];
        }

        transferFunction = new TransferFunction(
                new double[] {ks},
                MathStuff.mul(MathStuff.poly(numeratorCoefficients), multiplicator)
        );
    }

    public void setParameters(double tu, double tg, double ks) {
        this.tu = tu;
        this.tg = tg;
        this.ks = ks;
        timeConstants = sani.calculateTimeConstants(tu, tg);
        calculateTransferFunction();
    }

    public double getKs() {
        return this.ks;
    }

    public double getTu() {
        return this.tu;
    }

    public double getTg() {
        return this.tg;
    }

    public double[] getTimeConstants() {
        return this.timeConstants;
    }
}
