package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * GraphDisplayPanel is a panel which includes checkBoxes with names of all
 * simulated graphs. If a checkBox is set the appendant graph is displayed
 * else it isn't displayed anymore.
 * @author Josua
 *
 */
public class GraphDisplayPanel extends JPanel implements ActionListener, IModelListener {

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
		//super(new FlowLayout(FlowLayout.LEADING));
		super(new WrapLayout(WrapLayout.LEFT));
        this.controller = controller;
	}

    private JCheckBox findCheckBox(String name) throws CheckBoxNotFoundException {
        for(JCheckBox c : checkBoxes) {
            if(c.getText().compareTo(name) == 0) {
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
	public void onAddClosedLoop(ClosedLoop closedLoop) {
		JCheckBox cb = new JCheckBox(closedLoop.getName(), true);
        cb.addActionListener(this);
        checkBoxes.add(cb);
		add(cb);
	}

	@Override
	public void onRemoveClosedLoop(ClosedLoop closedLoop) {
        try {
            JCheckBox c = findCheckBox(closedLoop.getName());
            remove(c);
            checkBoxes.remove(c);
        } catch(CheckBoxNotFoundException e) {
            System.out.println(e.getMessage());
        }
	}

    @Override
    public void onSimulationBegin(int numberOfStepResponses) {}

    @Override
    public void onSimulationComplete() {}

    @Override
    public void onHideStepResponse(ClosedLoop closedLoop) {}

    @Override
    public void onShowStepResponse(ClosedLoop closedLoop) {}
}