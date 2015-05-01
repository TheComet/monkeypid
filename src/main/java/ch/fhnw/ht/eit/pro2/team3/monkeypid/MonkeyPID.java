package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.MenuBar;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.StatusBar;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.View;

import javax.swing.*;

/**
 * 
 * @author Josua
 *
 */
public class MonkeyPID extends JFrame {

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//set look and feel
				try {
					UIManager
							.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				JFrame frame = new JFrame();
				frame.setUndecorated(true);
				frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// set title of window
				frame.setTitle("Easy-PID Smart Controller Design");

				// create Model, Controller and View
				Model model = new Model();
				Controller controller = new Controller(model);
				View view = new View(controller, model);

				controller.setView(view);
				frame.getContentPane().add(view);

				// create new menuBar and add it to the frame
				MenuBar menuBar = new MenuBar(controller, view);
				frame.setJMenuBar(menuBar);

				model.addObserver(view);

				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}