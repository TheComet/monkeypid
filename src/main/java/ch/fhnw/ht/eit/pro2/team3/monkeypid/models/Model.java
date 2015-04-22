package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.util.Observable;

public class Model extends Observable {

    private SaniCurves sani = new SaniCurves();
    private ControlPath controlPath = null;

	public Model()
    {
    }

    public void setControlPath(ControlPath controlPath) {
        this.controlPath = controlPath;

        // recalculate time constants
        double[] timeConstants = sani.calculateTimeConstants(controlPath.getTu(), controlPath.getTg());
        controlPath.setTimeConstants(timeConstants);
    }

    public void simulateAll() {

    }

	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
}
