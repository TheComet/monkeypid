package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.OverswingValueTuple;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Creates a panel which includes the input fields for Tu, Tg, Ks, Tp, a
 * comboBox to select the regulator, a comboBox to select the overshoot, the
 * buttons and the table with the results of the simulation.
 * 
 * @author Josua
 *
 */
public class OutputPanel extends JPanel implements ActionListener, IModelListener {

	private Controller controller;

	// create test table
	private OverswingValueTuple[] overswingTable = new OverswingValueTuple[4];

	// simulation title
	private JLabel lbSimulationTitle = new JLabel("Simulationen");

	// manual adjustment
	private JSlider slKp = new JSlider(JSlider.HORIZONTAL, 0, 5, 3);
	private JSlider slTn = new JSlider(JSlider.HORIZONTAL, 0, 5, 3);
	private JSlider slTv = new JSlider(JSlider.HORIZONTAL, 0, 5, 3);
	private JFormattedDoubleTextField tfAdaptKp = new JFormattedDoubleTextField(
			1);
	private JFormattedDoubleTextField tfAdaptTn = new JFormattedDoubleTextField(
			1);
	private JFormattedDoubleTextField tfAdaptTv = new JFormattedDoubleTextField(
			1);

	// buttons delete and adopt
	private JButton btDelete = new JButton("Loeschen");
	private JButton btAdopt = new JButton("Uebernehmen");

	// table and table model
	CustomTableModel tableModel = new CustomTableModel();
	JTable table = new JTable(tableModel);

	// spinnerIcon icon
	//ImageIcon spinnerIcon = Assets.loadImageSpinner();
	//JLabel spinnerLabel = new JLabel();

	// adjustment slider
	private JLabel lbTrimmSlider = new JLabel("Trimm für Zellwegermethode");
	private JSlider slTrimmSlider = new JSlider(JSlider.HORIZONTAL, 45, 150,
			100);

