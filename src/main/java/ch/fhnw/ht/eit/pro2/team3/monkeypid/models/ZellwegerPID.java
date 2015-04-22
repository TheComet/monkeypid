package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.Regulator;

public class ZellwegerPID extends AbstractZellweger {

    @Override
    public Regulator calculate(ControlPath path) {
        double tn = calculateTn();
        double kr = calculateKr(tn);
        // TODO Calculate Tv
        return new PIDRegulator(kr, tn, 0.0);
    }
}