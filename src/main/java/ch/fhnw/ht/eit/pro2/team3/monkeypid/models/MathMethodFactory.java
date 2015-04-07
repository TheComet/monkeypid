package ch.fhnw.ht.eit.pro2.team3.monkeypid.models;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.MathMethodInterface;

class MathMethodFactory {
    public enum MethodID {
        TEST,
        ZELLWEGER_PI,
        ZELLWEGER_PID,
        SANI
    }

    /**
     * Returns a new math method implementing the method specified.
     * @param method The method to instantiate.
     * @return The object implementing the method.
     */
    public MathMethodInterface getMethod(MethodID method)
    {
        switch(method) {
            case TEST:
                return new TestMathMethod();
        }

        // invalid method ID
        throw new RuntimeException("Invalid MethodID");
    }
}
