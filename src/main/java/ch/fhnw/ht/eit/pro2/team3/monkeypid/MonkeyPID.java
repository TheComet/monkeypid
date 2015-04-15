package ch.fhnw.ht.eit.pro2.team3.monkeypid;


import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.MenuBar;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.View;

import javax.swing.*;

import java.awt.Dimension;

public class MonkeyPID extends JFrame {

	// TODO Josua Stierli - Remove
    /*public void init() {

        try {
            UIManager
                    .setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dimensionierungstool Phasengang-Methode");

        // initialise MVC stuff
        Model model = new Model();
        Controller controller = new Controller(model);
        TopViewPanel view = new TopViewPanel(controller, model);
        controller.setView(view);
        model.addObserver(view);
        add(view);
    }*/

    /*public static void main(String[] args) {
        MonkeyPID app = new MonkeyPID();

        // handle window close event
        app.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(1);
            }
        });

        // init app and run
        app.init();
        app.setVisible(true);
        app.setTitle("Monkey PID");
    }*/
	

    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
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
                frame.setTitle("Dimensionierungstool Phasengang-Methode");
                
                Model model = new Model();
                Controller controller = new Controller(model);
                View view = new View(controller, model);
                frame.getContentPane().add(view);
                
                //Fuegt die MenuBar dem Window hinzu
            	MenuBar menuBar = new MenuBar(controller, view);
                frame.setJMenuBar(menuBar);

                //model.addObserver(view);

                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}