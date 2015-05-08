package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

public class FistFormulaOppeltPID extends AbstractControllerCalculator {

    public FistFormulaOppeltPID(Plant plant) {
        super(plant);
    }

    @Override
    protected final AbstractController calculate() {
        double tn = 2.0 * plant.getTu();
        double tv = 0.42 * plant.getTu();
        double kr = 1.2 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);

        return new PIDController(getName(), tn, tv, kr, tp);
    }

    @Override
    public String getName() {
        return CalculatorNames.OPPELT_PID;
    }

    @Override
    public Color getColor() {
        return RenderColors.OPPELT_PID;
    }
}