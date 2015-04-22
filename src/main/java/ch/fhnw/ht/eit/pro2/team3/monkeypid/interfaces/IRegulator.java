package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

public interface IRegulator {

    public class ControllerParameters {
        public double kr, tu, tv;
    }

    public void setOverswing(double percent);
    public void calculate(double tg, double tu, double ks);
    public ControllerParameters getResult();
}
