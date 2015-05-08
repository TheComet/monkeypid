package ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.OverswingValueTuple;

/**
 * 
 * @author Josua
 *
 */
public class Controller {
	private Model model;

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
                                 OverswingValueTuple overswing){

        // issue #31 - Don't allow
		model.setRegulatorType(selectedRegulatorName);
        model.setPlant(tuValue, tgValue, ksValue);
        model.setParasiticTimeConstantFactor(tpValue);
		model.setPhaseMargin(overswing.angle());
        model.simulateAll();
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

    public void cbCheckAction(String closedLoopName) {
        model.selectSimulation(closedLoopName);
        model.showSelectedSimulation();
    }

    public void cbUncheckAction(String closedLoopName) {
        model.selectSimulation(closedLoopName);
        model.hideSelectedSimulation();
    }
}
