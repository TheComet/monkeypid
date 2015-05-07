package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

import java.awt.*;

public class FistFormulaReswickStoerPID0 extends AbstractControllerCalculator {

    public FistFormulaReswickStoerPID0(Plant plant) {
        super(plant);
    }

    @Override
    protected final IController calculate() {
        double tn = 2.4 * plant.getTu();
        double tv = 0.42 * plant.getTu();
        double kr = 0.95 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);

        return new PIDController(getName(), tn, tv, kr, tp);
    }

    @Override
    public String getName() {
        return CalculatorNames.RESWICK_STOER_PID_0;
    }

    @Override
    public Color getColor() {
        return RenderColors.RESWICK_STOER_PID_0;
    }
}