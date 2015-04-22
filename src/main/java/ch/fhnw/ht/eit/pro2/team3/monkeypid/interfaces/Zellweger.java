package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ControlPath;

public interface Zellweger {
    public void setControlPath(ControlPath path);
    public void setNumSamplePoints(int samples);
    public void setPhiDamping(double phiDamping);
    public void setMaxIterations(int iterations);
}