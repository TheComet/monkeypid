package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import javax.swing.*;


public class MonkeyPID {

    public static void main(String[] args) {
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

        // initialise MVC stuff
        Model model = new Model();
        Controller controller = new Controller(model);
        TopViewPanel view = new TopViewPanel(controller, model);
        controller.setView(view);
        model.addObserver(view);

        frame.getContentPane().add(view);
        frame.setVisible(true);

        /*
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
        app.setTitle("Monkey PID");*/
    }

    // TODO Josua Stierli - Remove
    /*
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
                //frame.getContentPane().add(new TopViewPanel(null));

                Model model = new Model();
                Controller controller = new Controller(model);
                TopViewPanel view = new TopViewPanel(controller);
                controller.setView(view);
                //model.addObserver(view);


                frame.pack();
                frame.setVisible(true);
            }
        });
    }*/
}