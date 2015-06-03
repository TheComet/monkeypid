package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.Assets;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.MenuBar;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.View;
import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;

import javax.swing.*;

/**
 * The main class. Sets up the MVC pattern, registers listeners, sets the LookAndFeel... You get the idea.
 * @author Alex Murray
 */
public class MonkeyPID {

	/**
	 * Sets the LookAndFeel of the application.
	 */
	public void setLookAndFeel() {
		try {
            // TODO @Stierli - Kommentier s zü
            //
            // g ih und us, jenachdem welles theme du willsch.
            // UIManager.setLookAndFeel(SyntheticaAluOxideLookAndFeel.class.getName());
            // UIManager.setLookAndFeel(SyntheticaBlueSteelLookAndFeel.class.getName());
            //UIManager.setLookAndFeel(SyntheticaSilverMoonLookAndFeel.class.getName());
            UIManager.setLookAndFeel(SyntheticaSimple2DLookAndFeel.class.getName());
            // UIManager.setLookAndFeel(SyntheticaSkyMetallicLookAndFeel.class.getName());
            //UIManager.setLookAndeel(SyntheticaWhiteVisionLookAndFeel.class.getName());
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Creates the root frame for the application window.
	 * @return Returns the frame.
	 */
	public JFrame createFrame() {
		JFrame frame = new JFrame();
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Easy-PID Smart Controller Design");
		frame.setIconImage(Assets.loadImageIcon("logo.png").getImage());

		return frame;
	}

	/**
	 * Creates the model, view, and controller, and registers all listeners.
	 * @param frame The frame to add the view to.
	 */
	public void createMVC(JFrame frame) {

		// typical MVC pattern
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(controller);
		controller.setView(view);

		model.registerListener(view.outputPanel);
        model.registerListener(view.graphPanel);
        model.registerListener(view.graphDisplayPanel);
        model.registerListener(view.inputPanel);

		// add the view to the root pane
		frame.getContentPane().add(view);
		//sets the Simulation-Button as Default Button
		view.inputPanel.setDefaultButtonSimulation();
		
		// add a menu bar to the frame
		MenuBar menuBar = new MenuBar(controller, view);
		frame.setJMenuBar(menuBar);
	}

	/**
	 * Swing application entry point.
	 */
	public void go() {
		SwingUtilities.invokeLater(() -> {
			setLookAndFeel();
			JFrame frame = createFrame();
			createMVC(frame);
			
			frame.pack();
			frame.setMinimumSize(frame.getPreferredSize());
			frame.setVisible(true);
		});
	}

	/**
	 * Main method.
	 * @param args Command line arguments.
	 */
	public static void main(String args[]) {
		MonkeyPID app = new MonkeyPID();
		app.go();
	}
}