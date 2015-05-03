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

    public void setLookAndFeel() {
        try {
            UIManager
                    .setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
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

        return frame;
    }

    public void createMVC(JFrame frame) {

        // typical MVC pattern
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller);
        model.addObserver(view);

        // add the view to the root pane
        frame.getContentPane().add(view);

        // add a menu bar to the frame
        MenuBar menuBar = new MenuBar(controller, view);
        frame.setJMenuBar(menuBar);

    }

	public void go() {
        SwingUtilities.invokeLater(() -> {
            setLookAndFeel();
            JFrame frame = createFrame();
            createMVC(frame);

            frame.pack();
            frame.setVisible(true);
        });
    }

	public static void main(String args[]) {
        MonkeyPID app = new MonkeyPID();
        app.go();
	}
}