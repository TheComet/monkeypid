package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.SaniCurves;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates a panel which includes the input fields for Tu, Tg, Ks, Tp,
 * Overswing, a comboBox to select the regulator, the buttons and the table with
 * the results of the simulation and a slider to trimm the curve.
 * 
 * @author Josua
 *
 */
public class InputPanel extends JPanel implements ActionListener, IModelListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private View view;

	// regulator types
	static final int I = 0, PI = 1, PID = 2;

	// enter value of Ks Tu Tg
	private JLabel lbEnterKsTuTgTitle = new JLabel(
			"Eingabe der Kenngrösse der Regelstrecke:");
	private JLabel lbKs = new JLabel("Ks");
	private JLabel lbTu = new JLabel("Tu");
	private JLabel lbTg = new JLabel("Tg");
	private JFormattedDoubleTextField tfKs = new JFormattedDoubleTextField(
			null, 1);
	private JFormattedDoubleTextField tfTu = new JFormattedDoubleTextField(
			null, 1);
	private JFormattedDoubleTextField tfTg = new JFormattedDoubleTextField(
			null, 1);

	// info label for wrong value textfields
	private JLabel lbValueErrorInfo = new JLabel();

	// time constant
	private JLabel lbTimeConstantTitle = new JLabel(
			"Faktor für Parasitäre Zeitkonstante:");
	private JLabel lbTpInfo = new JLabel("%");
	private JFormattedDoubleTextField tfTp = new JFormattedDoubleTextField(
			null, 1);

	// select regulator
	private JLabel lbSelectRegulatorTitle = new JLabel("Wahl des Reglers:");
	private JComboBox<String> cbSelectRegulator = new JComboBox<String>(
			new String[] { "I", "PI", "PID" });

	// Phasengangmethode overshoot
	private JLabel lbPhasengangmethodTitle = new JLabel(
			"Überschwingen der Phasengangmethode:");
	private JFormattedDoubleTextField tfOvershoot = new JFormattedDoubleTextField(
			null, 1);
	private JLabel lbOvershootPercent = new JLabel("%");

	// simulation button
	private JButton btSimulate = new JButton("Simulieren");

	/**
	 * The constuctor of LeftPanel set the layout to GridBagLayout and adds all
	 * the components to the panel. Furthermore it creates the table for the
	 * results and the buttons listen to the ActionListener
	 * 
	 * @param controller
	 */
	public InputPanel(Controller controller, View view) {
		super(new GridBagLayout());
		this.controller = controller;
		this.view = view;

		// TODO remove
		tfTu.setText("2");
		tfTg.setText("6");
		tfKs.setText("1");

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

		// add tool tip for input fields
		tfTu.setToolTipText("Verzögerungszeit");
		tfTg.setToolTipText("Ausgleichszeit");
		tfKs.setToolTipText("Strecken-Verstärkung");
		tfTp.setToolTipText("Faktor für die Parasitäre Zeitkonstante in % von Tv/Tvk");
		tfOvershoot.setToolTipText("Überschwingen");

		// set dummy text to error info label
		lbValueErrorInfo.setText(" ");

		// add error info label to GridBagLayout
		add(lbValueErrorInfo, new GridBagConstraints(0, 2, 17, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(0, 10, 0, 10), 0, 0));

		// add label and comboBox for for regulator selection to GridBagLayout
		add(lbSelectRegulatorTitle, new GridBagConstraints(0, 3, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		add(cbSelectRegulator, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(5, 10, 10, 10), 0, 0));

		// add title and textField for overshoot to GridBagLayout
		add(lbPhasengangmethodTitle, new GridBagConstraints(0, 5, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(5, 10, 0, 10), 0, 0));
		tfOvershoot.setText("10"); // set default value
		add(tfOvershoot, new GridBagConstraints(0, 6, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(5, 10, 10, 10), 50, 0));
		add(lbOvershootPercent, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(5, 0, 10, 10), 0, 0));

		// add items for Tp to GridBagLayout
		add(lbTimeConstantTitle, new GridBagConstraints(0, 7, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));
		add(lbTpInfo, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(5, 0, 10, 10), 0, 0));

		tfTp.setText("10"); // set default value of Tp
		add(tfTp, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(5, 10, 10, 0), 50, 0));

		// add button simulate to GridBagLayout
		add(btSimulate,
				new GridBagConstraints(0, 9, 7, 1, 1.0, 0.0,
						GridBagConstraints.FIRST_LINE_START,
						GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10,
								10), 0, 0));

		// add ActionListener to buttons
		btSimulate.addActionListener(this);

		// addActionListener to select regulator comboBox
		cbSelectRegulator.addActionListener(this);

		// set fields greyed out for selected I-regulator
		lbPhasengangmethodTitle.setEnabled(false);
		tfOvershoot.setEnabled(false);
		lbOvershootPercent.setEnabled(false);
		lbTimeConstantTitle.setEnabled(false);
		lbTpInfo.setEnabled(false);
		tfTp.setEnabled(false);
	}

	/**
	 * Sets the Simulation-Button as Default-Button. i.e. If the Enter-Key is
	 * pressed, the Simulation-Buttons gets clicked (internally).
	 */
	public void setDefaultButtonSimulation() {
		JFrame myFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
		myFrame.getRootPane().setDefaultButton(btSimulate);
	}

	/**
	 * Sets the elements to visible or invisible. It depends on which version
	 * (mini or normal) is selected in the menu.
	 * 
	 * @param miniVersionSelected
	 */
	public void setMiniVersion(boolean miniVersionSelected) {
		// set all changing components to in- or visible
	}

	/**
<<<<<<< HEAD
	 * If button simulate is pressed the fields will be checked and if
	 * everything is correct the values will comitted to the controller.
	 * Furthermore fields will be greyed out depending on the regulator which is
	 * selected.
=======
	 * // TODO Stierli
>>>>>>> cleanup
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// if button simulate is pressed
		if (e.getSource() == btSimulate) {

			// convert string values to double
			// .doubleValue(); from JFormatedDoubleTextfield doesn't update
			// correct
			double tfKsValue = Double.parseDouble(tfKs.getText());
			double tfTuValue = Double.parseDouble(tfTu.getText());
			double tfTgValue = Double.parseDouble(tfTg.getText());
			// convert percent to absolute
			double tfTpValue = Double.parseDouble(tfTp.getText()) * 0.01;

			// get text of selected regulator in comboBox
			String selectedRegulatorName = String.valueOf(cbSelectRegulator
					.getSelectedItem());
			// get value of overshoot textField
			double valueOfOvershoot = Double.parseDouble(tfOvershoot.getText());

			// set dummy value for error textField, if not height would be zero
			lbValueErrorInfo.setText(" ");

			// set color of error info label to red
			lbValueErrorInfo.setForeground(Color.RED);

			// check input values
			if (tfTuValue <= 0) {
				// error message if value is zero
				lbValueErrorInfo.setText("Wert von Tu muss grösser 0 sein");
			} else if (tfTgValue <= 0) {
				// error message if value is zero
				lbValueErrorInfo.setText("Wert von Tg muss grösser 0 sein");
			} else if (tfKsValue <= 0) {
				// error message if value is zero
				lbValueErrorInfo.setText("Wert von Ks muss grösser 0 sein");
			} else if ((tfTuValue / tfTgValue) < 0.001) {
				// error message if tu/tg is smaller than 0.001 (value from
				// matlab sani example)
				lbValueErrorInfo
						.setText("Tu/Tg zu klein N = 1  => Verhältnis grösser wählen");
			} else if (valueOfOvershoot < 0) {
				// error message if value of ovsershoot is smaller than 0
				lbValueErrorInfo
						.setText("Wert des Überschwingens muss grösser gleich 0% sein");
			} else if (valueOfOvershoot > 45) {
				// error message if value of overshoot is greater or equal 45%
				lbValueErrorInfo
				.setText("Wert des Überschwingens muss kleiner gleich 45% sein");
			} else if (tfTpValue <= 0) {
				// error message if value of tp is smaller than 0
				lbValueErrorInfo.setText("Wert von TP ist kleiner als 0");
			} else if (tfTpValue > 10) {
				// error message if value of tp is greater than 10
				lbValueErrorInfo.setText("Wert von TP ist grösser als 10");
			} else if (tfOvershoot.getText().equals("")) {
				lbValueErrorInfo.setText("error");

			} else {
				// set dummy value in textfield
				lbValueErrorInfo.setText(" ");

				// give over the values to controller
				try {
					controller.btSimulateAction(tfKsValue, tfTuValue,
							tfTgValue, tfTpValue, selectedRegulatorName,
							valueOfOvershoot);
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

		// sets parts from inputPanel greyed out for different regulators
		if (e.getSource() == cbSelectRegulator) {
			switch (cbSelectRegulator.getSelectedIndex()) {
			// I regulator selected
			case I:
				lbPhasengangmethodTitle.setEnabled(false);
				tfOvershoot.setEnabled(false);
				lbOvershootPercent.setEnabled(false);
				lbTimeConstantTitle.setEnabled(false);
				lbTpInfo.setEnabled(false);
				tfTp.setEnabled(false);
				break;
			// PI regulator selected
			case PI:
				lbPhasengangmethodTitle.setEnabled(true);
				tfOvershoot.setEnabled(true);
				lbOvershootPercent.setEnabled(true);
				lbTimeConstantTitle.setEnabled(false);
				lbTpInfo.setEnabled(false);
				tfTp.setEnabled(false);
				break;
			// PID regulator selected
			case PID:
				lbPhasengangmethodTitle.setEnabled(true);
				tfOvershoot.setEnabled(true);
				lbOvershootPercent.setEnabled(true);
				lbTimeConstantTitle.setEnabled(true);
				lbTpInfo.setEnabled(true);
				tfTp.setEnabled(true);
				break;
			default:
				break;
			}
		}
	}
	// TODO Murray
	/**
	 * 
	 */
	@Override
	public void onSetPlant(Plant plant) {
		// set color of error info label to red
		lbValueErrorInfo.setForeground(Color.BLACK);
		lbValueErrorInfo.setText("(Ordnung der Strecke = " + plant.getOrder() + ")");
	}

	@Override
	public void onAddCalculation(ClosedLoop closedLoop, boolean visible) {}

	@Override
	public void onRemoveCalculation(ClosedLoop closedLoop) {}

	@Override
	public void onUpdateCalculation(ClosedLoop closedLoop) {
	}

	@Override
	public void onSimulationBegin(int numberOfStepResponses) {
	}

	@Override
	public void onSimulationComplete() {}

	@Override
	public void onHideCalculation(ClosedLoop closedLoop) {}

	@Override
	public void onShowCalculation(ClosedLoop closedLoop) {}
}