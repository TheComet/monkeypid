package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel {
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
