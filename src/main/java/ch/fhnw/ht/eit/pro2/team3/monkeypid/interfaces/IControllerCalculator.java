package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;

public interface IControllerCalculator extends Runnable {
    public void setPlant(Plant plant);
    public IController getController();
}