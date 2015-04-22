package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javafx.geometry.HorizontalDirection;



import java.util.Enumeration;

import javax.swing.*;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TabExpander;

/**
 * 
 * @author Josua
 *
 */
public class LeftPanel extends JPanel implements ActionListener {

    Controller controller;
	
	// Eingabefeld Ks Tu Tg
	private JLabel lbEnterKsTuTgTitle = new JLabel(
			"Eingabe der Kenngroesse der Regelstrecke:");
	private JLabel lbKs = new JLabel("Ks");
	private JLabel lbTu = new JLabel("Tu");
	private JLabel lbTg = new JLabel("Tg");
	//private JTextField tfKs = new JTextField(5);
	private JFormattedDoubleTextField tfKs = new JFormattedDoubleTextField(1);
	//private JTextField tfTu = new JTextField(5);
	private JFormattedDoubleTextField tfTu = new JFormattedDoubleTextField(1);
	//private JTextField tfTg = new JTextField(5);
	private JFormattedDoubleTextField tfTg = new JFormattedDoubleTextField(1);
	
	// Parasitaere Zeitkonstante
	private JLabel lbTimeConstantTitle = new JLabel(
			"Parasitaere Zeitkonstante:");
	private JLabel lbTp = new JLabel("Tp");
	private JLabel lbTuInfo = new JLabel("%   (standardmaessig 10% von Tg)");
	//private JTextField tfTp = new JTextField("10", 5);
	private JFormattedDoubleTextField tfTp = new JFormattedDoubleTextField(1);
	
	// Wahl des Reglers
	private JLabel lbSelectRegulatorTitle = new JLabel("Wahl des Reglers:");
	private JComboBox<String> cbSelectRegulator = new JComboBox<String>(
			new String[] { "I", "PI", "PID" });

