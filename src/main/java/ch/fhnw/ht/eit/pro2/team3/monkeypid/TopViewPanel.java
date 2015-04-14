package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JWindow;
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

	// Projektname
	private String projektName = "monkeypid";

	// TODO besserer Name
	private boolean miniVersionSelected = false;

	// MenuBar
	private JMenuBar menuBar = new JMenuBar();

	// Menu Datei
	private JMenu menuData = new JMenu("Datei");

	// Submenu Hilfreiche Links
	private JMenuItem LinkWikiRegelungstechnik = new JMenuItem(
			"Regelungstechnik Wiki", 'R');
	private JMenuItem LinkWikiFaustformelverfahren = new JMenuItem(
			"Faustformelverfahren Wiki", 'F');
	private JMenuItem LinkRnWissenRegelungstechnik = new JMenuItem(
			"Regelungstechnik RnWissen", 'e');
	private JMenuItem LinkPhasengangMethodePDF = new JMenuItem(
			"Phasengang-Methode.pdf", 'P');
	private JMenuItem LinkRegelkreiseUndRegelungenPDF = new JMenuItem(
			"Regelkreise und Regelungen.pdf", 'R');
	private JMenuItem LinkPidEinstellenPDF = new JMenuItem(
			"pid-einstellen.pdf", 'p');
	private JMenuItem LinkBuergieSolenickiV3PDF = new JMenuItem(
			"Buergi_Solenicki-V3.pdf", 'P');

	// Menueintraege
	private JMenuItem menuItemExit = new JMenuItem("Exit");
	private JMenuItem menuItemInfo = new JMenuItem("Info");
	private JMenuItem menuItemMiniVersion = new JMenuItem("Mini-Version");

	private LeftPanel leftPanel;
	private GraphDisplayPanel graphDisplayPanel;
	private GraphPanel graphPanel;

	/**
	 * 
	 * @param controller
	 */
	public TopViewPanel(Controller controller, Model model) {
		super(new GridBagLayout());

		this.controller = controller;
		this.model = model;

		leftPanel = new LeftPanel(controller); // LeftPanel
		graphDisplayPanel = new GraphDisplayPanel(controller);
		graphPanel = new GraphPanel(controller);
		
		// TODO
		// Registriert die Eintraege der MenuBar beim ActionListener
		LinkWikiRegelungstechnik.addActionListener(this);
		LinkWikiFaustformelverfahren.addActionListener(this);
		LinkRnWissenRegelungstechnik.addActionListener(this);
		LinkPhasengangMethodePDF.addActionListener(this);
		LinkRegelkreiseUndRegelungenPDF.addActionListener(this);
		LinkPidEinstellenPDF.addActionListener(this);
		LinkBuergieSolenickiV3PDF.addActionListener(this);
		menuItemExit.addActionListener(this);
		menuItemInfo.addActionListener(this);
		menuItemMiniVersion.addActionListener(this);

		// Menu Datei erstellen
		menuData.add(menuItemMiniVersion);
		menuData.add(menuItemExit);
		menuBar.add(menuData); // Menu Datei der MenuBar hinzufuegen

		// Help
		JMenu menuHilfe = new JMenu("Help");
		menuHilfe.add(menuItemInfo);

		// Submenu Hilfreiche Links
		JMenu ret = new JMenu("Hilfreiche Links");
		ret.setMnemonic('H');
		ret.add(LinkWikiRegelungstechnik);
		ret.add(LinkWikiFaustformelverfahren);
		ret.add(LinkRnWissenRegelungstechnik);
		ret.add(LinkPhasengangMethodePDF);
		ret.add(LinkRegelkreiseUndRegelungenPDF);
		ret.add(LinkPidEinstellenPDF);
		ret.add(LinkBuergieSolenickiV3PDF);

		menuHilfe.add(ret);

		menuBar.add(menuHilfe);

		// TODO Remove
		// menuBar.setPreferredSize(new Dimension(100,50));
		// menuBar.setMinimumSize(new Dimension(100,50));
		// MenuBar hinzufuegen
		add(menuBar, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 10, 0, 10), 50, 10));
		// TODO TEST ENTFERNEN JOSUA
		/*
		 * add(new MenuPanel(controller), new GridBagConstraints(0, 0, 1, 1,
		 * 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
		 * new Insets(0, 10, 0, 10), 50, 10));
		 */

		// LeftPanel hinzufuegen
		add(leftPanel, new GridBagConstraints(0, 1, 1, 2, 0.0, 1.0,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.VERTICAL, new Insets(10, 10, 10, 10), 50, 0));
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
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		// ruft den Link im Standardwebbrowser auf
		if (e.getSource() == LinkWikiRegelungstechnik) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://de.wikipedia.org/wiki/Regler").toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		// ruft den Link im Standardwebbrowser auf
		if (e.getSource() == LinkWikiFaustformelverfahren) {
			try {
				Desktop.getDesktop()
						.browse(new URL(
								"http://de.wikipedia.org/wiki/Faustformelverfahren_%28Automatisierungstechnik%29")
								.toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		// ruft den Link im Standardwebbrowser auf
		if (e.getSource() == LinkRnWissenRegelungstechnik) {
			try {
				Desktop.getDesktop()
						.browse(new URL(
								"http://rn-wissen.de/wiki/index.php/Regelungstechnik")
								.toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		// ruft den Link im Standardwebbrowser auf
		if (e.getSource() == LinkPhasengangMethodePDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/rt_phasengang-methode.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		// ruft den Link im Standardwebbrowser auf
		if (e.getSource() == LinkRegelkreiseUndRegelungenPDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/Regelkreise_und_Regelungen.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		// ruft den Link im Standardwebbrowser auf
		if (e.getSource() == LinkPidEinstellenPDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/pid-einstellregeln.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		// ruft den Link im Standardwebbrowser auf
		if (e.getSource() == LinkBuergieSolenickiV3PDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/Buergi_Solenicki-V3.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		// Beendet das Tool und schliesst das Fenster
		if (e.getSource() == menuItemExit) {
			System.exit(1);
		}
		if (e.getSource() == menuItemInfo) {
			JOptionPane
					.showMessageDialog(
							this,
							"Titel\nVersion: 1.0\n\nFHNW Brugg Windisch\nProjekt 2 Team 3\nYanick Frei\nSimon Wyss\nSimonSturm\nJosua Stierli\nAlex Murray",
							"Info", JOptionPane.INFORMATION_MESSAGE);
		}

		// Umschalten zwischen Normal- und Mini-Version
		if (e.getSource() == menuItemMiniVersion) {

			graphPanel.setVisible(miniVersionSelected); // graphPanel
														// ein-/ausblenden
			graphDisplayPanel.setVisible(miniVersionSelected); // graphDisplayPanel
																// ein-/ausblenden
			// schaltet alle unerwuenschten Komponenten auf dem leftPanel aus
			leftPanel.setMiniVersion(miniVersionSelected);

			miniVersionSelected = !miniVersionSelected; // invertiert die Zustandsvariable fuer die Ansicht
		}
	}

	public void update(Observable observable, Object o) {
	}
}
