package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

public class FistFormulaReswickStoerPID20 extends AbstractControllerCalculator {

    public FistFormulaReswickStoerPID20(Plant plant) {
        super(plant);
    }

    @Override
    protected final AbstractController calculate() {
        double tn = 2.0 * plant.getTu();
        double tv = 0.42 * plant.getTu();
        double kr = 1.2 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);

        return new ControllerPID(getName(), tn, tv, kr, tp);
    }

    @Override
    public String getName() {
        return CalculatorNames.RESWICK_STOER_PID_20;
    }

    @Override
    public Color getColor() {
        return RenderColors.RESWICK_STOER_PID_20;
    }
}