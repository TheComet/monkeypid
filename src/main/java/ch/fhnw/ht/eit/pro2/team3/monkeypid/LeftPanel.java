package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

public class LeftPanel extends JPanel implements ActionListener {

	// Eingabefeld Ks Tu Tg
	private JLabel lbEnterKsTuTgTitle = new JLabel(
			"Eingabe der Kenngrösse der Regelstrecke:");
	private JLabel lbKs = new JLabel("Ks");
	private JLabel lbTu = new JLabel("Tu");
	private JLabel lbTg = new JLabel("Tg");
	private JTextField tfKs = new JTextField(5);
	private JTextField tfTu = new JTextField(5);
	private JTextField tfTg = new JTextField(5);

	// Parasitaere Zeitkonstante
	private JLabel lbTimeConstantTitle = new JLabel("Parasitäre Zeitkonstante:");
	private JLabel lbTp = new JLabel("Tp");
	private JLabel lbTuInfo = new JLabel("%   (standardmässig 10% von Tg)");
	private JTextField tfTp = new JTextField("10", 5);

	// Wahl des Reglers
	private JLabel lbChooseRegulatorTitle = new JLabel("Wahl des Reglers:");
	private JComboBox<String> cbChooseRegulator = new JComboBox<String>(
			new String[] { "I", "PI", "PID" });

	// Phasengangmethode Ueberschwingen
	private JLabel lbPhasengangmethodTitle = new JLabel(
			"Überschwingen der Phasengangmethode:");
	private JComboBox<String> cbOvershoot = new JComboBox<String>(new String[] {
			"0%", "4.6%", "16.3%" });

	// Simulationsbutton
	private JButton btSimulate = new JButton("Simulieren");
	
	// Simulation
	private JLabel lbSimulationTitle = new JLabel("Simulation");

	// Beschriftung der Liste
	private JLabel lbOutputSimulation = new JLabel("Ausgangssimulation:");
	private JLabel lbListeTitelName = new JLabel("Name");
	private JLabel lbListeTitelKp = new JLabel("KP");
	private JLabel lbListeTitelKi = new JLabel("Ki");
	private JLabel lbListeTitelKd = new JLabel("Kd");
	private JLabel lbListeTitelUeberschwingen = new JLabel("Überschwingen");

	// Test Tabelle
	private JTable tbTest = new JTable(10, 5);

	// 
	
	// Button Loeschen und Uebernehmen
	private JButton btDelete = new JButton("Löschen");
	private JButton btAdopt = new JButton("Übernehmen");
	
	public LeftPanel(Controller controller) {
		super(new GridBagLayout());

		add(lbEnterKsTuTgTitle, new GridBagConstraints(0, 0, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
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
				new Insets(10, 10, 10, 10), 0, 0));
		add(tfTu, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(tfTg, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Tp
		add(lbTimeConstantTitle, new GridBagConstraints(0, 2, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(lbTp, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));
		add(lbTuInfo, new GridBagConstraints(2, 3, 4, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 0, 10, 10), 0, 0));
		add(tfTp, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 0), 0, 0));

		//
		add(lbChooseRegulatorTitle, new GridBagConstraints(0, 4, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		add(cbChooseRegulator, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Einstellung des Ueberschwingen
		add(lbPhasengangmethodTitle, new GridBagConstraints(0, 6, 6, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		add(cbOvershoot, new GridBagConstraints(0, 7, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		// Button Simulieren
		add(btSimulate, new GridBagConstraints(0, 8, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		

		/*
		 * // Liste der Ausgangssimulationen add(lbOutputSimulation, new
		 * GridBagConstraints(0, 9, 6, 1, 0.0, 0.0,
		 * GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new
		 * Insets(10, 10, 10, 10), 0, 0)); add(lbListeTitelName, new
		 * GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
		 * GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new
		 * Insets(10, 10, 10, 10), 0, 0)); add(lbListeTitelKp, new
		 * GridBagConstraints(1, 10, 1, 1, 0.0, 0.0,
		 * GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new
		 * Insets(10, 10, 10, 10), 0, 0)); add(lbListeTitelKi, new
		 * GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
		 * GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new
		 * Insets(10, 10, 10, 10), 0, 0)); add(lbListeTitelKd, new
		 * GridBagConstraints(3, 10, 1, 1, 0.0, 0.0,
		 * GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new
		 * Insets(10, 10, 10, 10), 0, 0)); add(lbListeTitelUeberschwingen, new
		 * GridBagConstraints(4, 10, 1, 1, 0.0, 0.0,
		 * GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new
		 * Insets(10, 10, 10, 10), 0, 0));
		 */

		// Button Loeschen
		add(btDelete, new GridBagConstraints(4, 15, 2, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Button Uebernehmen
		add(btAdopt, new GridBagConstraints(2, 15, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		
		// Test
		tbTest.setEnabled(false);
		add(tbTest, new GridBagConstraints(0, 14, 6, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));

		// Vertikaler Dummy
		add(new JPanel(), new GridBagConstraints(0, 13, 1, 1, 0.0, 1.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.VERTICAL,
				new Insets(10, 10, 10, 10), 0, 0));
	}

	/*
	 * public static void main(String args[]) { SwingUtilities.invokeLater(new
	 * Runnable() { public void run() { try { UIManager
	 * .setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); } catch
	 * (Exception exception) { exception.printStackTrace(); } JFrame frame = new
	 * JFrame(); frame.setUndecorated(true);
	 * frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * frame.setTitle("TopView"); frame.getContentPane().add(new
	 * LeftPanel(null)); frame.pack(); frame.setVisible(true); } }); }
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btSimulate) {

		}
		if (e.getSource() == btDelete) {

		}

	}
}
