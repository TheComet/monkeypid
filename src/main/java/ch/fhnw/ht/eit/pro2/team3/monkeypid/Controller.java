package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.event.ActionEvent;

/**
 * 
 * @author Josua
 *
 */
public class Controller {
	private Model model;
	private TopViewPanel view;

	/**
	 * 
	 * @param model
	 */
	public Controller(Model model) {
		this.model = model;
	}
	/**
	 * 
	 * 
	 */
	public void setView(TopViewPanel view) {
		this.view = view;
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
	public void btSimulateAction(double ksValue, double tuValue, double tgValue, double tpValue, String selectedRegulatorName, double overshootValue){
		System.out.println(ksValue+"\n"+tuValue+"\n"+tgValue+"\n"+tpValue+"\n"+selectedRegulatorName+"\n"+overshootValue);
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
}
