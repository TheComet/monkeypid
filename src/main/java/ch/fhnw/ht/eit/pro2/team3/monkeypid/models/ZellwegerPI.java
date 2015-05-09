package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

/**
 * Implements the Zellweger method for calculating PI controllers.
 * @author Alex Murray
 */
public class ZellwegerPI extends AbstractZellweger {

    /**
     * Constructs a new Zellweger calculator using the specified plant.
     * @param plant The plant to calculate a controller for.
     * @param phaseMargin The phase margin to use during angle lookups on the phase of the open loop.
     */
    public ZellwegerPI(Plant plant, double phaseMargin) {
        super(plant, phaseMargin);
    }

    /**
     * Uses a binary search to approximate the angle on the phase of the open loop. Accuracy can be set by calling the
     * method setMaxIterations().
     * @return Returns the angle at the specified phase of the open loop.
     */
    private double findAngleOnOpenLoopPhase(double tn) {

        // find phiDamping on the phase of the open loop
        double topFreq = endFreq;
        double bottomFreq = startFreq;
        double actualFreq = (topFreq + bottomFreq) / 2.0;
        for(int i = 0; i != maxIterations; i++) {
            double phiOpenLoopBuffer = calculatePlantPhase(actualFreq) +
                    calculateControllerPhase(actualFreq, tn);
            if(phiOpenLoopBuffer < phiDamping) {
                topFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            } else if(phiOpenLoopBuffer > phiDamping) {
                bottomFreq = actualFreq;
                actualFreq = (topFreq + bottomFreq) / 2.0;
            }
        }

        return actualFreq;
    }

    /**
     * Calculates the phase of a PI controller
     * @param omega Omega
     * @param tn Controller parameter Tn
     * @return atan(w*Tn)-pi/2
     */
    private double calculateControllerPhase(double omega, double tn) {
        return (Math.atan(omega * tn) - Math.PI / 2.0) * 180 / Math.PI;
    }

    /**
     * Calculates the amplitude of a PI controller
     * @param omega Omega
     * @param tn Controller parameter Tn
     * @return (sqrt(1+(w*Tn).^2)./(w*Tn))
     */
    private double calculateControllerAmplitude(double omega, double tn) {
        return Math.sqrt(1.0 + Math.pow(omega * tn, 2)) / (omega * tn);
    }

    /**
     * Calculates the appropriate controller for the specified plant.
     * @return Returns a new PI controller.
     */
    @Override
    protected final AbstractController calculate() {
        setAngleOfInflection(-90.0);

        // Tn parameter of controller
        double tn = 1.0 / findAngleOnPlantPhase();

        // get omega damping
        double omegaDamping = findAngleOnOpenLoopPhase(tn);

        // amplitude of the open loop at the omegaDamping frequency
        double ampOpenLoopKr = calculatePlantAmplitude(omegaDamping) * calculateControllerAmplitude(omegaDamping, tn);

        // Kr is the reciprocal of the amplitude at omegaDamping
        double kr = 1.0 / ampOpenLoopKr;

        return new ControllerPI(getName(), kr, tn);
    }

    /**
     * Gets the name of this calculator. The names are stored in a global class called CalculatorNames.
     * @return The name of this controller.
     */
    @Override
    public String getName() {
        return CalculatorNames.ZELLWEGER_PI;
    }

    /**
     * Gets the render colour of this calculator. The colours are stored in a global class called RenderColors.
     * @return The render color.
     */
    @Override
    public Color getColor() {
        return RenderColors.ZELLWEGER_PI;
    }
}