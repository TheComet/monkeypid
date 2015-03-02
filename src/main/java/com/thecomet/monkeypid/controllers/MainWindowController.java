package com.thecomet.monkeypid.controllers;

import com.thecomet.monkeypid.interfaces.MathBlock;
import com.thecomet.monkeypid.interfaces.MathBlockInterface;
import com.thecomet.monkeypid.models.MathChainFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alex Murray
 */
public class MainWindowController implements Initializable {
    @FXML private LineChart<Number, Number> lineChart;

    @FXML
    protected void handleTestButtonAction(ActionEvent event) {
        System.out.println("hello!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<Number, Number> series = new XYChart.Series();
        double timeStep = 1.0;
        MathBlockInterface chain = MathChainFactory.testChain();

        for(double time = 0.0; time < 100; time += timeStep) {
            series.getData().add(new XYChart.Data(time, chain.stepAll(1, timeStep)));
        }
        lineChart.getData().add(series);
    }
}
