package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.geometry.HorizontalDirection;

import javax.swing.*;

public class LeftPanel extends JPanel implements ActionListener {

	// Eingabefeld Ks Tu Tg
	private JLabel lbEnterKsTuTgTitle = new JLabel(
			"Eingabe der Kenngroesse der Regelstrecke:");
	private JLabel lbKs = new JLabel("Ks");
	private JLabel lbTu = new JLabel("Tu");
	private JLabel lbTg = new JLabel("Tg");
	private JTextField tfKs = new JTextField(5);
	private JTextField tfTu = new JTextField(5);
	private JTextField tfTg = new JTextField(5);

	// Parasitaere Zeitkonstante
	private JLabel lbTimeConstantTitle = new JLabel(
			"Parasitaere Zeitkonstante:");
	private JLabel lbTp = new JLabel("Tp");
	private JLabel lbTuInfo = new JLabel("%   (standardmaessig 10% von Tg)");
	private JTextField tfTp = new JTextField("10", 5);

	// Wahl des Reglers
	private JLabel lbChooseRegulatorTitle = new JLabel("Wahl des Reglers:");
	private JComboBox<String> cbChooseRegulator = new JComboBox<String>(
			new String[] { "I", "PI", "PID" });

	// Phasengangmethode Ueberschwingen
	private JLabel lbPhasengangmethodTitle = new JLabel(
			"Ueberschwingen der Phasengangmethode:");
	private JComboBox<String> cbOvershoot = new JComboBox<String>(new String[] {
			"0%", "4.6%", "16.3%" });

	// Simulationsbutton
	private JButton btSimulate = new JButton("Simulieren");

	// Simulation
	private JLabel lbSimulationTitle = new JLabel("Simulation");

	// Beschriftung der Liste
	private JLabel lbListeTitelName = new JLabel("Name Kp Tn Tv Ueberschwingen");


	// Test Tabelle
	private JTable tbTest = new JTable(10, 4);

	// Manuelles Anpassen
	private JSlider slKp = new JSlider(JSlider.HORIZONTAL,0,5,3);
	private JSlider slTn = new JSlider(JSlider.HORIZONTAL,0,5,3);
	private JSlider slTv = new JSlider(JSlider.HORIZONTAL,0,5,3);

	// Button Loeschen und Uebernehmen
	private JButton btDelete = new JButton("Löschen");
	private JButton btAdopt = new JButton("Übernehmen");

	public LeftPanel(Controller controller) {
		super(new GridBagLayout());

		add(lbEnterKsTuTgTitle, new GridBagConstraints(0, 0, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));
		add(lbKs, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));
		add(lbTu, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));
		add(lbTg, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));
		add(tfKs, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 10), 0, 0));
		add(tfTu, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 10), 0, 0));
		add(tfTg, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 10), 0, 0));

		// Tp
		add(lbTimeConstantTitle, new GridBagConstraints(0, 2, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));
		add(lbTp, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));
		add(lbTuInfo, new GridBagConstraints(2, 3, 4, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 0, 10, 10), 0, 0));
		add(tfTp, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 0), 0, 0));

		//
		add(lbChooseRegulatorTitle, new GridBagConstraints(0, 4, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		add(cbChooseRegulator, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Einstellung des Ueberschwingen
		add(lbPhasengangmethodTitle, new GridBagConstraints(0, 6, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		add(cbOvershoot, new GridBagConstraints(0, 7, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		// Button Simulieren
		add(btSimulate, new GridBagConstraints(0, 8, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Button Simulieren
		add(lbSimulationTitle, new GridBagConstraints(0, 9, 5, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));

		// Liste der Ausgangssimulationen
		add(lbListeTitelName, new GridBagConstraints(0, 10, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));
		

		// Test
		tbTest.setEnabled(false);
		add(tbTest, new GridBagConstraints(0, 11, 7, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		
		// Slider
		add(slKp, new GridBagConstraints(0, 12, 5, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(slTn, new GridBagConstraints(0, 13, 5, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(slTv, new GridBagConstraints(0, 14, 5, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Button Loeschen
		add(btDelete, new GridBagConstraints(3, 15, 3, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Button Uebernehmen
		add(btAdopt, new GridBagConstraints(0, 15, 3, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(
						10, 10, 10, 10), 0, 0));

		// Vertikaler Dummy
		add(new JPanel(), new GridBagConstraints(0, 16, 1, 1, 0.0, 1.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.VERTICAL,
				new Insets(10, 10, 10, 10), 0, 0));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btSimulate) {

		}
		if (e.getSource() == btDelete) {

		}

	}
}
