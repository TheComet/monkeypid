package ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;

public interface IModelListener {
    void onAddClosedLoop(ClosedLoop closedLoop);
    void onRemoveClosedLoop(ClosedLoop closedLoop);
    void onSimulationBegin();
    void onSimulationComplete();
    void onHideStepResponse(ClosedLoop closedLoop);
    void onShowStepResponse(ClosedLoop closedLoop);
}
