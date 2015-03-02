package com.thecomet.monkeypid.models;

import com.thecomet.monkeypid.interfaces.MathBlock;
import com.thecomet.monkeypid.interfaces.MathBlockInterface;

public class MathChainFactory {
    public static MathBlockInterface testChain() {
        MathBlockInterface chain = new MathBlock(0) {
            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                return this.value += inputValue * deltaTime;
            }
        }.setNextBlock(new MathBlock(0) {
            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                return this.value += inputValue * deltaTime;
            }
        }.setNextBlock(new MathBlock(0) {
            @Override
            public double stepSingle(double inputValue, double deltaTime) {
                return inputValue + 10;
            }
        }));

        return chain;
    }
}