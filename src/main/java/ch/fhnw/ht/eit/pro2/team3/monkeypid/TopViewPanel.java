package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class TopViewPanel extends JPanel implements ActionListener {

	public TopViewPanel(Controller controller) {
		super(new GridBagLayout());
		
		LeftPanel leftPanel = new LeftPanel(controller); // LeftPanel
		GraphDisplayPanel graphDisplayPanel = new GraphDisplayPanel(controller);
		GraphPanel graphPanel = new GraphPanel(controller);

		// MenuBar
		JMenuBar menuBar = new JMenuBar();

		// Menu "Datei" erstellen
		JMenu menuDatei = new JMenu("Datei");
		JMenuItem eintrag1Datei = new JMenuItem("Mini-Version");
		menuDatei.add(eintrag1Datei);
		JMenuItem eintrag2Datei = new JMenuItem("Exit");
		menuDatei.add(eintrag2Datei);

		menuBar.add(menuDatei); // Menu Datei der MenuBar hinzufuegen

		// Help
		JMenu menuHilfe = new JMenu("Help");
		JMenuItem miTest12 = new JMenuItem("Option1");
		menuHilfe.add(miTest12);
		JMenuItem miTest22 = new JMenuItem("Option2");
		menuHilfe.add(miTest22);

		menuBar.add(menuHilfe);
		
		add(menuBar, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 10, 0, 10), 60, 10));

		// LeftPanel hinzufuegen
		add(leftPanel, new GridBagConstraints(0, 1, 1, 2, 0.0, 1.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.VERTICAL, new Insets(10, 10, 10, 10), 60, 0));
		leftPanel.setBorder(new TitledBorder(null, "Einstellungen"));

		// GraphPanel hinzufuegen
		add(graphPanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						10, 0, 10, 10), 0, 0));
		graphPanel.setBorder(new TitledBorder((null), "Graph"));

		// GraphDisplayPanel hinzufuegen
		add(graphDisplayPanel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 10, 10), 0, 0));
		graphDisplayPanel.setBorder(new TitledBorder((null), "Graph"));
		
		//graphPanel.setVisible(false);
		//graphDisplayPanel.setVisible(false);
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager
							.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				JFrame frame = new JFrame();
				frame.setUndecorated(true);
				frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Dimensionierungstool Phasengang-Methode");
				frame.getContentPane().add(new TopViewPanel(null));
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
