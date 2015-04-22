package ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ControlPath;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.OverswingValue;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.StatusBar;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.View;

import java.awt.event.ActionEvent;

/**
 * 
 * @author Josua
 *
 */
public class Controller {
	private Model model;
	private View view;

	/**
	 * 
	 * @param model
	 */
	public Controller(Model model) {
		this.model = model;
	}

	/**
	 * 
	 * @param ksValue
	 * @param tuValue
	 * @param tgValue
	 * @param tpValue
	 * @param chooseRegulatorIndex
	 * @param overshootIndex
	 */
	public void btSimulateAction(double ksValue, double tuValue, double tgValue, double tpValue,
                                 String selectedRegulatorName,
                                 OverswingValue overswing){
        model.setControlPath(new ControlPath(ksValue, tuValue, tgValue));
        model.simulateAll();
		System.out.println(ksValue+"\n"+tuValue+"\n"+tgValue+"\n"+tpValue+"\n"+selectedRegulatorName+"\n"+overswing.asAngle());
	}

	/**
	 * 
	 */
	public void btDeleteAction(){
		
	}
	/**
	 * 
	 * @param slKpValue
	 * @param slTnValue
	 * @param slTvValue
	 */
	public void btAdoptAction(int slKpValue, int slTnValue, int slTvValue){
		
	}
	/**
	 * 
	 * @param e
	 */
	public void bt2(ActionEvent e) {
		StatusBar.showStatus(this, e, "Button2");
	}

	public void setView(View view) {
		this.view=view;
		
	}
}
