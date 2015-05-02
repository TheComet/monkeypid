package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class ZellwegerPID extends AbstractZellweger {

    @Override
    public void calculate(Plant path) {
        setPlant(path);
        double tn = calculateTn();
        double kr = calculateKr(tn);
        // TODO Calculate Tv
        this.IController = new PIDController(kr, tn, 0.0);
    }
}