package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class ZellwegerPI extends AbstractZellweger {

    @Override
    public void calculate(ControlPath path) {
        setControlPath(path);
        double tn = calculateTn();
        double kr = calculateKr(tn);
        this.regulator = new PIRegulator(kr, tn);
    }
}