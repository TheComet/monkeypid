package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.PhaseAndOverSwingTuple;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.SaniCurves;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Creates a panel which includes the input fields for Tu, Tg, Ks, Tp, a
 * comboBox to select the regulator, a comboBox to select the overshoot, the
 * buttons and the table with the results of the simulation.
 * 
 * @author Josua
 *
 */
public class InputPanel extends JPanel implements ActionListener, KeyListener, IModelListener {

	Controller controller;

	// create test table
	private PhaseAndOverSwingTuple[] overswingTable = new PhaseAndOverSwingTuple[4];

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
		overswingTable[0] = new PhaseAndOverSwingTuple(76.3, "0%");
		overswingTable[1] = new PhaseAndOverSwingTuple(65.5, "4.6%");
		overswingTable[2] = new PhaseAndOverSwingTuple(51.5, "16.3%");
		overswingTable[3] = new PhaseAndOverSwingTuple(45, "23.3%");

		// add overswing table strings to combo box
		for (int i = 0; i < overswingTable.length; i++) {
			cbSelectOvershoot.addItem(overswingTable[i].percent());
		}

		//TODO remove
		tfTu.setValue(2);
		tfTg.setValue(6);
		tfKs.setValue(1);
		
		
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

		add(new JLabel(), new GridBagConstraints(6, 0, 1, 1, 	1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(10, 5, 0, 10), 50, 0));

		
		//add tool tip for input fields
		tfTu.setToolTipText("Test ToolTip");
		tfTg.setToolTipText("Test ToolTip");
		tfKs.setToolTipText("Test ToolTip");
		
		//TODO best solution?
		tfKs.setPreferredSize(new Dimension(50, 25));
		tfKs.setMinimumSize(new Dimension(50, 25));

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
		add(lbTuInfo, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(10, 0, 10, 10), 0, 0));

		tfTp.setValue(10); // set default value of Tp
		add(tfTp, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 50, 0));

		// add label and comboBox for for regulator selection to GridBagLayout
		add(lbSelectRegulatorTitle, new GridBagConstraints(0, 5, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		add(cbSelectRegulator, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(5, 10, 10, 10), 0, 0));

		// add title and comboBox for overshoot selection to GridBagLayout
		add(lbPhasengangmethodTitle, new GridBagConstraints(0, 7, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(5, 10, 0, 10), 0, 0));
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
			double tfTpValue = tfTp.doubleValue() * 0.01; // convert percent to absolute

			// get text of selected regulator in comboBox
			String selectedRegulatorName = String.valueOf(cbSelectRegulator
					.getSelectedItem());

			// get index of selected overshoot in comboBox
			int overswingIndex = cbSelectOvershoot.getSelectedIndex();
			
			// handling of wrong entries
			lbValueErrorInfo.setText(" ");
			
			// set color of error info label to red
			lbValueErrorInfo.setForeground(Color.RED);
			
			if (tfTuValue == 0) {
				// error message if value is zero
				lbValueErrorInfo.setText("Wert von Tu darf nicht 0 sein");
			} else if (tfTgValue == 0) {
				// error message if value is zero
				lbValueErrorInfo.setText("Wert von Tg darf nicht 0 sein");
			} else if (tfKsValue == 0) {
				// error message if value is zero
				lbValueErrorInfo.setText("Wert von Ks darf nicht 0 sein");
			} else if ((tfTuValue / tfTgValue) < 0.001) {
				// error message if tu/tg is smaller than 0.001 (value from
				// matlab sani example)
				lbValueErrorInfo
						.setText("Tu/Tg zu klein N = 1  => Verhältnis grösser wählen");
			} else {
				// set dummy value in textfield
				lbValueErrorInfo.setText(" ");

				// give over the values to controller
				try {
					controller.btSimulateAction(tfKsValue, tfTuValue, tfTgValue,
							tfTpValue, selectedRegulatorName,
							overswingTable[overswingIndex]);
				} catch (Model.InvalidPlantForPIDSimulationException ex) {
					// set color of error info label to red
					lbValueErrorInfo.setForeground(Color.RED);
					lbValueErrorInfo.setText(ex.getMessage());
				} catch (SaniCurves.TuTgRatioTooLargeException ex) {
					// set color of error info label to red
					lbValueErrorInfo.setForeground(Color.RED);
					lbValueErrorInfo.setText(ex.getMessage());
				}
			}

		}
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
	public void onSetPlant(Plant plant) {
		// set color of error info label to red
		lbValueErrorInfo.setForeground(Color.BLACK);
		lbValueErrorInfo.setText("Ordnung der Strecke = "+plant.getOrder());
	}

	@Override
	public void onAddCalculation(ClosedLoop closedLoop) {}

	@Override
	public void onRemoveCalculation(ClosedLoop closedLoop) {}

	@Override
	public void onSimulationBegin(int numberOfStepResponses) {}

	@Override
	public void onSimulationComplete() {}

	@Override
	public void onHideCalculation(ClosedLoop closedLoop) {}

	@Override
	public void onShowCalculation(ClosedLoop closedLoop) {}
}