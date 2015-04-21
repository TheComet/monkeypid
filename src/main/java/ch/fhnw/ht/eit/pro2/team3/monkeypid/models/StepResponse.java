package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.old.Assets;

import org.jfree.data.xy.XYSeries;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class StepResponse {

    public static XYSeries exampleCalculate() {

        // load XY points from disk
        ArrayList<Double> time = new ArrayList<>();
        ArrayList<Double> response = new ArrayList<>();
        try {
            // reads the data into a 2D array "data"
            ArrayList<ArrayList<Double>> data = new ArrayList<>();
            URL url = Assets.get().getResourceURL("curves/example_step_response_pid");
            String path = url.getPath();
            Stream<String> lines = Files.lines(Paths.get(path));
            lines.forEach(s -> {
                ArrayList<Double> row = new ArrayList<>();
                for(String str : s.split("\t")) {
                    row.add(Double.parseDouble(str));
                }
                data.add(row);
            });

            // extract the relevant rows
            time = data.get(0);
            response = data.get(1);
        } catch(IOException e) {
            System.out.println("Failed to load matlab table: " + e.getMessage());
            e.printStackTrace();
        }

        // construct XY dataset from the loaded data
        XYSeries series = new XYSeries("PID step response");
        for(int i = 0; i < time.size(); i++) {
            series.add(time.get(i), response.get(i));
        }

        return series;
    }
}