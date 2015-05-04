package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.OverswingValueTuple;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Josua
 *
 */
public class LeftPanel extends JPanel
        implements ActionListener, IControllerCalculatorListener {

	Controller controller;

	// create test table
	private OverswingValueTuple[] overswingTable = new OverswingValueTuple[4];

	// enter value of Ks Tu Tg
	private JLabel lbEnterKsTuTgTitle = new JLabel(
			"Eingabe der Kenngroesse der Regelstrecke:");
	private JLabel lbKs = new JLabel("Ks");
	private JLabel lbTu = new JLabel("Tu");
	private JLabel lbTg = new JLabel("Tg");
	// private JTextField tfKs = new JTextField(5);
	private JFormattedDoubleTextField tfKs = new JFormattedDoubleTextField(1);
	// private JTextField tfTu = new JTextField(5);
	private JFormattedDoubleTextField tfTu = new JFormattedDoubleTextField(1);
	// private JTextField tfTg = new JTextField(5);
	private JFormattedDoubleTextField tfTg = new JFormattedDoubleTextField(1);

	// time constant
	private JLabel lbTimeConstantTitle = new JLabel(
			"Parasitaere Zeitkonstante:");
	private JLabel lbTp = new JLabel("Tp");
	private JLabel lbTuInfo = new JLabel("%   (standardmaessig 10% von Tg)");
	// private JTextField tfTp = new JTextField("10", 5);
	private JFormattedDoubleTextField tfTp = new JFormattedDoubleTextField(1);

	// select regulator
	private JLabel lbSelectRegulatorTitle = new JLabel("Wahl des Reglers:");
	private JComboBox<String> cbSelectRegulator = new JComboBox<String>(
			new String[] { "I", "PI", "PID" });

	// Phasengangmethode overshoot
	private JLabel lbPhasengangmethodTitle = new JLabel(
			"Ueberschwingen der Phasengangmethode:");
	private JComboBox<String> cbSelectOvershoot = new JComboBox<>();

	// simulation button
	private JButton btSimulate = new JButton("Simulieren");

	// simulation title
	private JLabel lbSimulationTitle = new JLabel("Simulation");

	// test table
	private JTable tbTest = new JTable(10, 4);

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
    DefaultTableModel tableModel = new DefaultTableModel();
    JTable table = new JTable(tableModel);

	/**
	 * 
	 * @param controller
	 */
	public LeftPanel(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;

		// init overswnig table - see Pflichtenheft Technischer Teil Kapitel 2.3
		overswingTable[0] = new OverswingValueTuple(76.3, "0%");
		overswingTable[1] = new OverswingValueTuple(65.5, "4.6%");
		overswingTable[2] = new OverswingValueTuple(51.5, "16.3%");
		overswingTable[3] = new OverswingValueTuple(45, "23.3%");

		// add overswing table strings to combo box
		for (int i = 0; i < overswingTable.length; i++) {
			cbSelectOvershoot.addItem(overswingTable[i].percent());
		}

		// add items for input fields to GridBagLayout
		add(lbEnterKsTuTgTitle, new GridBagConstraints(0, 0, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));
		add(lbTu, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));
		add(lbTg, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));
		add(lbKs, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));
		add(tfTu, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 10), 50, 0));
		add(tfTg, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 10), 50, 0));
		add(tfKs, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 10), 50, 0));

		// add items for Tp to GridBagLayout
		add(lbTimeConstantTitle, new GridBagConstraints(0, 2, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));
		add(lbTp, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));
		add(lbTuInfo, new GridBagConstraints(2, 3, 4, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 0, 10, 10), 0, 0));
		tfTp.setText("10"); // set default value of Tp
		add(tfTp, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 0), 50, 0));

		// add label and comboBox for for regulator selection to GridBagLayout
		add(lbSelectRegulatorTitle, new GridBagConstraints(0, 4, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		add(cbSelectRegulator, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// add title and comboBox for overshoot selection to GridBagLayout
		add(lbPhasengangmethodTitle, new GridBagConstraints(0, 6, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		add(cbSelectOvershoot, new GridBagConstraints(0, 7, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// add button simulate to GridBagLayout
		add(btSimulate, new GridBagConstraints(0, 8, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// add title simulate to GridBagLayout
		add(lbSimulationTitle, new GridBagConstraints(0, 9, 5, 1, 0.0, 0.0,
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
		 * model.addColumn("Col1"); model.addColumn("Col2");
		 * model.addColumn("Col3");
		 * 
		 * String testheader[] = new String[] { "Prority", "Task Title",
		 * "Start", "Pause", "Stop", "Statulses" };
		 * 
		 * model.setColumnIdentifiers(testheader); table.setModel(model);
		 * 
		 * model.addRow(new Object[] { "v1", "v2" });
		 * 
		 * model.addRow(new Object[] { "v1" });
		 * 
		 * model.addRow(new Object[] { "v1", "v2", "v3" });
		 */

		tableModel.addColumn("Name");
		tableModel.addColumn("Kr");
		tableModel.addColumn("Tn");
		tableModel.addColumn("Tv");
		tableModel.addColumn("Tp");

		table.setPreferredSize(new Dimension(300, 300));
		
		// add header of table to GridBagLaout
		add(table.getTableHeader(), new GridBagConstraints(0, 10, 7, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		// add table to GridBagLayout
		add(table, new GridBagConstraints(0, 11, 7, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(0, 10, 10, 10), 0, 0));

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
		add(btDelete, new GridBagConstraints(4, 15, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// add button adopt to GridBagLayout
		add(btAdopt, new GridBagConstraints(0, 15, 4, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// add vertical dummy to GridbagLayout
		add(new JPanel(), new GridBagConstraints(0, 16, 1, 1, 0.0, 1.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.VERTICAL,
				new Insets(10, 10, 10, 10), 0, 0));

		// add ActionListener to buttons
		btSimulate.addActionListener(this);
		btAdopt.addActionListener(this);
		btDelete.addActionListener(this);
	}

	/**
	 * Setzt die Elemente auf sichtbar/unsichtbar welche sich in der Normal- und
	 * der Mini-Version unterscheiden
	 * 
	 * @param MiniVersionOn
	 */
	public void setMiniVersion(boolean miniVersionSelected) {
		// set all changing components to un- or visible
		lbSimulationTitle.setVisible(miniVersionSelected);
		tbTest.setVisible(miniVersionSelected);
		slKp.setVisible(miniVersionSelected);
		slTn.setVisible(miniVersionSelected);
		slTv.setVisible(miniVersionSelected);
		tfAdaptKp.setVisible(miniVersionSelected);
		tfAdaptTn.setVisible(miniVersionSelected);
		tfAdaptTv.setVisible(miniVersionSelected);
		btDelete.setVisible(miniVersionSelected);
		btAdopt.setVisible(miniVersionSelected);
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		// if button simulate is pressed
		if (e.getSource() == btSimulate) {
			// Stringwerte werden zu Double konvertiert
			/*
			 * double tfKsValue = Double.parseDouble(tfKs.getText()); double
			 * tfTuValue = Double.parseDouble(tfTu.getText()); double tfTgValue
			 * = Double.parseDouble(tfTg.getText()); double tfTpValue =
			 * Double.parseDouble(tfTp.getText());
			 */

			// convert string values to double
			double tfKsValue = tfKs.doubleValue();
			double tfTuValue = tfTu.doubleValue();
			double tfTgValue = tfTg.doubleValue();
			double tfTpValue = tfTp.doubleValue();

			// get text of selected regulator in comboBox
			String selectedRegulatorName = String.valueOf(cbSelectRegulator
					.getSelectedItem());

			// get index of selected overshoot in comboBox
			int overswingIndex = cbSelectOvershoot.getSelectedIndex();

			// give over the values to controller
			controller.btSimulateAction(tfKsValue, tfTuValue, tfTgValue,
					tfTpValue, selectedRegulatorName,
					overswingTable[overswingIndex]);
		}
		// if button delete is pressed
		if (e.getSource() == btDelete) {
			// call method of controller
			controller.btDeleteAction();
		}
		// if button adopt is pressed
		if (e.getSource() == btAdopt) {
			int slKpValue = slKp.getValue();
			int slTnValue = slTn.getValue();
			int slTvValue = slTv.getValue();
			//give over values to controller
			controller.btAdoptAction(slKpValue, slTnValue, slTvValue);
		}
	}

    @Override
    public void notifyNewController(IController controller) {
        controller.addToTable(tableModel);
    }
}