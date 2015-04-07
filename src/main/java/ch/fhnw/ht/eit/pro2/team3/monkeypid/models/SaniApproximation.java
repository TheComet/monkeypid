package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.util.ArrayList;

/**
 * @author Alex Murray
 */
public class SaniApproximation {

    private static SaniApproximation instance = null;

    protected SaniApproximation() {

        // load sani curves from disk

    }

    public static SaniApproximation get() {
        if(instance == null) {
            instance = new SaniApproximation();
        }
        return instance;
    }

    /**
     * Returns a list of time constants which can be fed into the Zellweger method.
     * @param tu Controller parameter Tu.
     * @param tg Controller parameter Tg.
     * @return
     */
    public static ArrayList<Double> saniApproximation(double tu, double tg) {
        ArrayList<Double> returnArray = new ArrayList<>();

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
        // the magic numbers, then skip ahead to order 10
        double ratio = tu / tg;
        int order = 2;
        while(ratio > orderThresholds[order - 2]) {
            order++;

            /* ratio is larger than all thresholds
            if(order - 2 >= orderThresholds.length()) {
                order = 10; // skip order 9
            }*/
        }

        return returnArray;
    }
}