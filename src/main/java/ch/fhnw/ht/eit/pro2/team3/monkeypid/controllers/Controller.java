package ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.PhaseAndOverSwingTuple;

/**
 * Controller part of the MVC pattern
 * @author Josua Stierli
 */
public class Controller {
	private Model model;

	/**
	 * Constructor
	 * @param model The model to control.
	 */
	public Controller(Model model) {
		this.model = model;
	}

	/**
	 * This is called when the user clicks on simulate. The model should receive all of the required information and
     * then simulate everything.
	 * @param ksValue The value the user entered for Ks.
	 * @param tuValue The value the user entered for Tu.
	 * @param tgValue The value the user entered for Tg.
	 * @param tpValue The value the user entered for Tp.
	 * @param selectedRegulatorName The regulator the user selected. This is passed as a string containing "I", "PI",
     *                              or "PID".
	 * @param overswing The selected overswing from the dropdown.
	 */
	public void btSimulateAction(double ksValue, double tuValue, double tgValue, double tpValue,
                                 String selectedRegulatorName, double overswing){
		model.setRegulatorType(selectedRegulatorName);
        model.setPlant(tuValue, tgValue, ksValue);
        model.setParasiticTimeConstantFactor(tpValue);
		model.setOverswing(overswing);
        model.simulateAll();
	}
	
	public void phaseInflectionChanged(int phaseInflectionOffset){
		model.updateZellweger(phaseInflectionOffset);
	}

    /**
     * This is called when the user marks one of the show/hide checkboxes. The appropriate curve in the chart should
     * be shown.
     * @param closedLoopName The name of the curve to show.
     */
    public void cbCheckAction(String closedLoopName) {
        model.selectCalculation(closedLoopName);
        model.showSelectedCalculation();
    }

    /**
     * This is called when the user unmarks one of the show/hide checkboxes. The appropriate curve in the chart should
     * be hidden.
     * @param closedLoopName
     */
    public void cbUncheckAction(String closedLoopName) {
        model.selectCalculation(closedLoopName);
        model.hideSelectedCalculation();
    }
}
