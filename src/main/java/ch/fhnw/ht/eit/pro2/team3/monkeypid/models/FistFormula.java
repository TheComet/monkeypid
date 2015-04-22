package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IRegulator;

public class FistFormula extends AbstractPlant {

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

    public FistFormula(Method method, Mode mode, double overswingPercent) {
        this.method = method;
        this.mode = mode;

        // Fist formula only supports 0% or 20%. Snap to the nearest valid value.
        if(overswingPercent > 10.0)
            overswing = Overswing.TWENTY;
        else
            overswing = Overswing.ZERO;
    }

    @Override
    public IRegulator calculateRegulator() {

        // here, we go through all combinations of:
        // - methods (Reswick, Oppelt, and Rosenberg)
        // - modes (PI and PID)
        // - overswing (0% and 20%)
        // this produces a huge nested switch-case statement.
        
        double result_kr = 0.0, result_tu = 0.0, result_tv = 0.0;

        switch(method) {
            case RESWICK:
                switch(mode) {
                    case PI:
                        // gutes stoerverhalten PI
                        switch(overswing) {
                            case ZERO:
                                // Reswick, PI, 0%
                                result_kr = 0.6 * this.parameters.tg / (this.parameters.ks * this.parameters.tu);
                                result_tu = 4 * this.parameters.tu;
                                break;
                            case TWENTY:
                                // Reswick, PI, 20%
                                result_kr = 0.7 * this.parameters.tg / (this.parameters.ks * this.parameters.tu);
                                result_tu = 2.3 * this.parameters.tu;
                                break;
                            default: break;
                        }
                        // PI, so no D factor
                        result_tv = 0.0;
                        break;

                    case PID:
                        // gutes stoerverhalten PID
                        switch(overswing) {
                            case ZERO:
                                // Reswick, PID, 0%
                                result_kr = 0.95 * this.parameters.tg / (this.parameters.ks * this.parameters.tu);
                                result_tu = 2.4 * this.parameters.tu;
                                result_tv = 0.42 * this.parameters.tu;
                                break;
                            case TWENTY:
                                // Reswick, PID, 20%
                                result_kr = 1.2 * this.parameters.tg / (this.parameters.ks * this.parameters.tu);
                                result_tu = 2 * this.parameters.tu;
                                result_tv = 0.42 * this.parameters.tu;
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
                        result_kr = 0.8 * this.parameters.tg / (this.parameters.ks * this.parameters.tu);
                        result_tu = 3.0 * this.parameters.tu;
                        result_tv = 0.0;
                        break;
                    case PID:
                        // Oppelt, PID
                        result_kr = 1.2 * this.parameters.tg / (this.parameters.ks * this.parameters.tu);
                        result_tu = 2.0 * this.parameters.tu;
                        result_tv = 0.42 * this.parameters.tu;
                        break;
                }
                break;

            case ROSENBERG:
                switch(mode) {
                    case PI:
                        // Rosenberg, PI
                        result_kr = 0.91 * this.parameters.tg / (this.parameters.ks * this.parameters.tu);
                        result_tu = 3.3 * this.parameters.tu;
                        result_tv = 0.0;
                        break;
                    case PID:
                        // Rosenberg, PID
                        result_kr = 1.2 * this.parameters.tg / (this.parameters.ks * this.parameters.tu);
                        result_tu = 2.0 * this.parameters.tu;
                        result_tv = 0.44 * this.parameters.tu;
                        break;
                }
                break;

            default:
                // unknown
                break;
        }

        return new PIDRegulator(result_kr, result_tu, result_tv);
    }

}