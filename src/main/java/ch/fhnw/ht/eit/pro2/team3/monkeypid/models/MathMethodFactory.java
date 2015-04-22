package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.RegulatorInterface;

class MathMethodFactory {

    public enum Method {
        ZELLWEGER,
        FISTFORMULA_RESWICK,
        FISTFORMULA_OPPELT,
        FISTFORMULA_ROSENBERG
    }

    public enum Mode {
        I,
        PI,
        PID
    }

    /**
     * Returns a new math method implementing the method specified.
     * @param method The method to instantiate.
     * @return The object implementing the method.
     */
    public RegulatorInterface getMethod(Method method, Mode mode)
    {
        switch(method) {
            case ZELLWEGER:
                break;
            case FISTFORMULA_RESWICK:
                return new FistFormula(FistFormula.Method.RESWICK, mode);
            case FISTFORMULA_OPPELT:
                return new FistFormula(FistFormula.Method.OPPELT, mode);
            case FISTFORMULA_ROSENBERG:
                return new FistFormula(FistFormula.Method.ROSENBERG, mode);
            default:
                break;
        }
        throw new RuntimeException("Invalid MethodID");
    }
}
