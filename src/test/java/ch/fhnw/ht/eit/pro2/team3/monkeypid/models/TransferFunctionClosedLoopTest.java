package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class TransferFunctionClosedLoopTest {	
	double[] Bs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	double[] Br = {11, 12, 13, 14};
	double[] As = {15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
	double[] Ar = {25, 26, 27, 28, 29};
	
	@Test
	public void testTransferFunctionClosedLoop() {
		TransferFunctionClosedLoop tFCL= new TransferFunctionClosedLoop(
				new TransferFunction(As, Bs),
				new TransferFunction(Ar, Br)
		);
		//assertArrayEquals(, actuals);
	}

}
