package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;


import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.TransferFunction;
import org.apache.commons.math3.complex.Complex;

public class MathStuff {

    public static final double[] linspace(double startValue, double endValue, int nValues){
        double step = (endValue - startValue)/(nValues-1);

        double[] res = new double[nValues];

        for (int i = 0; i < nValues; i++) {
            res[i] = step * i;
        }
        return res;
    }

    public static final Complex[] freqs(TransferFunction g, double[] omega) {
        Complex[] res = new Complex[omega.length];

        for (int i = 0; i < res.length; i++) {
            Complex jw = new Complex(0.0, omega[i]);
            Complex zaehler = polyVal(g.getB(), jw);
            Complex nenner = polyVal(g.getA(), jw);
            res[i] = zaehler.divide(nenner);
        }
        return res;
    }

    public static final Complex polyVal(double[] poly, Complex x) {

        Complex res = new Complex(0, 0);

        for (int i = 0; i < poly.length; i++) {
            res=res.add(x.pow(poly.length - i - 1).multiply(poly[i]));
        }
        return res;
    }

    public static final double[] conv(double[] a, double[] b){
        double[] res = new double[a.length +b.length - 1];
        for (int n = 0; n < res.length; n++) {
            for (int i=Math.max(0, n - a.length + 1); i <= Math.min(b.length - 1, n); i++) {
                res[n] += b[i] * a[n - i];
            }
        }
        return res;
    }

    public static final Complex[] ifft(Complex[] f){
        double log2f = Math.log(f.length)/Math.log(2);
        int minLength =(int)(Math.pow(2, Math.ceil(log2f)));
        int difLength = minLength - f.length;

        //Complex[] res = transformer.transform(f, TransformType.INVERSE);
        //return res;
        return null;
    }
}
