package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

public class ControllerI extends AbstractController {

    public ControllerI(String name) {
        super(name);
    }

    @Override
    protected void calculateTransferFunction() {

    }

    @Override
    public String[] getTableRowStrings() {
        return new String[0];
    }
}
