package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

public abstract class AbstractController implements IController{
    private String name;

    public AbstractController(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
