package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;


import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IControllerCalculatorListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.OverswingValueTuple;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Creates a panel which includes the input fields for Tu, Tg, Ks, Tp, a
 * comboBox to select the regulator, a comboBox to select the overshoot, the
 * buttons and the table with the results of the simulation.
 * 
 * @author Josua
 *
 */
public class InputPanel extends JPanel implements ActionListener,
		IControllerCalculatorListener, KeyListener, IModelListener {

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

	// info label for wrong value textfields
	private JLabel lbValueErrorInfo = new JLabel();

	// time constant
	private JLabel lbTimeConstantTitle = new JLabel(
			"Faktor für Parasitäre Zeitkonstante:");
	//private JLabel lbTp = new JLabel("Tp");
	private JLabel lbTuInfo = new JLabel("%");
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

	// table and table model
	DefaultTableModel tableModel = new DefaultTableModel();
	JTable table = new JTable(tableModel);

	/**
	 * The constuctor of Leftpanel set the layout to GridBagLayout and adds all
	 * the components to the panel. Furthermore it creates the table for the
	 * results and the buttons listen to the ActionListener
	 * 
	 * @param controller
	 */
	public InputPanel(Controller controller) {
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
				new Insets(10, 10, 0, 0), 0, 0));
		add(lbTg, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 0), 0, 0));
		add(lbKs, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 0), 0, 0));
		add(tfTu, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 0, 10), 50, 0));
		add(tfTg, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 0, 10), 50, 0));
		add(tfKs, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 0, 10), 50, 0));

		// set color of error info label to red
		lbValueErrorInfo.setForeground(Color.RED);

		// set dummy text to error info label
		lbValueErrorInfo.setText(" ");

		// add error info label to GridBagLayout
		add(lbValueErrorInfo, new GridBagConstraints(0, 2, 17, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(0, 10, 0, 10), 0, 0));

		// add items for Tp to GridBagLayout
		add(lbTimeConstantTitle, new GridBagConstraints(0, 3, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));
		/*add(lbTp, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));*/
		add(lbTuInfo, new GridBagConstraints(2, 4, 4, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 0, 10, 10), 0, 0));
		tfTp.setText("10"); // set default value of Tp
		add(tfTp, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 50, 0));

		// add label and comboBox for for regulator selection to GridBagLayout
		add(lbSelectRegulatorTitle, new GridBagConstraints(0, 5, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		add(cbSelectRegulator, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// add title and comboBox for overshoot selection to GridBagLayout
		add(lbPhasengangmethodTitle, new GridBagConstraints(0, 7, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		add(cbSelectOvershoot, new GridBagConstraints(0, 8, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// add button simulate to GridBagLayout
		add(btSimulate, new GridBagConstraints(0, 9, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(0, 10, 20, 10), 0, 0));

		// add ActionListener to buttons
		btSimulate.addActionListener(this);

		// set leftpanel focusable
		this.setFocusable(true);

		// add KeyListener to panel
		this.addKeyListener(this);

		// pack frame
		//JFrame myParent = (JFrame) view.getTopLevelAncestor(); // get frame
		
		//myParent.getRootPane().setDefaultButton(btSimulate);
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
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {

		// if button simulate is pressed
		if (e.getSource() == btSimulate) {

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

			// handling of wrong entries
			lbValueErrorInfo.setText(" ");
			if (tfTuValue == 0) {
				// error message if value is zero
				lbValueErrorInfo.setText("Wert von Tu darf nicht 0 sein");
			} else if (tfTgValue == 0) {
				// error message if value is zero
				lbValueErrorInfo.setText("Wert von Tg darf nicht 0 sein");
			} else if (tfKsValue == 0) {
				// error message if value is zero
				lbValueErrorInfo.setText("Wert von Ks darf nicht 0 sein");
			} else if ((tfTuValue / tfTgValue) > 0.64173) {
				// error message if tu/tg is bigger than 0.64173 (value from
				// matlab sani example)
				lbValueErrorInfo
						.setText("Tu/Tg zu gross N > 8  => Verhältnis kleiner wählen");
			} else if ((tfTuValue / tfTgValue) < 0.001) {
				// error message if tu/tg is smaller than 0.001 (value from
				// matlab sani example)
				lbValueErrorInfo
						.setText("Tu/Tg zu klein N = 1  => Verh�ltnis gr�sser w�hlen");
			} else {
				// set dummy value in textfield
				lbValueErrorInfo.setText(" ");

				// give over the values to controller
				controller.btSimulateAction(tfKsValue, tfTuValue, tfTgValue,
						tfTpValue, selectedRegulatorName,
						overswingTable[overswingIndex]);
			}

		}
	}

	@Override
    public void onAddClosedLoop(ClosedLoop closedLoop) {
		closedLoop.getController().addToTable(tableModel);
	}

	@Override
	public void onRemoveClosedLoop(ClosedLoop closedLoop) {
        closedLoop.getController().removeFromTable(tableModel);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// not needed
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// if enter on keyboard is pressed simulation starts, same as btSimulate
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// press button simulate
			btSimulate.doClick();
			e.setKeyCode(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// not needed
	}

    @Override
    public void onSimulationStarted() {

    }

    @Override
    public void onSimulationComplete() {

    }

	@Override
	public void onControllerCalculationComplete(IControllerCalculator controller) {
		// TODO Auto-generated method stub
		
	}
}