package ch.fhnw.ht.eit.pro2.team3.monkeypid.old;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.old.view.MainWindowView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MonkeyPID extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // load main window
        MainWindowView mainWindow = new MainWindowView();
        mainWindow.initialise();

        Scene scene = new Scene(mainWindow.getRoot(), 300, 275);
        stage.setTitle("Monkey PID");
        stage.setScene(scene);
        stage.show();
    }
}
