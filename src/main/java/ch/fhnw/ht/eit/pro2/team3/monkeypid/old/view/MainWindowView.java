package ch.fhnw.ht.eit.pro2.team3.monkeypid.old.view;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.old.Assets;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.old.controllers.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * @author Alex Murray
 */
public class MainWindowView {
    private Pane root;

    public void initialise() throws IOException {
        FXMLLoader loader = new FXMLLoader(Assets.get().getResourceURL("ui/main_window.fxml"));

        int inputViewGridLocationX = 0;
        int inputViewGridLocationY = 0;
        int graphViewGridLocationX = 1;
        int graphViewGridLocationY = 0;

        // create a root pane and add whatever is loaded from the FXML file as a child
        root = loader.load();
        MainWindowController controller = loader.getController();

        // load input view and add to the main window's input pane
        InputView inputView = new InputView();
        inputView.initialize();
        controller.getInputPane().add(inputView.getRoot(), inputViewGridLocationX, inputViewGridLocationY);

        // load graph view and add to main window
        GraphView graphView = new GraphView();
        graphView.initialize();
        controller.getMainPane().add(graphView.getRoot(), graphViewGridLocationX, graphViewGridLocationY);

    }

    public Parent getRoot() {
        return this.root;
    }
}
