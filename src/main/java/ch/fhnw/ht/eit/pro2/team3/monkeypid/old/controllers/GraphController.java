package ch.fhnw.ht.eit.pro2.team3.monkeypid.old.controllers;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.SaniCurves;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alex Murray
 */
public class GraphController implements Initializable {

    @FXML private LineChart<Number, Number> lineChartSaniTg;
    @FXML private LineChart<Number, Number> lineChartSaniTuTg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        for(int power = 2; power <= 8; power++) {
            int index = 0;
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            lineChartSaniTuTg.getData().add(series);
            for(double value : SaniCurves.get().getTuTgRatioCurve(power)) {
                series.getData().add(new XYChart.Data<>(index, value));
                index++;
            }
        }

        for(int power = 2; power <= 8; power++) {
            int index = 0;
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            lineChartSaniTg.getData().add(series);
            for(double value : SaniCurves.get().getTgInverseCurve(power)) {
                series.getData().add(new XYChart.Data<>(index, value));
                index++;
            }
        }*/
    }
}
