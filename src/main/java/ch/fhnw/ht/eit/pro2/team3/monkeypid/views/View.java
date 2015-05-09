package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.ModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.Assets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;

/**
 * 
 * @author Josua
 *
 */
public class View extends JPanel implements ActionListener {

	private Controller controller;

	public InputPanel inputPanel;
	public OutputPanel outputPanel;
	public GraphDisplayPanel graphDisplayPanel;
	public GraphPanel graphPanel;

	public JButton btAutoAdjust = new JButton(
			Assets.loadImageIcon("autoSizeIcon.png"));

	/**
	 * The constructor from view adds the panel leftPanel, graphPanel and
	 * graphDisplayPanel with a GridBagLayout to the view panel. For each panel
	 * a border with a title will be added.
	 * 
	 * @param controller
	 */
	public View(Controller controller) {
		super(new GridBagLayout());

		this.controller = controller;

		// create the panels
		inputPanel = new InputPanel(controller);
		outputPanel = new OutputPanel(controller);
		graphDisplayPanel = new GraphDisplayPanel(controller);
		graphPanel = new GraphPanel();

		// set border for input and output panel
		inputPanel.setBorder(new TitledBorder(null, "Eingabe"));
		outputPanel.setBorder(new TitledBorder(null, "Ausgabe"));

		// add input and output panel into a new panel
		JPanel inputOutputPanel = new JPanel();
		inputOutputPanel.setLayout(new GridBagLayout());
		inputOutputPanel.add(inputPanel, new GridBagConstraints(0, 0, 1, 1,
				1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
		inputOutputPanel.add(outputPanel, new GridBagConstraints(0, 1, 1, 1,
				0.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

		// add inputOutputPanel to GridBagLayout
		add(inputOutputPanel, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
				new Insets(10, 10, 10, 10), 0, 0));

		// add graphPanel to GridBagLayout
		add(graphPanel, new GridBagConstraints(1, 0, 2, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH,
				new Insets(10, 0, 5, 10), 0, 0));
		// set border and title of graphPanel
		graphPanel.setBorder(new TitledBorder((null), "Graph"));

		// add graphDisplayPanel to GridBagLayout
		add(graphDisplayPanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.LAST_LINE_START, GridBagConstraints.BOTH,
				new Insets(0, 0, 10, 0), 0, 0));
		// set border and title of graphDisplayPanel
		graphDisplayPanel
				.setBorder(new TitledBorder((null), "Ein-/Ausblenden"));

		//
		JPanel graphSettingPanel = new JPanel();

		btAutoAdjust.setMargin(new Insets(0, 0, 0, 0));
		graphSettingPanel.add(btAutoAdjust);

		btAutoAdjust.addActionListener(this);
		
		//
		add(graphSettingPanel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_END, GridBagConstraints.VERTICAL,
				new Insets(0, 10, 10, 10), 0, 0));
		// set border
		graphSettingPanel.setBorder(new TitledBorder((null), "Einstellungen"));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAutoAdjust) {
			graphPanel.autoScaleAxis();
		}
	}
}
