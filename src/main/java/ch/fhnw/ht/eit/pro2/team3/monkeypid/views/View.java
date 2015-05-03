package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.MonkeyPID;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Josua
 *
 */
public class View extends JPanel implements Observer {

	private Controller controller;
	private Model model;

	public LeftPanel leftPanel;
	public GraphDisplayPanel graphDisplayPanel;
	public GraphPanel graphPanel;

	/**
	 * 
	 * @param controller
	 */
	public View(Controller controller, Model model) {
		super(new GridBagLayout());

		this.controller = controller;
		this.model = model;

		//create the panels
		leftPanel = new LeftPanel(controller);
		graphDisplayPanel = new GraphDisplayPanel();
		graphPanel = new GraphPanel();

		//add leftPanel to GridBagLayout
		add(leftPanel, new GridBagConstraints(0, 0, 1, 2, 0.0, 1.0,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.VERTICAL, new Insets(10, 10, 10, 10), 0, 0));
		//set border and title of leftPanel
		leftPanel.setBorder(new TitledBorder(null, "Einstellungen"));

		//add graphPanel to GridBagLayout
		add(graphPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						10, 0, 10, 10), 0, 0));
		//set border and title of graphPanel
		graphPanel.setBorder(new TitledBorder((null), "Graph"));

		//add graphDisplayPanel to GridBagLayout
		add(graphDisplayPanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 10, 10), 0, 0));
		//set border and title of graphDisplayPanel
		graphDisplayPanel.setBorder(new TitledBorder((null), "Graph"));
	}

	/**
	 * 
	 */
	public void update(Observable observable, Object o) {
	}
}
