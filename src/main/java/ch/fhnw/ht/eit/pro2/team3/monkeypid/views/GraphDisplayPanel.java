package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * GraphDisplayPanel is a panel which includes checkBoxes with names of all
 * simulated graphs. If a checkBox is set the appendant graph is displayed
 * else it isn't displayed anymore.
 * @author Josua
 *
 */
public class GraphDisplayPanel extends JPanel implements IModelListener {

	/**
	 * Constructor of GraphDisplayPanel adds 
	 * 
	 * @param controller
	 */
	public GraphDisplayPanel() {
		super(new FlowLayout(FlowLayout.LEADING));
	}

	@Override
	public void onAddClosedLoop(ClosedLoop closedLoop) {
		JCheckBox cb = new JCheckBox(closedLoop.getName(), true);
		add(cb);
	}

	@Override
	public void onRemoveClosedLoop(ClosedLoop closedLoop) {
        for(Component c : getComponents()) {
            String name = closedLoop.getName();
            String checkBoxText = ((JCheckBox)c).getText(); // probably not a good idea, just ensure that only
                                                            // JCheckBox objects are added to the layout
            if(checkBoxText.compareTo(name) == 0) {
                remove(c);
                return;
            }
        }
	}

	@Override
	public void onSimulationStarted() {

	}

	@Override
	public void onSimulationComplete() {

	}
}