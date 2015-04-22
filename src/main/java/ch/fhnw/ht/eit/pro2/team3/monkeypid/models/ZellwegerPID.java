package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class ZellwegerPID extends AbstractZellweger {

    @Override
    public void calculate(ControlPath path) {
        setControlPath(path);
        double tn = calculateTn();
        double kr = calculateKr(tn);
        // TODO Calculate Tv
        this.regulator = new PIDRegulator(kr, tn, 0.0);
    }
}