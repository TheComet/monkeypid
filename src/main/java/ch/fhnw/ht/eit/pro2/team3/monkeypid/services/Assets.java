package ch.fhnw.ht.eit.pro2.team3.monkeypid.services;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Manages the loading of various assets used throughout the application. All methods are static, so the Assets class
 * doesn't need to be instantiated.
 * @author Alex Murray
 */
public class Assets {

    private static final String RESOURCE_PREFIX =
            pathToPlatform("src/main/resources/ch/fhnw/ht/eit/pro2/team3/monkeypid");

    /**
     * Converts a path containing forward slashes to a platform specific path.
     * @param path The path to convert.
     * @return The converted path.
     */
    public static String pathToPlatform(String path) {
        return String.join(File.separator, path.split("/")) + File.separator;
    }

    /**
     * Loads the "about" icon image.
     * @return The about icon image.
     */
    public static ImageIcon loadImageIconInfo(){
      	return loadImageIcon("about.png");
    }


    /**
     * Loads an image icon from the "pictures" resource folder.
     * @param name The name of the image to load.
     * @return Loaded image.
     */
   public static ImageIcon loadImageIcon(String name){
       return new ImageIcon(RESOURCE_PREFIX + "pictures/" + name);
   }

    /**
     * Loads an exported matlab table into a 2D array of doubles. Each value in each row should be separated by a tab.
     * @param fileName The file to load.
     * @return Returns an array list of double arrays containing the values from the table.
     */
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