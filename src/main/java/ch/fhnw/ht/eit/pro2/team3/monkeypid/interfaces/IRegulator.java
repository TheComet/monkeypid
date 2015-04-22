package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

public interface IRegulator {

    public class ControllerParameters {
        public double kr;
        public double tu;
        public double tv;
    }

    public ControllerParameters getParameters();
}
