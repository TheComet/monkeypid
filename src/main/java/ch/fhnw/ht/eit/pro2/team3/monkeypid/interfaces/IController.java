package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import javax.swing.table.DefaultTableModel;

public interface IController {
    public String getName();
    public void addToTable(DefaultTableModel table);
}