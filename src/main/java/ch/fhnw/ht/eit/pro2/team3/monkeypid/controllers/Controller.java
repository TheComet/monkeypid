package ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.View;

/**
 * Controller part of the MVC pattern
 * @author Josua Stierli
 */
public class Controller {
	private Model model;
	private View view;

	/**
	 * Constructor
	 * @param model The model to control.
	 */
	public Controller(Model model) {
		this.model = model;
	}
	
	/**
	 * Connects the View with the Controller for the MVC pattern.
	 * @param view The view to control.
	 */
	public void setView(View view){
		this.view = view;
	}

	/**
	 * This is called when the user clicks on simulate. The model should receive all of the required
	 * information and then simulate everything.
	 * @param ksValue The value the user entered for Ks.
	 * @param tuValue The value the user entered for Tu.
	 * @param tgValue The value the user entered for Tg.
	 * @param tpValue The value the user entered for Tp.
	 * @param selectedRegulatorName The regulator the user selected. This is passed as a string
	 *                              containing "I", "PI", or "PID".
	 * @param overshoot The selected overshoot from the dropdown.
	 */
	public void btSimulateAction(double ksValue, double tuValue, double tgValue, double tpValue,
								 String selectedRegulatorName, double overshoot){
		view.outputPanel.setSliderDefaultValue();
		model.setRegulatorType(selectedRegulatorName);
		model.setPlant(tuValue, tgValue, ksValue);
		model.setParasiticTimeConstantFactor(tpValue);
		model.setOvershoot(overshoot);
		model.simulateAll();
	}
	
	/**
	 * This is called, when the user adjusts the slider. Then the model has to update the ZellwegerController.
	 * This is done with updateZellweger()
	 * @param angleOfInflectionOffset The actual value of the slider (the offset of the phaseInflection).
	 */
	public void angleOfInflectionOffsetChanged(int angleOfInflectionOffset){
		model.updateZellweger(angleOfInflectionOffset);
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
