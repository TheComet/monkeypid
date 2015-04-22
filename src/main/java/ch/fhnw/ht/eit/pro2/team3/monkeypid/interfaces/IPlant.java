package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

public interface IPlant {

    public class ControllerParameters {
        public double ks, tu, tg;
    }

    public ControllerParameters getParameters();
    public IRegulator calculateRegulator();
}
