package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.Assets;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;


import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * MenuBar creates a menuBar with two menuItems: data and help. The menuItem
 * data includes two items. The first one is for selecting the mini- or normal
 * version. The second item is for closing the whole java application.
 * 
 * @author Josua
 *
 */
public class MenuBar extends JMenuBar implements ActionListener {
	private Controller controller;
	private View view;

	// projectname is used for link-path
	private String projektName = "easypid";

	// TODO besserer Name
	private boolean miniVersionSelected = false;

	// menu data
	private JMenu menuData = new JMenu("Datei");

	// menu item of menu data
	private JMenuItem menuItemMiniVersion = new JMenuItem(
			"Zur Mini-Version wechseln");
	private JMenuItem menuItemExit = new JMenuItem("Schliessen");
	
	private JMenuItem menuItemPDF = new JMenuItem("Export als PDF");

	// menu help
	private JMenu menuHelp = new JMenu("Hilfe");

	// menu item about
	private JMenuItem menuItemInfo = new JMenuItem("Info");

	// submenu useful links of menu data
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
	private JMenuItem LinkSaniMethodePDF = new JMenuItem(
			"Aperiodic Filter Analysis and Designe by Symbolic Computation Sani.pdf", 'S');

	/**
	 * The constructor of MenuBar adds all the items to the menuBar.
	 * 
	 * @param controller
	 */
	public MenuBar(Controller controller, View view) {
		this.controller = controller;
		this.view = view;

		// item of submenu useful links
		LinkWikiRegelungstechnik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://de.wikipedia.org/wiki/Regler");
			}
		});
		// item of submenu useful links
		LinkWikiFaustformelverfahren.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://de.wikipedia.org/wiki/Faustformelverfahren_%28Automatisierungstechnik%29");
			}
		});
		// item of submenu useful links
		LinkRnWissenRegelungstechnik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://rn-wissen.de/wiki/index.php/Regelungstechnik");
			}
		});
		// item of submenu useful links
		LinkPhasengangMethodePDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://simonwyss.me/" + projektName
						+ "/rt_phasengang-methode.pdf");
			}
		});
		// item of submenu useful links
		LinkRegelkreiseUndRegelungenPDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://simonwyss.me/" + projektName
						+ "/Regelkreise_und_Regelungen.pdf");
			}
		});
		// item of submenu useful links
		LinkPidEinstellenPDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://simonwyss.me/" + projektName
						+ "/pid-einstellregeln.pdf");
			}
		});
		// item of submenu useful links
		LinkBuergieSolenickiV3PDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://simonwyss.me/" + projektName
						+ "/Buergi_Solenicki-V3.pdf");
			}
		});
		
		LinkSaniMethodePDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openURLInBrowser("http://simonwyss.me/" + projektName
						+ "/Aperiodic_Filter_Analysis_and_Designe_by_Symbolic_Computation_Sani.pdf");
			}
		});

		// add actionListener to the menuItems
		menuItemExit.addActionListener(this);
		menuItemInfo.addActionListener(this);
		menuItemMiniVersion.addActionListener(this);
		menuItemPDF.addActionListener(this);

		// add menu item to the menu data
		menuData.add(menuItemMiniVersion);
		menuData.add(menuItemPDF);
		menuData.add(menuItemExit);

		// submenu for useful links
		JMenu usefulLinksSubMenu = new JMenu("Hilfreiche Links");
		usefulLinksSubMenu.setMnemonic('H');
		usefulLinksSubMenu.add(LinkWikiRegelungstechnik);
		usefulLinksSubMenu.add(LinkWikiFaustformelverfahren);
		usefulLinksSubMenu.add(LinkRnWissenRegelungstechnik);
		usefulLinksSubMenu.add(LinkPhasengangMethodePDF);
		usefulLinksSubMenu.add(LinkRegelkreiseUndRegelungenPDF);
		usefulLinksSubMenu.add(LinkPidEinstellenPDF);
		usefulLinksSubMenu.add(LinkBuergieSolenickiV3PDF);
		usefulLinksSubMenu.add(LinkSaniMethodePDF);

		// add menu items to the menu help
		menuHelp.add(menuItemInfo);
		menuHelp.add(usefulLinksSubMenu);

		// add menus to menuBar
		add(menuData);
		add(menuHelp);
	}

	/**
	 * Opens an URL in the standard browser.
	 * 
	 * @param stringURL
	 */
	private void openURLInBrowser(String stringURL) {
		try {
			Desktop.getDesktop().browse(new URL(stringURL).toURI());
		} catch (Exception a) { // catch only necessary exceptions
			// a.printStackTrace();
		}
	}

	/**
	 * Handles the events of the menuBar. - If menuItem exit is pressed, it
	 * closes the application - If menuItem info is selected, a dialog windows
	 * is appears with informations about the tool - if menuItem
	 * miniVersionSelected is pressed, it switch between mini- and normal-version
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// menu item exit is pressed
		if (e.getSource() == menuItemExit) {
			System.exit(1); // close pogramm
		}
		
		// menu item PDF is pressed
		if (e.getSource() == menuItemPDF) {
			//print PDF
			final String RESULT = "hello.pdf";
			FileDialog getNameBox = new FileDialog((JFrame) view.getTopLevelAncestor(), "Namen lesen", FileDialog.LOAD);
			getNameBox.show();
			String fileName = getNameBox.getFile();
			PrintFrameToPDF(fileName);
			/*
			try {
				//pdfPrinter.createPdf(fileName);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
		}

		// menu item info is pressed
		if (e.getSource() == menuItemInfo) {

			// shows dialogBox with informations about the project
			// used JLabel because icon is not center
			//JLabel label = new JLabel("");
			//label.setIcon(Assets.loadImageIconInfo());
			Image img;
			img = Assets.loadImageIconInfo().getImage();
			img = img.getScaledInstance(700, 420,Image.SCALE_SMOOTH);

			//Image img = new Image(Assets.loadImageIconInfo());
			
			ImageIcon test = new ImageIcon(img);
			
			JLabel label = new JLabel("");
			label.setIcon(test);
			
			JOptionPane.showMessageDialog(null, label, "Info",
					JOptionPane.PLAIN_MESSAGE);
		}

		// switch between normal- and mini-version
		if (e.getSource() == menuItemMiniVersion) {
			// set visibility of graphPanel
			view.graphPanel.setVisible(miniVersionSelected);
			// set visibility of graphDisplayPanel
			view.graphDisplayPanel.setVisible(miniVersionSelected);
			// set visibility of components on the inputPanel
			view.inputPanel.setMiniVersion(miniVersionSelected);
			// set visibility of components on the outputPanel
			view.outputPanel.setMiniVersion(miniVersionSelected);

			// change text of menuItem
			if (miniVersionSelected) {
				// change text, normal-version is selected
				menuItemMiniVersion.setText("Zur Mini-Version wechseln");
			} else {
				// change text, mini-version is selected
				menuItemMiniVersion.setText("Zur Normal-Version wechseln");
			}

			// pack frame
			JFrame myParent = (JFrame) view.getTopLevelAncestor(); // get frame
			myParent.pack(); // pack frame (make as small as possible)

			// invert state variable
			miniVersionSelected = !miniVersionSelected;
		}
	}
	
	
	public static class pdfPrinter {
	    	//new HelloWorld().createPdf(RESULT);
		public static void createPdf(String filename)throws IOException, DocumentException  {
	    	        // step 1
	    	        Document document = new Document();
	    	        // step 2
	    	        PdfWriter.getInstance(document, new FileOutputStream(filename));
	    	        // step 3
	    	        document.open();
	    	        // step 4
	    	        document.add(new Paragraph("Hello World!"));
	    	        // step 5
	    	        document.close();
	   }
	}
	
	public void PrintFrameToPDF(String file) {
	    try {
	        Document d = new Document();
	        PdfWriter writer = PdfWriter.getInstance(d, new FileOutputStream(file));
	        d.open();
	        
	       // JFrame myFrame = (JFrame) view.getTopLevelAncestor();
	        JPanel inputPn = view.inputPanel;

	        PdfContentByte cb = writer.getDirectContent( );
	        PdfTemplate template = cb.createTemplate(inputPn.getWidth(), inputPn.getHeight());
	        Graphics2D g2d = template.createGraphics(inputPn.getWidth(), inputPn.getHeight());
	        inputPn.print(g2d);
	        inputPn.addNotify();
	        inputPn.validate();
	        
	        /*
	        PdfContentByte cb = writer.getDirectContent();
	        PdfTemplate template = cb.createTemplate(PageSize.A4.getWidth(),PageSize.A4.getHeight());
	        cb.addTemplate(template, 0, 0);

	        Graphics2D g2d = template.createGraphics(PageSize.A4.getWidth(),PageSize.A4.getHeight());
	        g2d.scale(0.4, 0.4);

	        for(int i=0; i< view.getTopLevelAncestor().getComponents().length; i++){
	            Component c = view.getTopLevelAncestor().getComponent(i);
	            System.out.println(c);
	            if(c instanceof JLabel || c instanceof JScrollPane){
	                g2d.translate(c.getBounds().x,c.getBounds().y);
	                if(c instanceof JScrollPane){c.setBounds(0,0,(int)PageSize.A4.getWidth()*2,(int)PageSize.A4.getHeight()*2);}
	                c.paintAll(g2d);
	                c.addNotify();
	            }
	        }
	        */


	        g2d.dispose();

	        d.close();
	    } catch (Exception e) {
	        System.out.println("ERROR: " + e.toString());
	    }
	}
	
	
}