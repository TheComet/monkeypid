package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * GraphDisplayPanel is a panel which includes checkBoxes with names of all
 * simulated graphs. If a checkBox is set the appendant graph is displayed else
 * it isn't displayed anymore.
 * @author Josua Stierli
 */
public class GraphDisplayPanel extends JPanel implements ActionListener,
		IModelListener {

	private Controller controller;
	private HashMap<String, JCheckBox> checkBoxes = new HashMap<>();
	private View view;
	private boolean CurvesDisplayOn = true;

	/**
	 * Constructor of GraphDisplayPanel adds
	 */
	public GraphDisplayPanel(Controller controller, View view) {
		// super(new FlowLayout(FlowLayout.LEADING));
		super(new WrapLayout(WrapLayout.LEFT));
		this.controller = controller;
		this.view = view;
	}

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        for(Map.Entry<String, JCheckBox> entry : checkBoxes.entrySet()) {
            if (actionEvent.getSource() == entry.getValue()) {
                if (entry.getValue().isSelected()) {
                    controller.cbCheckAction(entry.getKey());
                } else {
                    controller.cbUncheckAction(entry.getKey());
                }
            }
        }
    }
    
    public void toggleDisplayAllCurves(){
    	if(CurvesDisplayOn){
    		CurvesDisplayOn = false;
			for(Map.Entry<String, JCheckBox> entry : checkBoxes.entrySet()) {
				controller.cbUncheckAction(entry.getKey());
				entry.getValue().setSelected(false);
			}	
		}
		else{
			CurvesDisplayOn = true;
			for(Map.Entry<String, JCheckBox> entry : checkBoxes.entrySet()) {
				controller.cbCheckAction(entry.getKey());
				entry.getValue().setSelected(true);
			}
		}
    }
    
    public void toggleDisplayFistCurves() {
    	if(CurvesDisplayOn){
    		CurvesDisplayOn = false;
			for(Map.Entry<String, JCheckBox> entry : checkBoxes.entrySet()) {
				if(entry.getKey() != "Zellweger"){
					controller.cbUncheckAction(entry.getKey());
					entry.getValue().setSelected(false);
				}
			}	
		}
		else{
			CurvesDisplayOn = true;
			for(Map.Entry<String, JCheckBox> entry : checkBoxes.entrySet()) {
				if(entry.getKey() != "Zellweger"){
					controller.cbCheckAction(entry.getKey());
					entry.getValue().setSelected(true);
				}
			}
		}
	}

	@Override
	public void onAddCalculation(ClosedLoop closedLoop) {
        SwingUtilities.invokeLater(() -> {

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

            // insert into hash map and add to layout
            checkBoxes.put(closedLoop.getName(), cb);
            add(cb);
            view.validate();
        });
	}

	@Override
	public void onRemoveCalculation(ClosedLoop closedLoop) {
        SwingUtilities.invokeLater(() -> {
            JCheckBox c = checkBoxes.remove(closedLoop.getName());
            remove(c);
        });
	}

    @Override
    public void onSimulationBegin(int numberOfStepResponses) {
        SwingUtilities.invokeLater(() -> {
            for(Map.Entry<String, JCheckBox> entry : checkBoxes.entrySet()) {
                remove(entry.getValue());
            }
            checkBoxes.clear();
        });
    }

	@Override
	public void onSimulationComplete() {}

    @Override
    public void onHideCalculation(ClosedLoop closedLoop) {}

    @Override
    public void onShowCalculation(ClosedLoop closedLoop) {}

	@Override
	public void onSetPlant(Plant plant) {}
}