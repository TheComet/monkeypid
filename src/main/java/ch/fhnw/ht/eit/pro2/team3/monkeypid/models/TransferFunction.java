package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class TransferFunction {
	private double[] A;
	private double[] B;
	
	
	public TransferFunction(double[] A, double[] B){
		this.A = A;
		this.B = B;
	}
	public double[] getA(){
		return A;
	}
	public double[] getB(){
		return B;
	}

}
