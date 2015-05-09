package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.Assets;

import javax.swing.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * GraphDisplayPanel is a panel which includes checkBoxes with names of all
 * simulated graphs. If a checkBox is set the appendant graph is displayed else
 * it isn't displayed anymore.
 * 
 * @author Josua
 *
 */
public class GraphDisplayPanel extends JPanel implements ActionListener,
		IModelListener {

	private Controller controller;
	private ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

	private class CheckBoxNotFoundException extends Exception {
		public CheckBoxNotFoundException(String message) {
			super(message);
		}
	}

	/**
	 * Constructor of GraphDisplayPanel adds
	 */
	public GraphDisplayPanel(Controller controller) {
		// super(new FlowLayout(FlowLayout.LEADING));
		super(new WrapLayout(WrapLayout.LEFT));
		this.controller = controller;
	}


    private JCheckBox findCheckBox(String name) throws CheckBoxNotFoundException {
        for(JCheckBox c : checkBoxes) {
            if(c.getText().equals(name)) {
                return c;
            }
        }
        throw new CheckBoxNotFoundException("Checkbox with name \"" + name + "\" not found");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        for(JCheckBox c : checkBoxes) {
            if(actionEvent.getSource() == c) {
                if(c.isSelected()) {
                    controller.cbCheckAction(c.getText());
                } else {
                    controller.cbUncheckAction(c.getText());
                }
            }
        }
    }

	@Override
	public void onAddCalculation(ClosedLoop closedLoop) {
		/*
		@Override
		public void onAddClosedLoop(ClosedLoop closedLoop) {
		//get rgbColor from closedLoop and convert it to string
		String hexColor = String.format("#%02x%02x%02x", closedLoop.getColor()
				.getRed(), closedLoop.getColor().getGreen(), closedLoop
				.getColor().getBlue());
		//create checkBox with colored dot and name of closedLoop
		JCheckBox cb = new JCheckBox(
				"<html><font style=\"font-family: unicode \"color=" + hexColor
						+ ">" + "\u25CF" + "<font color=#000000>"
						+ closedLoop.getName(), true);
		//add actionListener to checkbox
		cb.addActionListener(this);
		
		checkBoxes.add(cb);
		add(cb);
		}
		 */
        SwingUtilities.invokeLater(() -> {
            JCheckBox cb = new JCheckBox(closedLoop.getName(), true);
            cb.addActionListener(this);
            checkBoxes.add(cb);
            add(cb);
        });
	}

	@Override
	public void onRemoveCalculation(ClosedLoop closedLoop) {
        SwingUtilities.invokeLater(() -> {
            try {
                JCheckBox c = findCheckBox(closedLoop.getName());
                checkBoxes.remove(c);
                remove(c);
            } catch (CheckBoxNotFoundException e) {
                System.out.println(e.getMessage());
            }
        });
	}

    @Override
    public void onSimulationBegin(int numberOfStepResponses) {
        SwingUtilities.invokeLater(() -> {
            checkBoxes.forEach(this::remove);
            checkBoxes.clear();
        });
    }

	@Override
	public void onSimulationComplete() {
	}

    @Override
    public void onHideCalculation(ClosedLoop closedLoop) {}

    @Override
    public void onShowCalculation(ClosedLoop closedLoop) {}
}