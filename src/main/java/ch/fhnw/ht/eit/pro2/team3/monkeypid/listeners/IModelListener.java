package ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;

public interface IModelListener {
    public void onAddClosedLoop(ClosedLoop closedLoop);
    public void onRemoveClosedLoop(ClosedLoop closedLoop);
}
