package ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces;

import org.jfree.data.xy.XYDataset;

public interface MathMethodInterface {
    public void doCalculation(double tg, double tu, double ks);
    public XYDataset getResult();
    public void setOverSwing(double percent);
}
