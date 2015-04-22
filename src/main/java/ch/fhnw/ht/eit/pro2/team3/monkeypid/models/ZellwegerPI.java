package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Regulator;

public class ZellwegerPI extends AbstractZellweger {

    @Override
    public Regulator calculate(ControlPath path) {
        double tn = calculateTn();
        double kr = calculateKr(tn);
        return new PIRegulator(kr, tn);
    }
}