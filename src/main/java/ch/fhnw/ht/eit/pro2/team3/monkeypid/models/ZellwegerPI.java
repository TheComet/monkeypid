package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class ZellwegerPI extends AbstractZellweger {

    @Override
    public void calculate(Plant path) {
        setPlant(path);
        double tn = calculateTn();
        double kr = calculateKr(tn);
        this.controller = new PIController(kr, tn);
    }
}