package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;
import java.util.Observable;

public class Model extends Observable {

	public Model()
    {

    }
	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
}
