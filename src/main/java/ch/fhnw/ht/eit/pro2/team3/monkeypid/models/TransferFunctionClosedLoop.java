package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.jfree.data.xy.XYSeries;

public class TransferFunctionClosedLoop {

	private double[] B;
	private double[] A;
	
	static final FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
	
	public TransferFunctionClosedLoop(TransferFunction hS, TransferFunction hR) {


	}
	
	
	public double[][] calculate(){
		return null;
		
	}
	
	public double[] getA(){
		return A;
	}
	public double[] getB(){
		return B;
	}
	
	public XYSeries schrittIfft(TransferFunction g, double fs, int N){
		
        return null;
	}
	

}
