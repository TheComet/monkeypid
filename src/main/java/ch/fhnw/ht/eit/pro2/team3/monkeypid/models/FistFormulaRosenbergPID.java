package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

import java.awt.*;

public class FistFormulaRosenbergPID extends AbstractControllerCalculator {

    public FistFormulaRosenbergPID(Plant plant) {
        super(plant);
    }

    @Override
    protected final IController calculate() {
        double tn = 2.0 * plant.getTu();
        double tv = 0.44 * plant.getTu();
        double kr = 1.2 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);

        return new PIDController(getName(), tn, tv, kr, tp);
    }

    @Override
    public String getName() {
        return CalculatorNames.ROSENBERG_PID;
    }

    @Override
    public Color getColor() {
        return RenderColors.ROSENBERG_PID;
    }
}