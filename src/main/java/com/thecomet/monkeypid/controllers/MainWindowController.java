package com.thecomet.monkeypid.controllers;

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
        double timeStep = 0.01;
        double endTime = 1000;

        double ks = 2;
        double tu = 1.25;
        double tg = 6.95;

        // faustregel 20%
        // http://de.wikipedia.org/wiki/Faustformelverfahren_%28Automatisierungstechnik%29
        double kp = 0.95 * tg / (tu * tg);
        double ki = 1.35 * tu;
        double kd = 0.47 * tu;

        MathBlockInterface pid = MathChainFactory.pidController(kp, ki, kd);
        MathBlockInterface controlledSystem = MathChainFactory.controlledSystemPT1(ks, tu, tg);
        MathBlockInterface closedSystem = MathChainFactory.closedSystem(pid, controlledSystem);

        series.getData().add(new XYChart.Data(0, 0));
        for(double time = timeStep; time < endTime; time += timeStep) {
            double result = closedSystem.stepAll(1, timeStep);
            series.getData().add(new XYChart.Data(time, result));
        }
        lineChart.getData().add(series);
    }
}
