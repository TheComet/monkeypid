package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test the ControllerPID
 */
public class ControllerPIDTest {
	double delta = 1e-3;

    /**
     * Test the Constructor
     */
	@Test
	public void testPIDControllerConstructor(){
		// [Tnk,Tvk,Tp,Krk,   Tn,Tv,Kr] = p2_zellweger_pid_tu_tg(45-180,2,6,1)
		// Tn = 3.3122, Kr = 0.9840
		
		double Tn = 3.8576;
		double Tv = 0.8656;
		double Kr = 1.7656;
		double Tp = 0.2030;

		ControllerPID myControllerPID = new ControllerPID("myPIDController", Tn, Tv, Kr, Tp);

		//expected values
		//Br = Kr*[Tn*Tv+Tn*Tp Tn+Tp 1];
		//Ar = [Tn*Tp Tn 0];
		double[] NumeratorExpected = {7.2780, 7.1693, 1.7656};
		double[] DenominatorExpected = {0.7832, 3.8576, 0};
		assertArrayEquals(NumeratorExpected, myControllerPID.getTransferFunction().getNumeratorCoefficients(), delta);
		assertArrayEquals(DenominatorExpected, myControllerPID.getTransferFunction().getDenominatorCoefficients(), delta);
	}

    /**
     * Test setParams
     */
	@Test
	public void testPIDControllerSetParameters(){
		// [Tnk,Tvk,Tp,Krk,   Tn,Tv,Kr] = p2_zellweger_pid_tu_tg(45-180,2,6,1)
		// Tn = 3.3122, Kr = 0.9840
		
		double Tn = 3.8576;
		double Tv = 0.8656;
		double Kr = 1.7656;
		double Tp = 0.2030;

		ControllerPID myControllerPID = new ControllerPID("myPIDController", 1.5, 3.45, 5.76, 34.5); //set some random values
		myControllerPID.setParameters(Tn, Tv, Kr, Tp);
		
		//expected values
		//Br = Kr*[Tn*Tv+Tn*Tp Tn+Tp 1];
		//Ar = [Tn*Tp Tn 0];
		double[] NumeratorExpected = {7.2780, 7.1693, 1.7656};
		double[] DenominatorExpected = {0.7832, 3.8576, 0};
		assertArrayEquals(NumeratorExpected, myControllerPID.getTransferFunction().getNumeratorCoefficients(), delta);
		assertArrayEquals(DenominatorExpected, myControllerPID.getTransferFunction().getDenominatorCoefficients(), delta);
		
	}

}
