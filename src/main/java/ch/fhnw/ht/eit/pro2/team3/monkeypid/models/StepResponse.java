package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.old.Assets;
import org.jfree.data.xy.XYSeries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class StepResponse {

    // TODO Remove
    // This is for the presentation on Wednesday only, and serves as a demonstration of how
    // it will look in the end
    public static XYSeries exampleCalculate() {

        // load XY points from disk
        ArrayList<Double> time = new ArrayList<>();
        ArrayList<Double> response = new ArrayList<>();
        try {
            // reads the data into a 2D array "data"
            ArrayList<ArrayList<Double>> data = new ArrayList<>();
            Path path = Paths.get(Assets.get().getResourceURL("curves/example_step_response_pid").getPath());
            Stream<String> lines = Files.lines(path);
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