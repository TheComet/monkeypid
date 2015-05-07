package ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;

public interface IClosedLoopListener {
    void onStepResponseCalculationComplete(ClosedLoop closedLoop);
}
