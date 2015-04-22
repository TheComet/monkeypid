package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

public interface IRegulator {

    public class ControllerParameters {
        public double kr = 0.0;
        public double tn = 0.0;
        public double tv = 0.0;
    }

    public ControllerParameters getParameters();
}
