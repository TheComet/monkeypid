package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;

import java.awt.*;

public class FistFormulaReswickFuehrungPID0 extends AbstractControllerCalculator {

    public FistFormulaReswickFuehrungPID0(Plant plant) {
        super(plant);
    }

    @Override
    protected final IController calculate() {
        double tn = 1.0 * plant.getTg();
        double tv = 0.5 * plant.getTu();
        double kr = 0.6 * plant.getTg() / (plant.getKs() * plant.getTu());
        double tp = beautifyTpSoNiklausIsHappy(tv * parasiticTimeConstantFactor);

        return new PIDController(getName(), tn, tv, kr, tp);
    }

    @Override
    public String getName() {
        return CalculatorNames.RESWICK_FUEHRUNG_PID_0;
    }

    @Override
    public Color getColor() {
        return RenderColors.RESWICK_FUEHRUNG_PID_0;
    }
}