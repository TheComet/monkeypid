package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

public interface MathMethod {

    public class ControllerParameters {
        public double kr, tu, tv;
    }

    public void setOverSwing(double percent);
    public void doCalculation(double tg, double tu, double ks);
    public ControllerParameters getResult();
}
