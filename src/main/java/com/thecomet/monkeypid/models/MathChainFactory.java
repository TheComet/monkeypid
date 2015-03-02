package com.thecomet.monkeypid.models;

import com.thecomet.monkeypid.interfaces.MathBlock;
import com.thecomet.monkeypid.interfaces.MathBlockInterface;

public class MathChainFactory {
    
    public static MathBlockInterface pidController(double p, double i, double d) {
        return new MathBlock("error") {
            private double error;
            // error
            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                return (inputValue - this.error) * deltaTime;
            }
            public void set(double value) {
                this.error = value;
            }
        }.setNextBlock(new MathBlock("pid") {
            // PID
            private double kp = p, ki = i, kd = d;
            private double valueI;
            private double lastError;

            @Override
            public double stepSingle(double error, double deltaTime) {
                double p = error * kp;
                this.valueI += error * ki;
                double d = (this.lastError - error) * kd;
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
                controller.getBlock("error").set(feedbackResult);

                return feedbackResult;
            }
        };
    }
}