package com.thecomet.monkeypid.interfaces;

public abstract class MathBlock implements MathBlockInterface {

    protected double value;
    private MathBlock nextBlock = null;

    protected MathBlock(double initialValue) {
        this.value = initialValue;
    }

    public MathBlock setNextBlock(MathBlock nextBlock) {
        this.nextBlock = nextBlock;
        return this;
    }

    public double stepAll(double inputValue, double deltaTime) {
        double blockResult = this.stepSingle(inputValue, deltaTime);

        if(this.nextBlock != null)
            return nextBlock.stepAll(blockResult, deltaTime);
        return blockResult;
    }
}