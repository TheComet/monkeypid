package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.Assets;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author Alex Murray
 */
public class SaniCurves {
    private static SaniCurves instance = null;
    private ArrayList<ArrayList<Double>> Tu_Tg_ratio;
    private ArrayList<ArrayList<Double>> Tg_inverse;

    /**
     * Loads
     */
    protected SaniCurves() {
        Tu_Tg_ratio = new ArrayList<>();
        Tg_inverse = new ArrayList<>();
        loadMatlabTables();
    }

    public static SaniCurves get() {
        if(instance == null) {
            instance = new SaniCurves();
        }
        return instance;
    }

    /**
     * Loads both matlab tables Tu/Tg and 1/Tg.
     *
     * The tables *should* contain 6 rows worth of data points, each row corresponding to
     * a power. The powers start at n=2 and increment up to n=8. Therefore, the first
     * element in the array of rows is power 2, and the last is power 8.
     */
    private void loadMatlabTables() {
        // load data from tables exported from matlab
        // Tu/Tg
        try {
            Path path = Paths.get(Assets.get().getResourceURL("math_tables/tu_tg_ratio").getPath());
            Stream<String> lines = Files.lines(path);
            lines.forEach(s -> {
                ArrayList<Double> row = new ArrayList<>();
                Tu_Tg_ratio.add(row);
                for(String str : s.split("\t")) {
                    row.add(Double.parseDouble(str));
                }
            });
        } catch(IOException e) {
            System.out.println("Failed to load matlab table: " + e.getMessage());
            e.printStackTrace();
        }

        // 1/Tg
        try {
            Path path = Paths.get(Assets.get().getResourceURL("math_tables/tg_inverse").getPath());
            Stream<String> lines = Files.lines(path);
            lines.forEach(s -> {
                ArrayList<Double> row = new ArrayList<>();
                Tg_inverse.add(row);
                for(String str : s.split("\t")) {
                    row.add(Double.parseDouble(str));
                }
            });
        } catch (IOException e) {
            System.out.println("Failed to load matlab table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public double lookupTuTgValue(double inputTuTgRatio) {

        // These are magic numbers extracted from the matlab file "p2_sani.m", based on
        // the document "Buergi, Solenicki - V3.pdf", page 5.
        double[] orderThresholds = {
                0.103638,
                0.218017,
                0.319357,
                0.410303,
                0.4933,
                0.5700,
                0.64173
        };

        // Find the order of the function based on the ratio of Tu to Tg.
        // order of 1 is invalid. Orders 2 through 8 are decided depending on the
        // magic numbers compared to the ratio. If the ratio is larger than all of
        // the magic numbers, then throw an exception.
        int power = 2;
        while(inputTuTgRatio > orderThresholds[power - 2]) {
            power++;

            // ratio is larger than all thresholds
            if(power - 2 >= orderThresholds.length) {
                throw new RuntimeException("Order is larger than 8, don't have a lookup table for that");
            }
        }

        for(int index = 0; index < getTuTgRatioCurve(power).size(); index++) {
            if(getTuTgRatioCurve(power).get(index) >= inputTuTgRatio)
                return (double)index / (double)getTuTgRatioCurve(power).size();
        }

        throw new RuntimeException("Failed to lookup Tu/Tg in sani curve: It doesn't intersect!");
    }

    public ArrayList<Double> getTuTgRatioCurve(int power) {
        return Tu_Tg_ratio.get(power - 2);
    }

    public ArrayList<Double> getTgInverseCurve(int power) {
        return Tg_inverse.get(power - 2);
    }
}
