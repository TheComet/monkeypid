package com.thecomet.monkeypid.models;

import com.thecomet.monkeypid.interfaces.MathBlock;
import com.thecomet.monkeypid.interfaces.MathBlockInterface;

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
     * passing a "current" value into block.set().
     *
     * Valid math block names for getBlock() are:
     *  + "error" for the error block.
     *  + "pid" for the PID controller block.
     *
     * @param kp The factor to use for the P block.
     * @param ki The factor to use for the I block.
     * @param kd The factor to use for the D block.
     * @return Returns a non-integrated value representing the correction to be taken.
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

    public static MathBlockInterface controlledSystem(double ks, double tu, double tg) {
        return new MathBlock("system") {
            private double e = 2.718281828459045235360;
            private double pi = 3.141592653589793238462;
            private double deadTime = ks;
            private double periodTime = tu;
            private double timeConstant = tg;
            private double value = 0;

            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                // PT1 simulation of model, *should* be accurate enough if tu/tg is small and deltaTime is small.
                // formula used is G(s) = k/(1 + s*T)
                this.value += (inputValue - this.value) / Math.sqrt(1.0 + Math.pow(2.0*pi*deadTime/deltaTime, 2.0));
                return this.value;
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