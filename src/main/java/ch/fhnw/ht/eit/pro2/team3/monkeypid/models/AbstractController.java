package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

public abstract class AbstractController {
    private String name;
    private Color color;
    private TransferFunction transferFunction;

    public AbstractController(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final Color getColor() {
        return color;
    }

    public final void setColor(Color color) {
        this.color = color;
    }

    public final TransferFunction getTransferFunction() {
        return transferFunction;
    }

    protected final void setTransferFunction(TransferFunction transferFunction) {
        this.transferFunction = transferFunction;
    }

    protected abstract void calculateTransferFunction();

    public abstract String[] getTableRowStrings();
}