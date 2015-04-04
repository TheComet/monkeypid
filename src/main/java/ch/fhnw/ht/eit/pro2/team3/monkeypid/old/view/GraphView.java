package ch.fhnw.ht.eit.pro2.team3.monkeypid.old.view;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.Assets;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * @author Alex Murray
 */
public class GraphView {
    private Parent root;

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(Assets.get().getResourceURL("ui/graph.fxml"));
        root = loader.load();
    }

    public Parent getRoot() {
        return root;
    }
}