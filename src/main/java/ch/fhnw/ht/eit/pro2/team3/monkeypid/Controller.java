package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.event.ActionEvent;

public class Controller {

	private Model model;

	public Controller(Model model) {
		this.model = model;
	}

	public void bt2(ActionEvent e) {
		StatusBar.showStatus(this, e, "Button2");		
	}
}
