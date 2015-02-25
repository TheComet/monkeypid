package com.thecomet.monkeypid.view;

import com.thecomet.monkeypid.Assets;
import com.thecomet.monkeypid.controllers.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * @author Alex Murray
 */
public class MainWindow {
    private Pane root;
    private MainWindowController controller;

    public void initialise() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        // create a root pane and add whatever is loaded from the FXML file as a child
        this.root = new Pane();
        this.root.getChildren().add(
                loader.load(Assets.get().getResourceURL("ui/main_window.fxml"))
        );
        this.controller = loader.getController();
    }

    public Parent getRoot() {
        return this.root;
    }
}
