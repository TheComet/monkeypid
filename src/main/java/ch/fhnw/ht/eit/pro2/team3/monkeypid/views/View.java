package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Josua
 *
 */
public class View extends JPanel implements Observer {

	private Controller controller;

	public LeftPanel leftPanel;
	public GraphDisplayPanel graphDisplayPanel;
	public GraphPanel graphPanel;

	/**
	 * 
	 * @param controller
	 */
	public View(Controller controller) {
		super(new GridBagLayout());

		this.controller = controller;

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
		graphDisplayPanel.setBorder(new TitledBorder((null), "Ein-/Ausblenden"));
	}

	/**
	 * 
	 */
	public void update(Observable observable, Object o) {
	}
}
