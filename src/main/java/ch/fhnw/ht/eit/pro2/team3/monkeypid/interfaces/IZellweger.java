package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;

public interface IZellweger {
    public void setPlant(Plant path);
    public void setNumSamplePoints(int samples);
    public void setPhi(double phi);
    public void setPhiDamping(double phiDamping);
    public void setMaxIterations(int iterations);
    public IController getController();
}