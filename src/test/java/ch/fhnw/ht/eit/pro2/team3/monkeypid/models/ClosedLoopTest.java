package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.TestGlobals;
import org.jfree.data.xy.XYSeries;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test the calculation of the StepResponse with the Closed-Loop
 */
public class ClosedLoopTest {
	private SaniCurves sani = new SaniCurves();

    /**
     * Test the Constructor
     */
	@Test
	public void testCalculateTransferFunctionWithConstructor() {
		Plant plant = new Plant(2, 6, 1, sani);
		AbstractController controller = new ControllerPI("test", 1, 1);

		plant.getTransferFunction().setNumeratortorCoefficients(new double[]{11, 12, 13, 14});
		plant.getTransferFunction().setDenominatorCoefficients(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		controller.getTransferFunction().setNumeratortorCoefficients(new double[]{25, 26, 27, 28, 29});
		controller.getTransferFunction().setDenominatorCoefficients(new double[]{15, 16, 17, 18, 19, 20, 21, 22, 23, 24});

		// this should calculate the closed loop transfer function
		ClosedLoop loop = new ClosedLoop(plant, controller);

		double[] expectedNumerator = new double[] {275, 586, 934, 1320, 1370, 1090, 769, 406};
		double[] expectedDenominator = new double[] {15, 46, 94, 160, 245, 350, 476, 624, 795, 990, 1020, 1299, 1587, 1884, 2190, 2130, 1709, 1215, 646};
		assertArrayEquals(loop.getTransferFunction().getNumeratorCoefficients(), expectedNumerator, TestGlobals.floatDelta);
		assertArrayEquals(loop.getTransferFunction().getDenominatorCoefficients(), expectedDenominator, TestGlobals.floatDelta);
	}

    /**
     * Test the Plant and the Controller
     */
	@Test
	public void testSetPlantAndController() {

		// these are values taken from Simulation_Richard_Gut.m
		Plant plant = new Plant(1.71, 7.6, 1, sani);
		AbstractController controller = new ControllerPI("test", 1.14, 3.58);

		// create close loop using dummy plant and controller
		ClosedLoop loop = new ClosedLoop(new Plant(1, 10, 1, sani), new ControllerPI("", 1, 1));

		// Replace dummy plant and controller with real plant and controller.
		// This should calculate the closed loop transfer function.
		loop.setPlantAndController(plant, controller);

		double delta = 0.2; // matlab script only used very approximate values
		double[] expectedNumerator = new double[] {4.08120000000000, 1.14000000000000};
		double[] expectedDenominator = new double[] {7.49023271808000, 33.6612679040000, 46.4842952000000, 23.2771600000000, 7.66120000000000, 1.14000000000000};
		assertArrayEquals(loop.getTransferFunction().getNumeratorCoefficients(), expectedNumerator, delta);
		assertArrayEquals(loop.getTransferFunction().getDenominatorCoefficients(), expectedDenominator, delta);
	}

    /**
     * Test the StepResponse
     * Time start- and end-value have to match
     * All y-values have to match
     */
	@Test
	public void testStepResponse() {
		Plant plant = new Plant(1.71, 7.6, 1, sani);
		AbstractController controller = new ControllerPI(CalculatorNames.ZELLWEGER_I, 1.14, 3.58);
		ClosedLoop loop = new ClosedLoop(plant, controller);
		loop.calculateStepResponse();
		XYSeries series = loop.getStepResponse();

		// make sure start and end times line up
		assertEquals(67.118408895258113, series.getMaxX(), TestGlobals.floatDelta);
		assertEquals(0, series.getMinX(), TestGlobals.floatDelta);

		double[] expectedYValues = new double[]{
                -1.62630325872826e-18,
                0.00100988976288797,
                0.00672739578979544,
                0.0206320906183641,
                0.0447734421431095,
                0.0798159964882687,
                0.125395172799643,
                0.180472107214649,
                0.243609295988279,
                0.313165707158697,
                0.387429453277190,
                0.464706568340425,
                0.543380081949326,
                0.621949114682351,
                0.699054318829550,
                0.773493690224301,
                0.844231317597042,
                0.910400744168619,
                0.971304084531571,
                1.02640772719759,
                1.07533526949127,
                1.11785822147984,
                1.15388494636322,
                1.18344825699276,
                1.20669205102715,
                1.22385733467912,
                1.23526795394180,
                1.24131632115318,
                1.24244939324227,
                1.23915512600201,
                1.23194959651911,
                1.22136495385623,
                1.20793832666154,
                1.19220178600652,
                1.17467343281646,
                1.15584965210550,
                1.13619855114405,
                1.11615457589580,
                1.09611427972875,
                1.07643320063388,
                1.05742378802684,
                1.03935430765986,
                1.02244864318748,
                1.00688690542101,
                0.992806755152425,
                0.980305342475877,
                0.969441764609653,
                0.960239945130878,
                0.952691840073335,
                0.946760880292997,
                0.942385564659570,
                0.939483124770504,
                0.937953188795944,
                0.937681379546520,
                0.938542789718980,
                0.940405285338692,
                0.943132596519232,
                0.946587162650030,
                0.950632706872918,
                0.955136522104886,
                0.959971457812431,
                0.965017603165783,
                0.970163668039035,
                0.975308068531684,
                0.980359728240699,
                0.985238610396766,
                0.989875999194016,
                0.994214551201211,
                0.998208139666424,
                1.00182151584813,
                1.00502981226196,
                1.00781791296920,
                1.01017971579947,
                1.01211731074934,
                1.01364009778415,
                1.01476386594938,
                1.01550985412371,
                1.01590381197399,
                1.01597507775354,
                1.01575568756947,
                1.01527952867711,
                1.01458154728277,
                1.01369701928748,
                1.01266089041738,
                1.01150719029040,
                1.01026852318739,
                1.00897563664934,
                1.00765706752570,
                1.00633886376311,
                1.00504437905673,
                1.00379413649021,
                1.00260575646596,
                1.00149394357164,
                1.00047052653519,
                0.999544545082176,
                0.998722377315491,
                0.998007901176349,
                0.997402683605653,
                0.996906191191510,
                0.996516016348650,
                0.996228113414208,
                0.996037039447792,
                0.995936194978017,
                0.995918060429537,
                0.995974424481367,
                0.996096601137297,
                0.996275632821583,
                0.996502477338301,
                0.996768177042044,
                0.997064009053774,
                0.997381615812240,
                0.997713115673531,
                0.998051193654948,
                0.998389172761779,
                0.998721066634832,
                0.999041614511846,
                0.999346299707265,
                0.999631352983002,
                0.999893742309297,
                1.00013115060155,
                1.00034194306876,
                1.00052512582472,
                1.00068029739787,
                1.00080759473279,
                1.00090763520983,
                1.00098145612242,
                1.00103045294825,
                1.00105631763406,
                1.00106097798754,
                1.00104653913765,
                1.00101522788847,
                1.00096934065553,
                1.00091119553861,
                1.00084308895468,
                1.00076725713003,
                1.00068584263331,
                1.00060086602335,
                1.00051420258698,
                1.00042756405455,
                1.00034248510387,
                1.00026031439806,
                1.00018220984855,
                1.00010913775125,
                1.00004187541178,
                0.999981016852966,
                0.999926981185521,
                0.999880023218522,
                0.999840245890326,
                0.999807614111562,
                0.999781969628895,
                0.999763046540496,
                0.999750487120700,
                0.999743857641179,
                0.999742663908257,
                0.999746366269994,
                0.999754393881466,
                0.999766158051669,
                0.999781064529994,
                0.999798524623665,
                0.999817965069531,
                0.999838836613544,
                0.999860621279060,
                0.999882838330257,
                0.999905048959522,
                0.999926859747270,
                0.999947924959473,
                0.999967947762055,
                0.999986680442356,
                1.00000392373618,
                1.00001952536466,
                1.00003337788840,
                1.00004541598748,
                1.00005561327467,
                1.00006397874680,
                1.00007055297428,
                1.00007540412374,
                1.00007862390118,
                1.00008032349625,
                1.00008062959910,
                1.00007968055330,
                1.00007762269883,
                1.00007460695052,
                1.00007078564841,
                1.00006630970767,
                1.00006132608797,
                1.00005597559406,
                1.00005039101252,
                1.00004469558299,
                1.00003900179655,
                1.00003341050879
        };
		assertArrayEquals(expectedYValues, series.toArray()[1], TestGlobals.zellwegerDelta);
	}
}