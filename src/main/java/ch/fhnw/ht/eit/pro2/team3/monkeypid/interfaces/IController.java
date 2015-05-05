package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.TransferFunction;

import javax.swing.table.DefaultTableModel;

public interface IController {
    public String getName();
    public void addToTable(DefaultTableModel table);
    public TransferFunction getTransferFunction();
}