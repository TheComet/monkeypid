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
public class MenuBar extends JMenuBar implements ActionListener {
	private Controller controller;
	private View view;

	// Projektname fuer Link-Pfad
	private String projektName = "monkeypid";

	// TODO besserer Name
	private boolean miniVersionSelected = false;

	// Menu Datei
	private JMenu menuData = new JMenu("Datei");

	// Menueintraege Menu Datei
	private JMenuItem menuItemMiniVersion = new JMenuItem(
			"Zur Mini-Version wechseln");
	private JMenuItem menuItemExit = new JMenuItem("Exit");

	// Menu Hilfe
	private JMenu menuHelp = new JMenu("Help");

	// Menueintraege Menu Hilfe
	private JMenuItem menuItemInfo = new JMenuItem("Info");

	// Submenu MenuDatei Hilfreiche Links
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

	/**
	 * 
	 * @param controller
	 */
	public MenuBar(Controller controller, View view) {
		this.controller = controller;
		this.view = view;

		// Registriert die Eintraege des Submenu Hilfreiche Links beim
		// ActionListener
		LinkWikiRegelungstechnik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://de.wikipedia.org/wiki/Regler");
			}
		});

		LinkWikiFaustformelverfahren.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://de.wikipedia.org/wiki/Faustformelverfahren_%28Automatisierungstechnik%29");
			}
		});
		LinkRnWissenRegelungstechnik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://rn-wissen.de/wiki/index.php/Regelungstechnik");
			}
		});
		LinkPhasengangMethodePDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://simonwyss.me/" + projektName
						+ "/rt_phasengang-methode.pdf");
			}
		});
		LinkRegelkreiseUndRegelungenPDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://simonwyss.me/" + projektName
						+ "/Regelkreise_und_Regelungen.pdf");
			}
		});
		LinkPidEinstellenPDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://simonwyss.me/" + projektName
						+ "/pid-einstellregeln.pdf");
			}
		});
		LinkBuergieSolenickiV3PDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://simonwyss.me/" + projektName
						+ "/Buergi_Solenicki-V3.pdf");
			}
		});
		menuItemExit.addActionListener(this);
		menuItemInfo.addActionListener(this);
		menuItemMiniVersion.addActionListener(this);

		// Menueintraege dem Menu Datei hinzufuegen
		menuData.add(menuItemMiniVersion);
		menuData.add(menuItemExit);

		// Submenu Hilfreiche Links
		JMenu usefulLinksSubMenu = new JMenu("Hilfreiche Links");
		usefulLinksSubMenu.setMnemonic('H');
		usefulLinksSubMenu.add(LinkWikiRegelungstechnik);
		usefulLinksSubMenu.add(LinkWikiFaustformelverfahren);
		usefulLinksSubMenu.add(LinkRnWissenRegelungstechnik);
		usefulLinksSubMenu.add(LinkPhasengangMethodePDF);
		usefulLinksSubMenu.add(LinkRegelkreiseUndRegelungenPDF);
		usefulLinksSubMenu.add(LinkPidEinstellenPDF);
		usefulLinksSubMenu.add(LinkBuergieSolenickiV3PDF);

		// Menueintraege dem Menu Hilfe hinzufuegen
		menuHelp.add(menuItemInfo);
		menuHelp.add(usefulLinksSubMenu);

		// Menues der MenuBar hinzuefuegen
		add(menuData);
		add(menuHelp);
	}
	
	private void openURLInBrowser(String stringURL){
		try {
			Desktop.getDesktop().browse(
					new URL(stringURL).toURI());
		} catch (Exception a) { //catch only necessary exceptions
			//a.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		if (e.getSource() == LinkWikiRegelungstechnik) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://de.wikipedia.org/wiki/Regler").toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		*/
		/*
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
		if (e.getSource() == LinkPhasengangMethodePDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/rt_phasengang-methode.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		if (e.getSource() == LinkRegelkreiseUndRegelungenPDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/Regelkreise_und_Regelungen.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		if (e.getSource() == LinkPidEinstellenPDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/pid-einstellregeln.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
		if (e.getSource() == LinkBuergieSolenickiV3PDF) {
			try {
				Desktop.getDesktop().browse(
						new URL("http://simonwyss.me/" + projektName
								+ "/Buergi_Solenicki-V3.pdf").toURI());
			} catch (Exception a) {
				a.printStackTrace(); // TODO Kommentar
			}
		}
	*/
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
<<<<<<< HEAD
		
		  if (e.getSource() == menuItemMiniVersion) {
		  
		  view.graphPanel.setVisible(miniVersionSelected); 
		  // graphPanel ein-/ausblenden 
		  view.graphDisplayPanel.setVisible(miniVersionSelected); 
		  //graphDisplayPanel ein-/ausblenden 
		  // schaltet alle unerwuenschten Komponenten auf dem leftPanel aus
		  view.leftPanel.setMiniVersion(miniVersionSelected);
		  JFrame myParent =  (JFrame) view.getTopLevelAncestor(); //get frame
		  myParent.pack(); //pack frame (make as small as possible)
		  miniVersionSelected = !miniVersionSelected; // invertiert die Zustandsvariable fuer die Ansicht 
		  } 
		 
=======

		if (e.getSource() == menuItemMiniVersion) {
			// graphPanel ein-/ausblenden
			view.graphPanel.setVisible(miniVersionSelected);
			// graphDisplayPanel ein-/ausblenden
			view.graphDisplayPanel.setVisible(miniVersionSelected);
			// schaltet alle unerwuenschten Komponenten auf dem leftPanel aus
			view.leftPanel.setMiniVersion(miniVersionSelected);

			// Aendert die bezeichnung des MenuItemMiniVersion
			if (miniVersionSelected) {
				// wenn von der Mini zur Normal-Ansicht gewechselt wird
				menuItemMiniVersion.setText("Zur Mini-Version wechseln");
			} else {
				// wenn von der Normal- zur Mini-Version gewechsetl wird
				menuItemMiniVersion.setText("Zur Normal-Version wechseln");
			}

			miniVersionSelected = !miniVersionSelected; // invertiert die
														// Zustandsvariable fuer
														// die Ansicht*/
		}
>>>>>>> b79907173cd6699f1a293c43b761de1682efdba0

	}
}
