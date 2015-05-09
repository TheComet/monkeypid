package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.awt.MediaTracker;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javafx.scene.image.Image;

import javax.swing.ImageIcon;

/**
 * @author Alex Murray
 */
public class Assets {

    private static final String RESOURCE_PREFIX =
            pathToPlatform("src/main/resources/ch/fhnw/ht/eit/pro2/team3/monkeypid");

    public static String pathToPlatform(String path) {
        return String.join(File.separator, path.split("/")) + File.separator;
    }
    
    public static ImageIcon loadImageIconInfo(){
      	ImageIcon icon = new ImageIcon( RESOURCE_PREFIX + "pictures/about.png");
    	return icon;
    }
    
   public static ImageIcon loadImageIcon(String name){
	   ImageIcon icon = new ImageIcon(RESOURCE_PREFIX + "pictures/"+name);
	   return icon;
    }

    public static ArrayList<double[]> loadMatlabTable(String fileName) {

        // the table is stored as rows of strings
        ArrayList<double[]> table = new ArrayList<>();
        try {
            //Path path = Paths.get(Assets.get().getResourceURL(fileName).getPath());
            // TODO get classpath working
            Path path = Paths.get(RESOURCE_PREFIX + fileName);
            Stream<String> lines = Files.lines(path);
            lines.forEach(s -> {

                // each element in the row is stored as tab separated strings
                String[] rowStrings = s.split("\t");
                double[] rowValues = new double[rowStrings.length];
                table.add(rowValues);
                for(int i = 0; i < rowStrings.length; i++) {
                    rowValues[i] = Double.parseDouble(rowStrings[i]);
                }
            });
        } catch(IOException e) {
            System.out.println("Failed to load matlab table: " + e.getMessage());
            e.printStackTrace();
        }

        return table;
    }
}