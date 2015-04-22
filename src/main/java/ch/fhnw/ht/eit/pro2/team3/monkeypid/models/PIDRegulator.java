package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class PIDRegulator extends AbstractRegulator {

    public PIDRegulator(double kr, double tu, double tv) {
        this.parameters.kr = kr;
        this.parameters.tn = tu;
        this.parameters.tv = tv;
    }

}
