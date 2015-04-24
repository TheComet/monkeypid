package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;

public interface Zellweger {
    public void setPlant(Plant path);
    public void setNumSamplePoints(int samples);
    public void setPhiDamping(double phiDamping);
    public void setMaxIterations(int iterations);
}