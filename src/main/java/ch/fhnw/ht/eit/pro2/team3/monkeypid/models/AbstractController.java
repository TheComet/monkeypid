package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

public abstract class AbstractController implements IController{
    private String name;
    private TransferFunction transferFunction;

    public AbstractController(String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final TransferFunction getTransferFunction() {
        return transferFunction;
    }

    protected final void setTransferFunction(TransferFunction transferFunction) {
        this.transferFunction = transferFunction;
    }

    protected abstract void calculateTransferFunction();
}
