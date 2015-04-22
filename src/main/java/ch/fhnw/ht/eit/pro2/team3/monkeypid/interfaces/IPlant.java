package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

public interface IPlant {

    public class ControllerParameters {
        public double ks = 0.0;
        public double tu = 0.0;
        public double tg = 0.0;
    }

    public ControllerParameters getParameters();
    public IRegulator calculateRegulator();
}
