package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IClosedLoopListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

/**
 * GraphPanel is a JPanel which includes the plot of the simulations. For the
 * plot JFreeChart is used.
 * 
 * @author Josua
 *
 */
public class GraphPanel extends JPanel implements IModelListener, IClosedLoopListener {
    private XYSeriesCollection dataCollection = null;

	/**
	 * 
	 * @param controller
	 */
	public GraphPanel() {
		// super(new GridBagLayout());
		super(new BorderLayout());

        // collection holds XY data series
		dataCollection = new XYSeriesCollection();

		// renderer
		XYItemRenderer renderer = new StandardXYItemRenderer();

		// axes
		NumberAxis xAxis = new NumberAxis("Y Data");
		NumberAxis yAxis = new NumberAxis("X Data");

		// create plot
		XYPlot plot = new XYPlot(dataCollection, xAxis, yAxis, renderer);

		// add plot into a new chart
		JFreeChart chart = new JFreeChart("Test Plot", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		// need a panel to add the chart to
		ChartPanel panel = new ChartPanel(chart);

		// TODO beste variante?
		// set prefered size to 600x400
		panel.setPreferredSize(new java.awt.Dimension(600, 400));

		// finally, add panel as an element in our GraphPanel
		this.add(panel);
	}

    @Override
    public void onAddClosedLoop(ClosedLoop closedLoop) {
        closedLoop.registerListener(this);
    }

    @Override
    public void onRemoveClosedLoop(ClosedLoop closedLoop) {
        if(closedLoop.getStepResponse() != null) {
            dataCollection.removeSeries(closedLoop.getStepResponse());
        }
    }

    @Override
    public void onStepResponseCalculationComplete(ClosedLoop closedLoop) {
        try {
            dataCollection.addSeries(closedLoop.getStepResponse());
        } catch(IllegalArgumentException e) {
            System.out.println("Can't add step response to graph, it's already in the graph");
        }
    }
}
