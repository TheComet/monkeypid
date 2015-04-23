package ch.fhnw.ht.eit.pro2.team3.monkeypid;


import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Model;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.MenuBar;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.views.View;

import javax.swing.*;

public class MonkeyPID extends JFrame {

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
                frame.setTitle("Easy-PID Dimensionierungstool Phasengang-Methode");
                
                Model model = new Model();
                Controller controller = new Controller(model);
                View view = new View(controller, model);
                controller.setView(view);
                frame.getContentPane().add(view);
                
                //Fuegt die MenuBar dem Window hinzu
            	MenuBar menuBar = new MenuBar(controller, view);
                frame.setJMenuBar(menuBar);

                model.addObserver(view);

                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}