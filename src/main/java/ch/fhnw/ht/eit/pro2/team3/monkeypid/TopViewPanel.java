package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Josua
 *
 */
public class TopViewPanel extends JPanel implements ActionListener, Observer {

    private Model model;
    private Controller controller;

	/**
	 * 
	 * @param controller
	 */
	public TopViewPanel(Controller controller, Model model) {
		super(new GridBagLayout());
		
		this.controller = controller;
        this.model = model;

		LeftPanel leftPanel = new LeftPanel(controller); // LeftPanel
		GraphDisplayPanel graphDisplayPanel = new GraphDisplayPanel(controller);
		GraphPanel graphPanel = new GraphPanel(controller);

		// MenuBar
		JMenuBar menuBar = new JMenuBar();

		// Menu "Datei" erstellen
		JMenu menuDatei = new JMenu("Datei");
		JMenuItem eintragMiniVersion = new JMenuItem("Mini-Version");
		menuDatei.add(eintragMiniVersion);
		JMenuItem eintrag2Datei = new JMenuItem("Exit");
		menuDatei.add(eintrag2Datei);

		menuBar.add(menuDatei); // Menu Datei der MenuBar hinzufuegen

		// Help
		JMenu menuHilfe = new JMenu("Help");
		
		JMenuItem miTest12 = new JMenuItem("Links");
		menuHilfe.add(miTest12);
		JMenuItem miTest22 = new JMenuItem("Option2");
		menuHilfe.add(miTest22);
		
		
		//submenu Links
		JMenu ret = new JMenu("Links");
		ret.setMnemonic('L');
		JMenuItem testLinkItem = new JMenuItem("LINK1", 'R');
		ret.add(testLinkItem);
		ret.add(new JMenuItem("LINK2", 'E'));
		ret.add(new JMenuItem("LINK3", 'B'));
		ret.add(new JMenuItem("LINK4", 'Z'));
		ret.add(new JMenuItem("LINK5", 'S'));
		ret.add(new JMenuItem("LINK6", 'V'));
		
		menuHilfe.add(ret);

		menuBar.add(menuHilfe);

		add(menuBar, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 10, 0, 10), 60, 10));

		// LeftPanel hinzufuegen
		add(leftPanel, new GridBagConstraints(0, 1, 1, 2, 0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				new Insets(10, 10, 10, 10), 60, 0));
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

		// graphPanel.setVisible(false);
		// graphDisplayPanel.setVisible(false);
	}

	// TEST TEST
	private JMenu createToolsSubMenu() {
		JMenu ret = new JMenu("Tools");
		ret.setMnemonic('T');
		ret.add(new JMenuItem("Rechner", 'R'));
		ret.add(new JMenuItem("Editor", 'E'));
		ret.add(new JMenuItem("Browser", 'B'));
		ret.add(new JMenuItem("Zipper", 'Z'));
		ret.add(new JMenuItem("Snapper", 'S'));
		ret.add(new JMenuItem("Viewer", 'V'));
		return ret;
	}

	// TESTET

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		/*if (e.getSource() == TestLink) {
			try {
			    Desktop.getDesktop().browse(new URL("http://www.google.com").toURI());
			} catch (Exception e) {}
		}*/
	}

    @Override
    public void update(Observable observable, Object o) {

    }
}
