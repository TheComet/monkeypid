package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.old.Assets;

import org.jfree.data.xy.XYSeries;

<<<<<<< HEAD
import java.io.FileReader;
=======
import java.io.File;
>>>>>>> bacbd49e6181b32e6e43a35e0cb2516265fe6a65
import java.io.IOException;
import java.net.URL;
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

        double[][] array = {{0,0.500000000000000,1,1.50000000000000,2,2.50000000000000,3,3.50000000000000,4,4.50000000000000,5,5.50000000000000,6,6.50000000000000,7,7.50000000000000,8,8.50000000000000,9,9.50000000000000,10,10.5000000000000,11,11.5000000000000,12,12.5000000000000,13,13.5000000000000,14,14.5000000000000,15,15.5000000000000,16,16.5000000000000,17,17.5000000000000, 18, 18.5000000000000, 19, 19.5000000000000,20,20.5000000000000,21,21.5000000000000,22,22.5000000000000,23,23.5000000000000,24,24.5000000000000,25,25.5000000000000,26,26.5000000000000,27,27.5000000000000,28,28.5000000000000,29,29.5000000000000,30,30.5000000000000,31,31.5000000000000,32,32.5000000000000,33,33.5000000000000,34,34.5000000000000,35,35.5000000000000,36,36.5000000000000,37,37.5000000000000,38,38.5000000000000,39,39.5000000000000,40,40.5000000000000,41,41.5000000000000,42,42.5000000000000,43,43.5000000000000,44,44.5000000000000,45,45.5000000000000,46,46.5000000000000,47,47.5000000000000,48,48.5000000000000,49,49.5000000000000,50,50.5000000000000,51,51.5000000000000,52,52.5000000000000,53,53.5000000000000,54,54.5000000000000,55,55.5000000000000,56,56.5000000000000,57,57.5000000000000,58,58.5000000000000,59,59.5000000000000,60,60.5000000000000,61,61.5000000000000,62,62.5000000000000,63,63.5000000000000,64,64.5000000000000,65,65.5000000000000,66,66.5000000000000,67,67.5000000000000,68,68.5000000000000,69,69.5000000000000,70,70.5000000000000,71,71.5000000000000,72,72.5000000000000,73,73.5000000000000,74,74.5000000000000,75,75.5000000000000,76,76.5000000000000,77,77.5000000000000,78,78.5000000000000,79,79.5000000000000,80,80.5000000000000,81,81.5000000000000,82,82.5000000000000,83,83.5000000000000,84,84.5000000000000,85,85.5000000000000,86,86.5000000000000,87,87.5000000000000,88,88.5000000000000,89,89.5000000000000,90,90.5000000000000,91,91.5000000000000,92,92.5000000000000,93,93.5000000000000,94,94.5000000000000,95,95.5000000000000,96,96.5000000000000,97,97.5000000000000,98,98.5000000000000,99,99.5000000000000,100
        },{0,0,0,0.00235681766169699,0.00940576818003117,0.0208239034764471,0.0362865656239484,0.0554707733120938,0.0780565185414251,0.103727979333916,0.132174647477620,0.163092370792127,0.196184309843332,0.231161809450901,0.267745185719627,0.305664429686367,0.344659829007536,0.384482509418393,0.424894897974735,0.465671110340607,0.506597264612564,0.547471724372596,0.588105273838423,0.628321228132498,0.667955481820176,0.706856498974142,0.744885248107154,0.781915085379317,0.817831589530506,0.852532352014098,0.885926725815956,0.917935536433601,0.948490758465765,0.977535161223162,1.00502192671829,1.03091424332659,1.05518487833421,1.07781573250017,1.09879737966389,1.11812859432361,1.13581586999860,1.15187293106883,1.16632024066110,1.17918450702108,1.19049819067803,1.20029901457246,1.20862947917928,1.21553638451928,1.22107036081187,1.22528540938175,1.22823845529316,1.22998891304702,1.23059826654033,1.23012966435338,1.22864753129939,1.22621719704354,1.22290454247457,1.21877566439207,1.21389655895736,1.20833282424455,1.20214938212287,1.19541021960008,1.18817814966129,1.18051459154717,1.17247937033078,1.16413053557293,1.15552419876240,1.14671438917928,1.13775292775710,1.12868931846281,1.11957065666185,1.11044155388989,1.10134407841185,1.09231771091343,1.08339931463971,1.07462311927005,1.06602071779717,1.05762107566252,1.04945055138743,1.04153292793224,1.03388945401122,1.02653889459135,1.01949758980598,1.01277952152139,1.00639638680384,1.00035767754711,0.994670765536191,0.989340992239919,0.984371762645468,0.979764642469298,0.975519458102686,0.971634398675051,0.968106119644743,0.964929847354489,0.962099484017268,0.959607712627618,0.957446101323315,0.955605206752612,0.954074676032829,0.952843346916677,0.951899345813403,0.951230183342176,0.950822847125332,0.950663891558772,0.950739524325942,0.951035689450388,0.951538146709630,0.952232547260090,0.953104505348890,0.954139666013453,0.955323768693962,0.956642706706739,0.958082582548590,0.959629759022898,0.961270906197888,0.962993044225900,0.964783582069719,0.966630352198008,0.968521641326661,0.970446217296481,0.972393352189908,0.974352841800740,0.976315021580739,0.978270779195900,0.980211563832858,0.982129392402550,0.984016852793842,0.985867104334334,0.987673875619182,0.989431459871356,0.991134707998501,0.992779019512439,0.994360331477404,0.995875105652418,0.997320313991781,0.998693422665600,0.999992374759558,1.00121557180988,1.00236185432565,1.00343048144638,1.00442110987807,1.00533377224588,1.00616885499619,1.00692707597510,1.00760946180451,1.00821732517067,1.00875224213407,1.00921602956266,1.00961072278425,1.00993855354721,1.01020192837191,1.01040340736884,1.01054568359284,1.01063156299617,1.01066394503702,1.01064580399355,1.01058017102752,1.01047011703560,1.01031873632052,1.01012913110869,1.00990439693560,1.00964760891480,1.00936180890169,1.00904999355835,1.00871510332124,1.00836001226944,1.00798751888698,1.00760033770931,1.00720109184038,1.00679230632348,1.00637640234653,1.00595569225927,1.00553237537785,1.00510853454990,1.00468613345141,1.00426701458502,1.00385289794789,1.00344538033614,1.00304593525192,1.00265591337827,1.00227654358655,1.00190893444071,1.00155407616254,1.00121284302202,1.00088599611708,1.00057418650734,1.00027795866688,0.999997754221673,0.999733915937957,0.999486691928838,0.999256240047051,0.999042632433079,0.998845860188755,0.998665838147739,0.998502409715441,0.998355351752262,0.998224379475343,0.998109151355397,0.998009273986563,0.997924306908649,0.997853767362539}};

        double[][] x = {{1, 2, 3}, {4, 5, 6}};

        for(int i = 0; i < x.length; i++) {
            System.out.println("" + x[0][i]);
        }

        /* load XY points from disk
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
        }*/

        XYSeries series = new XYSeries("PID step response");
        for(int i = 0; i < array[0].length; i++) {
            series.add(array[0][i], array[1][i]);
        }

        return series;
    }
}