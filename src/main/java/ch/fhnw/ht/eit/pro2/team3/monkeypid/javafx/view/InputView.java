package ch.fhnw.ht.eit.pro2.team3.monkeypid.view;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.Assets;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.InputController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * @author Alex Murray
 */
public class InputView {

    private Parent root;
    private InputController controller;

    void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(Assets.get().getResourceURL("ui/input.fxml"));

        root = loader.load();
        controller = loader.getController();
    }

    public Parent getRoot() {
        return root;
    }
}