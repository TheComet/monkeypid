package ch.fhnw.ht.eit.pro2.team3.monkeypid.old.controllers;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.old.interfaces.MathBlockInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alex Murray
 */
public class MainWindowController implements Initializable {
    @FXML private LineChart<Number, Number> lineChartPIDStepResponse;
    @FXML private GridPane mainPane;
    @FXML private GridPane inputPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public GridPane getMainPane() {
        return mainPane;
    }

    public GridPane getInputPane() {
        return inputPane;
    }

    public void doPIDStepResponseCalculations() {
        // delete all existing series
        lineChartPIDStepResponse.getData().clear();

        double numPoints = 1000;
        double endTime = 10;

        double ks = 2;
        double tu = 1.25;
        double tg = 6.95;

        // faustregel 20%
        // http://de.wikipedia.org/wiki/Faustformelverfahren_%28Automatisierungstechnik%29
        double kp = 0.95 * tg / (tu * tg);
        double ki = 1.35 * tu;
        double kd = 0.47 * tu;


    }
}
