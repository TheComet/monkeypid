package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

public abstract class AbstractMathMethod implements MathMethod {

    public ControllerParameters result = new ControllerParameters();

    @Override
    public ControllerParameters getResult() {
        return this.result;
    }
}
