package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import javax.swing.table.DefaultTableModel;

/**
 * This class overwrites a specific method to make cells non-editable. Copying cells from the
 * table is still possible
 * @author Josua
 */
public class NonEditableTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	/**
	 * All cells are not editable.
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}