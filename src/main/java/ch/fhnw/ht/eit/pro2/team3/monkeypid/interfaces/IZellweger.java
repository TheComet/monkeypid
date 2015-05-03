package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;

public interface IZellweger {
    public void setNumSamplePoints(int samples);
    public void setAngleOfInflection(double phi);
    public void setPhaseMargin(double phi);
    public void setMaxIterations(int iterations);
}