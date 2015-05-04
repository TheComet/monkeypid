package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author Alex Murray
 */
public class Assets {

    private static String RESOURCE_PREFIX =
            pathToPlatform("src/main/resources/ch/fhnw/ht/eit/pro2/team3/monkeypid");

    public static String pathToPlatform(String path) {
        return String.join(File.separator, path.split("/")) + File.separator;
    }

    public static ArrayList<PolynomialSplineFunction> loadSaniCurves(String fileName, boolean swapXY) {

        ArrayList<PolynomialSplineFunction> listOfSplines = new ArrayList<>();

        // load the data points from disk
        try {
            //Path path = Paths.get(Assets.get().getResourceURL(fileName).getPath());
            // TODO get classpath working
            Path path = Paths.get(RESOURCE_PREFIX + fileName);
            Stream<String> lines = Files.lines(path);
            lines.forEach(s -> {

                // load the data points into an XYSeries object and add that to the
                // list of XYSeries objects
                String[] yValuesStr = s.split("\t");
                double[] xValues = new double[yValuesStr.length];
                double[] yValues = new double[yValuesStr.length];
                for(int i = 0; i < yValuesStr.length; i++) {
                    xValues[i] = (double)i / (double)(yValuesStr.length - 1);
                    yValues[i] = Double.parseDouble(yValuesStr[i]);
                }

                // apply cubic interpolation to the data points and store curve into return value
                // for Tu_Tg the x and y data points are swapped
                LinearInterpolator interpolator = new LinearInterpolator();
                if(swapXY)
                    listOfSplines.add(interpolator.interpolate(yValues, xValues));
                else
                    listOfSplines.add(interpolator.interpolate(xValues, yValues));
            });
        } catch(IOException e) {
            System.out.println("Failed to load matlab table: " + e.getMessage());
            e.printStackTrace();
        }

        return listOfSplines;
    }
}
