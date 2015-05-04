package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;
/**
 * Klasse zur Repräsentation von komplexen Zahlen mit zugehörigen Methoden.
 * 
 * @author Richard Gut
 * 
 */
public class Complex {
	public double re;
	public double im;

	public Complex() {
		this(0.0, 0.0);
	}

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public Complex(double re) {
		this(re, 0.0);
	}

	public Complex(Complex z) {
		this(z.re, z.im);
	}

	static public Complex pow(Complex a, double x) {
		return new Complex(Math.pow(a.abs(), x) * Math.cos(x * angle(a)),
				Math.pow(a.abs(), x) * Math.sin(x * angle(a)));
	}

	public Complex add(Complex z) {
		return new Complex(this.re + z.re, this.im + z.im);
	}

	public Complex sub(Complex z) {
		return new Complex(this.re - z.re, this.im - z.im);
	}

	public Complex mul(Complex z) {
		return new Complex((re * z.re) - (im * z.im), (re * z.im) + (im * z.re));
	}

	public Complex mul(double z) {
		return new Complex((re * z), (im * z));
	}

	public Complex div(Complex b) {
		return new Complex((this.abs() / b.abs())
				* Math.cos(angle(this) - angle(b)), (this.abs() / b.abs())
				* Math.sin(angle(this) - angle(b)));
	}

	public Complex div(double b) {
		return new Complex(this.re / b, this.im / b);
	}

	public double abs() {
		return Math.sqrt(re * re + im * im);
	}

	public static double angle(Complex c) {
		return Math.atan2(c.im, c.re);
	}

	public static double[] angle(Complex[] c) {
		double[] res = new double[c.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = angle(c[i]);
		}
		return res;
	}

	public static double[] abs(Complex[] c) {
		double[] res = new double[c.length];
		for (int i = 0; i < c.length; i++) {
			res[i] = c[i].abs();
		}
		return res;
	}
}