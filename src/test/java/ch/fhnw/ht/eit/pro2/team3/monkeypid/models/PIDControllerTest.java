package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

public class PIDControllerTest {
	double delta = 1e-3;

	@Test
	public void testPIDControllerConstructor(){
		// [Tnk,Tvk,Tp,Krk,   Tn,Tv,Kr] = p2_zellweger_pid_tu_tg(45-180,2,6,1)
		// Tn = 3.3122, Kr = 0.9840
		
		double Tn = 3.8576;
		double Tv = 0.8656;
		double Kr = 1.7656;
		double Tp = 0.2030;

		PIDController myPIDController = new PIDController("myPIDController", Tn, Tv, Kr, Tp);

		//expected values
		//Br = Kr*[Tn*Tv+Tn*Tp Tn+Tv 1];
		//Ar = [Tn*Tp Tn 0];
		double[] NumeratorExpected = {7.2780, 8.3391, 1.7656};
		double[] DenominatorExpected = {0.7832, 3.8576, 0};
		assertArrayEquals(NumeratorExpected, myPIDController.getTransferFunction().getNumeratorCoefficients(), delta);
		assertArrayEquals(DenominatorExpected, myPIDController.getTransferFunction().getDenominatorCoefficients(), delta);

	}

	@Test
	public void testPIDControllerSetParameters(){
		// [Tnk,Tvk,Tp,Krk,   Tn,Tv,Kr] = p2_zellweger_pid_tu_tg(45-180,2,6,1)
		// Tn = 3.3122, Kr = 0.9840
		
		double Tn = 3.8576;
		double Tv = 0.8656;
		double Kr = 1.7656;
		double Tp = 0.2030;

		PIDController myPIDController = new PIDController("myPIDController", 1.5, 3.45, 5.76, 34.5); //set some random values
		myPIDController.setParameters(Tn, Tv, Kr, Tp);
		
		//expected values
		//Br = Kr*[Tn*Tv+Tn*Tp Tn+Tv 1];
		//Ar = [Tn*Tp Tn 0];
		double[] NumeratorExpected = {7.2780, 8.3391, 1.7656};
		double[] DenominatorExpected = {0.7832, 3.8576, 0};
		assertArrayEquals(NumeratorExpected, myPIDController.getTransferFunction().getNumeratorCoefficients(), delta);
		assertArrayEquals(DenominatorExpected, myPIDController.getTransferFunction().getDenominatorCoefficients(), delta);
		
	}

}
