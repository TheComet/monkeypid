package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.URL;

import javax.swing.*;

/**
 * 
 * @author Josua
 *
 */
public class MenuPanel extends JPanel implements ActionListener {
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
	
	public MenuPanel(Controller controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
		
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
				GraphDisplayPanel graphDisplayPanel = new GraphDisplayPanel();
				GraphPanel graphPanel = new GraphPanel();

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

				//menuBar.setPreferredSize(new Dimension(100,50));
				// MenuBar hinzufuegen
				add(menuBar);

			}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == LinkWikiRegelungstechnik) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://de.wikipedia.org/wiki/Regler").toURI());
			} catch (Exception a) {
				a.printStackTrace(); //TODO Kommentar
			}
		}
		if (e.getSource() == LinkWikiFaustformelverfahren) {
			try {
				Desktop.getDesktop()
						.browse(new URL(
								"http://de.wikipedia.org/wiki/Faustformelverfahren_%28Automatisierungstechnik%29")
								.toURI());
			} catch (Exception a) {
				a.printStackTrace(); //TODO Kommentar
			}
		}
		if (e.getSource() == LinkRnWissenRegelungstechnik) {
			try {
				Desktop.getDesktop()
						.browse(new URL(
								"http://rn-wissen.de/wiki/index.php/Regelungstechnik")
								.toURI());
			} catch (Exception a) {
				a.printStackTrace(); //TODO Kommentar
			}
		}
		if (e.getSource() == LinkPhasengangMethodePDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/rt_phasengang-methode.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); //TODO Kommentar
			}
		}
		if (e.getSource() == LinkRegelkreiseUndRegelungenPDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/Regelkreise_und_Regelungen.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); //TODO Kommentar
			}
		}
		if (e.getSource() == LinkPidEinstellenPDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/pid-einstellregeln.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); //TODO Kommentar
			}
		}
		if (e.getSource() == LinkBuergieSolenickiV3PDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/Buergi_Solenicki-V3.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); //TODO Kommentar
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
}
