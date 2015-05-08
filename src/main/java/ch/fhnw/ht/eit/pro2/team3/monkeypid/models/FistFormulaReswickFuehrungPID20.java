package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

public class FistFormulaReswickFuehrungPID20 extends AbstractControllerCalculator {

    public FistFormulaReswickFuehrungPID20(Plant plant) {
        super(plant);
    }

    @Override
    protected final AbstractController calculate() {
        double tn = 1.35 * plant.getTg();
        double tv = 0.47 * plant.getTu();
        double kr = 0.95 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);
        
        return new PIDController(getName(), tn, tv, kr, tp);
    }

    @Override
    public String getName() {
        return CalculatorNames.RESWICK_FUEHRUNG_PID_20;
    }

    @Override
    public Color getColor() {
        return RenderColors.RESWICK_FUEHRUNG_PID_20;
    }
}