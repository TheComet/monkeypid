package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class AbstractController implements IController {
    private String name;
    private Color color;
    private TransferFunction transferFunction;

    public AbstractController(String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Color getColor() {
        return color;
    }

    @Override
    public final void setColor(Color color) {
        this.color = color;
    }

    @Override
    public final TransferFunction getTransferFunction() {
        return transferFunction;
    }

    @Override
    public final synchronized void removeFromTable(DefaultTableModel table) {
        for(int row = 0; row < table.getRowCount(); row++) {
            if(getName().compareTo((String) table.getValueAt(row, 0)) == 0) { // name is stored in column 0
                table.removeRow(row);
                return;
            }
        }
    }

    protected final void setTransferFunction(TransferFunction transferFunction) {
        this.transferFunction = transferFunction;
    }

    protected abstract void calculateTransferFunction();
}
