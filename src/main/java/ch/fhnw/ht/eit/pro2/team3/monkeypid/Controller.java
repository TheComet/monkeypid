package ch.fhnw.ht.eit.pro2.team3.monkeypid;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller {

	private Model model;

	public Controller(Model model) {
		this.model = model;
	}

	public void bt2(ActionEvent e) {
		StatusBar.showStatus(this, e, "Button2");		
	}
}
