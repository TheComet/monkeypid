package com.thecomet.monkeypid.interfaces;

public abstract class MathBlock implements MathBlockInterface {

    private MathBlockInterface nextBlock = null;
    private MathBlockInterface previousBlock = null;
    private String name;

    protected MathBlock(String name) {
        this.name = name;
    }

    @Override
    public void setPreviousBlock(MathBlockInterface block) {
        this.previousBlock = block;
    }

    @Override
    public MathBlockInterface getBlock(String name) {
        if(this.name.equals(name))
            return this;
        if(this.nextBlock != null)
            return this.nextBlock.getBlock(name);
        throw new RuntimeException("Math block name \"" + name + "\" not found");
    }

    @Override
    public MathBlockInterface getFirstBlock() {
        if (this.previousBlock != null)
            return this.previousBlock.getFirstBlock();
        return this;
    }

    @Override
    public MathBlockInterface setNextBlock(MathBlockInterface nextBlock) {
        this.nextBlock = nextBlock;
        this.nextBlock.setPreviousBlock(this);
        return this;
    }

    @Override
    public double stepAll(double inputValue, double deltaTime) {
        double blockResult = this.stepSingle(inputValue, deltaTime);

        if(this.nextBlock != null)
            return nextBlock.stepAll(blockResult, deltaTime);
        return blockResult;
    }

    @Override
    public void set(double value) {
        // optional implementation by derived classes
    }
}