	// Phasengangmethode Ueberschwingen
	private JLabel lbPhasengangmethodTitle = new JLabel(
			"Ueberschwingen der Phasengangmethode:");
	private JComboBox<String> cbSelectOvershoot = new JComboBox<String>(new String[] {
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
	private JSlider slKp = new JSlider(JSlider.HORIZONTAL, 0, 5, 3);
	private JSlider slTn = new JSlider(JSlider.HORIZONTAL, 0, 5, 3);
	private JSlider slTv = new JSlider(JSlider.HORIZONTAL, 0, 5, 3);
	private JFormattedDoubleTextField tfAdaptKp = new JFormattedDoubleTextField(1);
	private JFormattedDoubleTextField tfAdaptTn = new JFormattedDoubleTextField(1);
	private JFormattedDoubleTextField tfAdaptTv = new JFormattedDoubleTextField(1);

	// Button Loeschen und Uebernehmen
	private JButton btDelete = new JButton("Loeschen");
	private JButton btAdopt = new JButton("Uebernehmen");

	/**
	 * 
	 * @param controller
	 */
	public LeftPanel(Controller controller) {
		super(new GridBagLayout());
        this.controller = controller;

		//Eingabefelder
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
				new Insets(10, 5, 10, 10), 50, 0));
		add(tfTu, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 10), 50, 0));
		add(tfTg, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 10), 50, 0));

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
		tfTp.setText("10"); //setzt 
		add(tfTp, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 5, 10, 0), 50, 0));

		// Wahl des Reglers
		add(lbSelectRegulatorTitle, new GridBagConstraints(0, 4, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		add(cbSelectRegulator, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Einstellung des Ueberschwingen
		add(lbPhasengangmethodTitle, new GridBagConstraints(0, 6, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		add(cbSelectOvershoot, new GridBagConstraints(0, 7, 6, 1, 0.0, 0.0,
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

		// Test
		//tbTest.setEnabled(false);
		//JTableHeader header = tbTest.getTableHeader();
		//tbTest.setValueAt(aValue, row, column);
		
		
		/*DefaultTableModel model = new DefaultTableModel();
	    JTable table = new JTable(model);

	    model.addColumn("Col1");
	    model.addColumn("Col2");
	    model.addColumn("Col3");

	    String testheader[] = new String[] { "Prority", "Task Title", "Start",
	            "Pause", "Stop", "Statulses" };
	    
	    model.setColumnIdentifiers(testheader);
	    table.setModel(model);
	    
	    model.addRow(new Object[] { "v1", "v2" });

	    model.addRow(new Object[] { "v1" });

	    model.addRow(new Object[] { "v1", "v2", "v3" });

		*/
	
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		
		tableModel.addColumn("Name");
		tableModel.addColumn("Kp");
		tableModel.addColumn("Tn");
		tableModel.addColumn("Tv");
		tableModel.addColumn("Überschwingen");
		
		tableModel.addRow(new Object[] {"1","2","3","4","5"});
		
		//Header der Tabelle
		add(table.getTableHeader(), new GridBagConstraints(0, 10, 7, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 0, 10), 0, 0));
		//Tabelle
		add(table, new GridBagConstraints(0, 11, 7, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(0, 10, 10, 10), 0, 0));

		// Manuelles Anpassen Slider
		add(slKp, new GridBagConstraints(0, 12, 4, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						10, 10, 10, 10), 0, 0));
		add(slTn, new GridBagConstraints(0, 13, 4, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						10, 10, 10, 10), 0, 0));
		add(slTv, new GridBagConstraints(0, 14, 4, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						10, 10, 10, 10), 0, 0));
		// Manuelles Anpassen Textfelder
		add(tfAdaptKp, new GridBagConstraints(5, 12, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(
						10, 10, 10, 10), 0, 0));
		add(tfAdaptTn, new GridBagConstraints(5, 13, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(
						10, 10, 10, 10), 0, 0));
		add(tfAdaptTv, new GridBagConstraints(5, 14, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(
						10, 10, 10, 10), 0, 0));
		
		
		// Button Loeschen
		add(btDelete, new GridBagConstraints(3, 15, 3, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Button Uebernehmen
		add(btAdopt, new GridBagConstraints(0, 15, 3, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Vertikaler Dummy
		add(new JPanel(), new GridBagConstraints(0, 16, 1, 1, 0.0, 1.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.VERTICAL,
				new Insets(10, 10, 10, 10), 0, 0));

		// Registriert den ActionListener bei den Buttons
		btSimulate.addActionListener(this);
		btAdopt.addActionListener(this);
		btDelete.addActionListener(this);
	}
	/**
	 * Setzt die Elemente auf sichtbar/unsichtbar welche sich 
	 * in der Normal- und der Mini-Version unterscheiden
	 * 
	 * @param MiniVersionOn
	 */
	public void setMiniVersion(boolean miniVersionSelected){
		lbSimulationTitle.setVisible(miniVersionSelected);
		lbListeTitelName.setVisible(miniVersionSelected);
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
		//Wenn btSimulate gedrueckt wird
		if (e.getSource() == btSimulate) {
			//Stringwerte werden zu Double konvertiert
			/*double tfKsValue = Double.parseDouble(tfKs.getText());
			double tfTuValue = Double.parseDouble(tfTu.getText());
			double tfTgValue = Double.parseDouble(tfTg.getText());
			double tfTpValue = Double.parseDouble(tfTp.getText());*/
			
			//Stringwerte werden zu Double konvertiert
			double tfKsValue = tfKs.doubleValue();
			double tfTuValue = tfTu.doubleValue();
			double tfTgValue = tfTg.doubleValue();
			double tfTpValue = tfTp.doubleValue();
				
			//String des ausgewaehlten Reglers auslesen
			String selectedRegulatorName = String.valueOf( cbSelectRegulator.getSelectedItem());
			
			//String des ausgewaehlten Ueberschwingens auslesen
			String overshootName = String.valueOf( cbSelectOvershoot.getSelectedItem());
			//Prozentzeichen entfernen
			overshootName = overshootName.substring(0,overshootName.length() - 1);
			//zu double wandeln und dezimale Darstellung
			double overshootValue = (Double.parseDouble(overshootName));
			overshootValue = overshootValue/100;

			//die ausgelesenen und angepassten Werte dem Controller uebergeben
			controller.btSimulateAction(tfKsValue, tfTuValue, tfTgValue, tfTpValue, selectedRegulatorName, overshootValue);
		}
		//Wenn btDelete gedrueckt wird
		if (e.getSource() == btDelete) {
			controller.btDeleteAction();
		}
		//Wenn btAdopt gedrueckt wird
		if (e.getSource() == btAdopt) {
			int slKpValue = slKp.getValue();
			int slTnValue = slTn.getValue();
			int slTvValue = slTv.getValue();
			controller.btAdoptAction(slKpValue, slTnValue, slTvValue);
		}
	}
}