package com.thecomet.monkeypid.interfaces;

public interface MathBlockInterface {
    public MathBlock setNextBlock(MathBlock nextBlock);
    public double stepSingle(double inputValue, double deltaTime);
    public double stepAll(double inputValue, double deltaTime);
}