	/**
	 * The constuctor of Leftpanel set the layout to GridBagLayout and adds all
	 * the components to the panel. Furthermore it creates the table for the
	 * results and the buttons listen to the ActionListener
	 * 
	 * @param controller
	 */
	public OutputPanel(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;

		// init overswnig table - see Pflichtenheft Technischer Teil Kapitel 2.3
		overswingTable[0] = new OverswingValueTuple(76.3, "0%");
		overswingTable[1] = new OverswingValueTuple(65.5, "4.6%");
		overswingTable[2] = new OverswingValueTuple(51.5, "16.3%");
		overswingTable[3] = new OverswingValueTuple(45, "23.3%");

		// add title simulate to GridBagLayout
		add(lbSimulationTitle, new GridBagConstraints(0, 10, 5, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));

		// TODO remove
		// tbTest.setEnabled(false);
		// JTableHeader header = tbTest.getTableHeader();
		// tbTest.setValueAt(aValue, row, column);

		/*
		 * DefaultTableModel model = new DefaultTableModel(); JTable table = new
		 * JTable(model);
		 * 
		 * model.addColumn("Col3");
		 * 
		 * String testheader[] = new String[] { "Prority", "Task Title",
		 * "Start", "Pause", "Stop", "Statulses" };
		 * 
		 * model.setColumnIdentifiers(testheader); table.setModel(model);
		 * 
		 * model.addRow(new Object[] { "v1", "v2" });
		 */

		// add columns to the table
		tableModel.addColumn("Name");
		tableModel.addColumn("Kr");
		tableModel.addColumn("Tn");
		tableModel.addColumn("Tv");
		tableModel.addColumn("Tp");
		tableModel.addColumn("<html><left>Über-<br>schwingen");
		
		// set size of first column
		table.getColumnModel().getColumn(0).setMinWidth(90);
		table.getColumnModel().getColumn(0).setMaxWidth(90);
		table.getColumnModel().getColumn(0).setPreferredWidth(90);

		table.getColumnModel().getColumn(5).setMinWidth(100);
		table.getColumnModel().getColumn(5).setMaxWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		
		//table.getColumnModel().getColumn(5).setCellRenderer()) {
			

		
		// set size of the rest columns
		for (int i = 1; i < table.getColumnCount()-1; i++) {
			TableColumn col;
			col = table.getColumnModel().getColumn(i);
			col.setMinWidth(80);
			col.setMaxWidth(80);
			col.setPreferredWidth(80);
		}

		// set preferred size of table
		table.setPreferredSize(new Dimension(500, 200));
		table.setMinimumSize(new Dimension(500, 200));
		table.getTableHeader().setPreferredSize(new Dimension(500, 50));
		
		// disable autoResize of table
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// disable mouse resize icon
		table.getTableHeader().setResizingAllowed(false);

		// disable user column dragging
		table.getTableHeader().setReorderingAllowed(false);

		// add header of table to GridBagLaout
		add(table.getTableHeader(), new GridBagConstraints(0, 11, 7, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		// add table to GridBagLayout
		add(table, new GridBagConstraints(0, 12, 7, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(0, 10, 10, 10), 0, 0));

		// init spinnerIcon icon label
		//spinnerLabel.setIcon(spinnerIcon);
		//spinnerIcon.setImageObserver(spinnerLabel);

		// TODO entscheiden ob fuer profimodus integriert wird
		/*
		 * //add sliders for manual adjustment add(slKp, new
		 * GridBagConstraints(0, 12, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		 * GridBagConstraints.NONE, new Insets( 10, 10, 10, 10), 0, 0));
		 * add(slTn, new GridBagConstraints(0, 13, 4, 1, 0.0, 0.0,
		 * GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 10,
		 * 10, 10, 10), 0, 0)); add(slTv, new GridBagConstraints(0, 14, 4, 1,
		 * 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new
		 * Insets( 10, 10, 10, 10), 0, 0)); //add textfields for manual
		 * adjustment add(tfAdaptKp, new GridBagConstraints(5, 12, 1, 1, 0.0,
		 * 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new
		 * Insets( 10, 10, 10, 10), 0, 0)); add(tfAdaptTn, new
		 * GridBagConstraints(5, 13, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		 * GridBagConstraints.HORIZONTAL, new Insets( 10, 10, 10, 10), 0, 0));
		 * add(tfAdaptTv, new GridBagConstraints(5, 14, 1, 1, 0.0, 0.0,
		 * GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(
		 * 10, 10, 10, 10), 0, 0));
		 */

		// add button delete to GridBagLayout
		/*
		 * add(btDelete, new GridBagConstraints(4, 16, 2, 1, 0.0, 0.0,
		 * GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE, new
		 * Insets(10, 10, 10, 10), 0, 0));
		 * 
		 * // add button adopt to GridBagLayout add(btAdopt, new
		 * GridBagConstraints(0, 16, 4, 1, 0.0, 0.0,
		 * GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new
		 * Insets(10, 10, 10, 10), 0, 0));
		 */

		// add title for trimm slider to GridbagLayout
		add(lbTrimmSlider, new GridBagConstraints(0, 17, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));

		// add the trimm slider to GridbagLayout
		add(slTrimmSlider, new GridBagConstraints(0, 18, 7, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 10, 10, 10), 0, 0));

		// add vertical dummy to GridbagLayout
		add(new JLabel(), new GridBagConstraints(0, 19, 1, 1, 0.0, 1.0,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

		// add ActionListener to buttons
		btAdopt.addActionListener(this);
		btDelete.addActionListener(this);

		// pack frame
		// JFrame myParent = (JFrame) view.getTopLevelAncestor(); // get frame

		// myParent.getRootPane().setDefaultButton(btSimulate);
	}

	/**
	 * Sets the elements to visible or invisible. It depends on which version
	 * (mini or normal) is selected in the menu.
	 * 
	 * @param MiniVersionOn
	 */
	public void setMiniVersion(boolean miniVersionSelected) {
		// set all changing components to in- or visible
		lbSimulationTitle.setVisible(miniVersionSelected);
		slKp.setVisible(miniVersionSelected);
		slTn.setVisible(miniVersionSelected);
		slTv.setVisible(miniVersionSelected);
		tfAdaptKp.setVisible(miniVersionSelected);
		tfAdaptTn.setVisible(miniVersionSelected);
		tfAdaptTv.setVisible(miniVersionSelected);
		table.setVisible(miniVersionSelected);
		table.getTableHeader().setVisible(miniVersionSelected);

		btDelete.setVisible(miniVersionSelected);
		btAdopt.setVisible(miniVersionSelected);
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {

		// if button delete is pressed
		if (e.getSource() == btDelete) {
			// call method of controller
			// TODO controller.btDeleteAction();
		}
		// if button adopt is pressed
		if (e.getSource() == btAdopt) {
			int slKpValue = slKp.getValue();
			int slTnValue = slTn.getValue();
			int slTvValue = slTv.getValue();
			// give over values to controller
			// TODO controller.btAdoptAction(slKpValue, slTnValue, slTvValue);
		}
	}

	@Override
	public void onAddCalculation(ClosedLoop closedLoop) {

        // do we have a row allocated for this closed loop?
        if(closedLoop.getTableRowIndex() > -1 && closedLoop.getTableRowIndex() < tableModel.getRowCount()) {
            String[] tableRowStrings = closedLoop.getTableRowStrings();
            for(int i = 0; i < tableRowStrings.length; i++) {
                tableModel.setValueAt(tableRowStrings[i], closedLoop.getTableRowIndex(), i);
            }
        } else {

            // we don't have space allocated, so just append it to the end
            tableModel.addRow(closedLoop.getTableRowStrings());
        }
	}

	@Override
	public void onRemoveCalculation(ClosedLoop closedLoop) {

        // remove from table
        for(int row = 0; row < tableModel.getRowCount(); row++) {
            // name is stored in column 0
            if (closedLoop.getName().equals(tableModel.getValueAt(row, 0))) {
                tableModel.removeRow(row);
                return;
            }
        }
	}

    @Override
    public void onSimulationBegin(int numberOfStepResponses) {
        // clear the table
        while(tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        // allocate all rows with empty strings
        for(int i = 0; i < numberOfStepResponses; i++) {
            tableModel.addRow(new String[] {"calculating...", "", "", "", "", ""});
        }
    }

	@Override
	public void onSimulationComplete() {}

	@Override
	public void onHideCalculation(ClosedLoop closedLoop) {}

	@Override
	public void onShowCalculation(ClosedLoop closedLoop) {}
}