package com.thecomet.monkeypid.models;

import com.thecomet.monkeypid.interfaces.MathBlock;
import com.thecomet.monkeypid.interfaces.MathBlockInterface;

public class MathChainFactory {

    public static MathBlockInterface testChain() {
        return new MathBlock("integrate1") {
            private double error;
            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                return this.error += inputValue * deltaTime;
            }
        }.setNextBlock(new MathBlock("integrate2") {
            private double value;
            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                return this.value += inputValue * deltaTime;
            }
        }.setNextBlock(new MathBlock("constant") {
            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                return inputValue + 10;
            }
        }));
    }

    public static MathBlockInterface pidOpen(double p, double i, double d) {
        MathBlockInterface chain = new MathBlock("error") {
            private double error;
            // error
            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                return (inputValue - this.error) * deltaTime;
            }
            public void set(double value) {
                this.error = value;
            }
        };
        chain.setNextBlock(new MathBlock("pid") {
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
                double finalValue = (p + this.valueI + d) / 3.0;

                return finalValue;
            }
        }.setNextBlock(new MathBlock("integrate") {
            private double value;
            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                this.value += inputValue * deltaTime;
                this.getFirstBlock().getBlock("error").set(this.value);
                return this.value;
            }
        }));

        return chain;
    }
}