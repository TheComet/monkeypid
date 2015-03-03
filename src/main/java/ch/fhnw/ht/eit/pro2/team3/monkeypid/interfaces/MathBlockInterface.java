package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

public interface MathBlockInterface {
    public MathBlockInterface setNextBlock(MathBlockInterface nextBlock);
    public void setPreviousBlock(MathBlockInterface block);
    public double stepSingle(double inputValue, double deltaTime);
    public double stepAll(double inputValue, double deltaTime);
    public void set(double value);
    public MathBlockInterface getBlock(String name);
    public MathBlockInterface getFirstBlock();
}