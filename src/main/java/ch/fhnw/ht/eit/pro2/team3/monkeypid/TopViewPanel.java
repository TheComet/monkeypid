package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.Desktop;
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
	String projektName = "monkeypid";

	//Submenu Hilfreiche Links
	JMenuItem LinkWikiRegelungstechnik = new JMenuItem("Regelungstechnik Wiki",
			'R');
	JMenuItem LinkWikiFaustformelverfahren = new JMenuItem(
			"Faustformelverfahren Wiki", 'F');
	JMenuItem LinkRnWissenRegelungstechnik = new JMenuItem(
			"Regelungstechnik RnWissen", 'e');
	JMenuItem LinkPhasengangMethodePDF = new JMenuItem(
			"Phasengang-Methode.pdf", 'P');
	JMenuItem LinkRegelkreiseUndRegelungenPDF = new JMenuItem(
			"Regelkreise und Regelungen.pdf", 'R');
	JMenuItem LinkPidEinstellenPDF = new JMenuItem("pid-einstellen.pdf", 'p');
	JMenuItem LinkBuergieSolenickiV3PDF = new JMenuItem(
			"Buergi_Solenicki-V3.pdf", 'P');

	//Menueintraege
	JMenuItem menuItemExit = new JMenuItem("Exit");
	JMenuItem menuItemInfo = new JMenuItem("Info");

	/**
	 * 
	 * @param controller
	 */
	public TopViewPanel(Controller controller, Model model) {
		super(new GridBagLayout());

		this.controller = controller;
		this.model = model;

		// TODO
		// Registriert die Eintraege des Submenu Hilfreiche Links beim
		// ActionListener
		LinkWikiRegelungstechnik.addActionListener(this);
		LinkWikiFaustformelverfahren.addActionListener(this);
		LinkRnWissenRegelungstechnik.addActionListener(this);
		LinkPhasengangMethodePDF.addActionListener(this);
		LinkRegelkreiseUndRegelungenPDF.addActionListener(this);
		LinkPidEinstellenPDF.addActionListener(this);
		LinkBuergieSolenickiV3PDF.addActionListener(this);
		menuItemExit.addActionListener(this);
		menuItemInfo.addActionListener(this);

		LeftPanel leftPanel = new LeftPanel(controller); // LeftPanel
		GraphDisplayPanel graphDisplayPanel = new GraphDisplayPanel(controller);
		GraphPanel graphPanel = new GraphPanel(controller);

		// MenuBar
		JMenuBar menuBar = new JMenuBar();

		// Menu "Datei" erstellen
		JMenu menuDatei = new JMenu("Datei");
		JMenuItem eintragMiniVersion = new JMenuItem("Mini-Version");
		menuDatei.add(eintragMiniVersion);

		menuDatei.add(menuItemExit);

		menuBar.add(menuDatei); // Menu Datei der MenuBar hinzufuegen

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

		// MenuBar hinzufuegen
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

	/**
	 * 
	 */

	// TODO josua exception a???
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == LinkWikiRegelungstechnik) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://de.wikipedia.org/wiki/Regler").toURI());
			} catch (Exception a) {
			}
		}
		if (e.getSource() == LinkWikiFaustformelverfahren) {
			try {
				Desktop.getDesktop()
						.browse(new URL(
								"http://de.wikipedia.org/wiki/Faustformelverfahren_%28Automatisierungstechnik%29")
								.toURI());
			} catch (Exception a) {
			}
		}
		if (e.getSource() == LinkRnWissenRegelungstechnik) {
			try {
				Desktop.getDesktop()
						.browse(new URL(
								"http://rn-wissen.de/wiki/index.php/Regelungstechnik")
								.toURI());
			} catch (Exception a) {
			}
		}
		if (e.getSource() == LinkPhasengangMethodePDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/rt_phasengang-methode.pdf").toURI());
			} catch (Exception a) {
			}
		}
		if (e.getSource() == LinkRegelkreiseUndRegelungenPDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/Regelkreise_und_Regelungen.pdf").toURI());
			} catch (Exception a) {
			}
		}
		if (e.getSource() == LinkPidEinstellenPDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/pid-einstellregeln.pdf").toURI());
			} catch (Exception a) {
			}
		}
		if (e.getSource() == LinkBuergieSolenickiV3PDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/Buergi_Solenicki-V3.pdf").toURI());
			} catch (Exception a) {
			}
		}

		if (e.getSource() == menuItemExit) {
			System.exit(1); // beendet das Tool und schliesst das Fenster
		}
		if (e.getSource() == menuItemInfo) {
			JOptionPane
					.showMessageDialog(
							this,
							"Titel\nVersion: 1.0\n\nFHNW Brugg Windisch\nProjekt 2 Team 3\nYanick Frei\nSimon Wyss\nSimonSturm\nJosua Stierli\nAlex Murray",
							"Info", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void update(Observable observable, Object o) {

	}
}
