package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import java.awt.*;

public class ZellwegerI extends AbstractZellweger {

    public ZellwegerI(Plant plant) {
        super(plant, 0.0); // phase margin is irrelevant in Zellweger I
    }

    @Override
    protected final AbstractController calculate() {
        setAngleOfInflection(-45.0);
        return calculateI();
    }

    @Override
    public String getName() {
        return CalculatorNames.ZELLWEGER_I;
    }

    @Override
    public Color getColor() {
        return RenderColors.ZELLWEGER_I;
    }

    private AbstractController calculateI() {

        // Tn parameter of controller
        double tn = 1.0 / findAngleOnPlantPhase();

        return new ControllerI(getName(), tn);
    }
}