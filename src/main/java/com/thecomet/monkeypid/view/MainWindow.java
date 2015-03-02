package com.thecomet.monkeypid.view;

import com.thecomet.monkeypid.Assets;
import com.thecomet.monkeypid.controllers.MainWindowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alex Murray
 */
public class MainWindow {
    private Pane root;
    private MainWindowController controller;

    public void initialise() throws IOException {
        FXMLLoader loader = new FXMLLoader(Assets.get().getResourceURL("ui/main_window.fxml"));

        // create a root pane and add whatever is loaded from the FXML file as a child
        this.root = new Pane();
        this.root.getChildren().add(loader.load());
        this.controller = loader.getController();
    }

    public Parent getRoot() {
        return this.root;
    }
}
