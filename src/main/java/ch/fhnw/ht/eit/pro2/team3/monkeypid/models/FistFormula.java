package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IPlant;

public class FistFormula extends IPlant {

    public enum Method {
        RESWICK,
        OPPELT,
        ROSENBERG
    }

    public enum Overswing {
        ZERO,
        TWENTY
    }

    public enum Mode {
        I,
        PI,
        PID
    }

    private Method method;
    private Mode mode;
    private Overswing overswing;

    public FistFormula(Method method, Mode mode, Overswing overswing) {
        this.method = method;
        this.mode = mode;
        this.overswing = overswing;
    }

    @Override
    public void setOverswing(double percent) {
        // Fist formula only supports 0% or 20%. Snap to the nearest valid value.
        if(percent > 10.0)
            overswing = Overswing.TWENTY;
        else
            overswing = Overswing.ZERO;
    }

    @Override
    public void calculate(double tg, double tu, double ks) {

        // here, we go through all combinations of:
        // - methods (Reswick, Oppelt, and Rosenberg)
        // - modes (PI and PID)
        // - overswing (0% and 20%)
        // this produces a huge nested switch-case statement.

        switch(method) {
            case RESWICK:
                switch(mode) {
                    case PI:
                        // gutes stoerverhalten PI
                        switch(overswing) {
                            case ZERO:
                                // Reswick, PI, 0%
                                this.result.kr = 0.6 * tg / (ks * tu);
                                this.result.tu = 4 * tu;
                                break;
                            case TWENTY:
                                // Reswick, PI, 20%
                                this.result.kr = 0.7 * tg / (ks * tu);
                                this.result.tu = 2.3 * tu;
                                break;
                            default: break;
                        }
                        // PI, so no D factor
                        this.result.tv = 0.0;
                        break;

                    case PID:
                        // gutes stoerverhalten PID
                        switch(overswing) {
                            case ZERO:
                                // Reswick, PID, 0%
                                this.result.kr = 0.95 * tg / (ks * tu);
                                this.result.tu = 2.4 * tu;
                                this.result.tv = 0.42 * tu;
                                break;
                            case TWENTY:
                                // Reswick, PID, 20%
                                this.result.kr = 1.2 * tg / (ks * tu);
                                this.result.tu = 2 * tu;
                                this.result.tv = 0.42 * tu;
                                break;
                            default: break;
                        }
                        break;

                    default: break;
                }
                break;

            case OPPELT:
                switch(mode) {
                    case PI:
                        // Oppelt, PI
                        this.result.kr = 0.8 * tg / (ks * tu);
                        this.result.tu = 3.0 * tu;
                        this.result.tv = 0.0;
                        break;
                    case PID:
                        // Oppelt, PID
                        this.result.kr = 1.2 * tg / (ks * tu);
                        this.result.tu = 2.0 * tu;
                        this.result.tv = 0.42 * tu;
                        break;
                }
                break;

            case ROSENBERG:
                switch(mode) {
                    case PI:
                        // Rosenberg, PI
                        this.result.kr = 0.91 * tg / (ks * tu);
                        this.result.tu = 3.3 * tu;
                        this.result.tv = 0.0;
                        break;
                    case PID:
                        // Rosenberg, PID
                        this.result.kr = 1.2 * tg / (ks * tu);
                        this.result.tu = 2.0 * tu;
                        this.result.tv = 0.44 * tu;
                        break;
                }
                break;

            default:
                // unknown, set everything to 0
                this.result.kr = 0.0;
                this.result.tu = 0.0;
                this.result.tv = 0.0;
                break;
        }
    }

}