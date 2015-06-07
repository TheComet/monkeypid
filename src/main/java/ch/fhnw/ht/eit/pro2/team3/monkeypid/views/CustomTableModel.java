package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import javax.swing.table.DefaultTableModel;
/**
 * 
 * @author Josua
 *
 */
public class CustomTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
