package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.TransferFunction;

import javax.swing.table.DefaultTableModel;
import java.awt.*;

public interface IController {
    String getName();
    Color getColor();
    void setColor(Color color);
    String[] getTableRowString();
    TransferFunction getTransferFunction();
}