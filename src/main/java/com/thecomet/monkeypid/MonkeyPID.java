package com.thecomet.monkeypid;

import com.thecomet.monkeypid.view.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MonkeyPID extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // load main window
        MainWindow mainWindow = new MainWindow();
        mainWindow.initialise();

        Scene scene = new Scene(mainWindow.getRoot(), 300, 275);
        stage.setTitle("Monkey PID");
        stage.setScene(scene);
        stage.show();
    }
}
