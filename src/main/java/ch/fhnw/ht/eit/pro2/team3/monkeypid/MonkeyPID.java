package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.Dimension;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.services.Assets;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.MenuBar;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.StatusBar;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.View;
import de.javasoft.plaf.synthetica.*;

import javax.swing.*;

/**
 * 
 * @author Josua
 *
 */
public class MonkeyPID {

	public void setLookAndFeel() {
		try {
            // TODO @Stierli - Kommentier s züg ih und us, jenachdem welles theme du willsch.
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

	public JFrame createFrame() {
		JFrame frame = new JFrame();
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Easy-PID Smart Controller Design");
		frame.setIconImage(Assets.loadImageIcon("logo.png").getImage());

		return frame;
	}

	public void createMVC(JFrame frame) {

		// typical MVC pattern
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(controller);

		model.registerListener(view.outputPanel);
        model.registerListener(view.graphPanel);
        model.registerListener(view.graphDisplayPanel);

		// add the view to the root pane
		frame.getContentPane().add(view);

		// add a menu bar to the frame
		MenuBar menuBar = new MenuBar(controller, view);
		frame.setJMenuBar(menuBar);

		// add statusBar to window
		StatusBar statusBar = new StatusBar();
		//frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		//frame.setMinimumSize(new Dimension(1200, 900));
		
	}

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

	public static void main(String args[]) {
		MonkeyPID app = new MonkeyPID();
		app.go();
	}
}