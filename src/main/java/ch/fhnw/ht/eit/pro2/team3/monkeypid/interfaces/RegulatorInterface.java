package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

public interface RegulatorInterface {

    public class ControllerParameters {
        public double kr, tu, tv;
    }

    public void setOverSwing(double percent);
    public void calculate(double tg, double tu, double ks);
    public ControllerParameters getResult();
}
