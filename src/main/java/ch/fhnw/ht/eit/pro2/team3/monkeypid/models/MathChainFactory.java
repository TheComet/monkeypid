package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.MathBlock;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.MathBlockInterface;

import java.util.ArrayList;

public class MathChainFactory {

    /**
     * Generates a math chain implementing a PID controller and error difference block.
     *
     * Visualisation of this math chain:
     *              Error block     P block        I block       D block
     *                 _____        _______        _______        _______
     * Target   ------| +   |      |       |      |   /   |      | /\    |        Return value of
     *                |     |------| ______|------|  /    |------||  |   |------  block.stepAll()
     * Current  ------| -   |      ||      |      | /     |      |/  \___|
     * block.set()     -----        -------        -------        -------
     *
     * The target value can be set with block.stepAll(targetValue, deltaTime).
     *
     * Note how there is no feedback loop (the loop is open). This is so other math
     * chains can be inserted in between, if necessary. The loop can be closed by
     * passing a "current" value into block.set() (or, more precisely,
     * block.getBlock("error").set())
     *
     * Valid math block names for getBlock() are:
     *  + "error" for the error block.
     *  + "pid" for the PID controller block.
     *
     * @param kp The factor to use for the P block.
     * @param ki The factor to use for the I block.
     * @param kd The factor to use for the D block.
     * @return Returns a chain initialised to 0, able to calculate a non-integrated value
     * representing the correction to be taken.
     */
    public static MathBlockInterface pidController(double kp, double ki, double kd) {
        return new MathBlock("error") {
            private double error;

            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                return (inputValue - this.error) * deltaTime;
            }

            public void set(double value) {
                this.error = value;
            }
        }.setNextBlock(new MathBlock("pid") {
            private double factorP = kp, factorI = ki, factorD = kd;
            private double valueI;
            private double lastError;

            @Override
            public double stepSingle(double error, double deltaTime) {
                double p = error * factorP;
                this.valueI += error * factorI;
                double d = (this.lastError - error) * factorD;
                this.lastError = error;
                return (p + this.valueI + d) / 3.0;
            }
        });
    }

    /**
     * Generates a math chain implementing a to-be-controlled model, which can be inserted into a
     * feedback loop.
     *
     *
     * @param ks Normalization factor, i.e. "how much larger was my target value than 1.0 when
     *           measuring the impulse response of my system".
     * @param tu Dead time of the system in seconds.
     * @param tg Period of the system.
     * @return Returns a math chain approximately implementing the required system using a PT1.
     */
    public static MathBlockInterface controlledSystemPT1(double ks, double tu, double tg) {
        return new MathBlock("system") {

            class PastValue {
                PastValue(double dueTime, double value) {
                    this.dueTime = dueTime;
                    this.value = value;
                }
                public double dueTime;
                public double value;
            }

            private double e = 2.718281828459045235360;
            private double pi = 3.141592653589793238462;
            private double normalisationFactor = ks;
            private double deadTime = tu;
            private double period = tg;
            private double value = 0;
            private double lastReturned = value;
            private double currentTime = 0;
            private ArrayList<PastValue> pastValues = new ArrayList<PastValue>();

            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                // PT1 simulation of model, *should* be accurate enough if tu/tg is small.
                // formula used is G(s) = k/(1 + s*T)
                this.value += (inputValue - this.value) / (
                        Math.sqrt(1.0 + Math.pow(2.0*pi*period/deltaTime, 2.0)) *
                        Math.sqrt(1.0 + Math.pow(2.0*pi*period*0.1/deltaTime, 2.0)));

                // simulate dead time by saving the current value into a list and returning
                // it when it's time
                double dueTime = this.currentTime + this.deadTime;
                this.pastValues.add(new PastValue(dueTime, this.value));

                // extract one of the past values and return it if its due time is, erm... overdue
                this.currentTime += deltaTime;
                for(int i = 0; i < this.pastValues.size(); i++) {
                    PastValue val = this.pastValues.get(i);
                    if(this.currentTime >= val.dueTime) {
                        this.lastReturned = val.value;
                        this.pastValues.remove(val);
                        break;
                    }
                }

                return this.lastReturned;
            }
        };
    }

    public static MathBlockInterface closedSystem(MathBlockInterface controllerBlock,
                                                  MathBlockInterface systemBlock) {
        return new MathBlock("closed system") {
            private MathBlockInterface controller = controllerBlock;
            private MathBlockInterface system = systemBlock;

            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                // step controller
                double controllerResult = controller.stepAll(inputValue, deltaTime);
                // step system - use result from controller as input for the system
                double feedbackResult = system.stepAll(controllerResult, deltaTime);
                // feedback result from system into the controller, thus closing the control loop
                controller.set(feedbackResult);

                return feedbackResult;
            }
        };
    }
}