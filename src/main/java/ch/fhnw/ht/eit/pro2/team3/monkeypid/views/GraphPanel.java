package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath.Axis;

import javax.swing.*;

import java.awt.*;

/**
 * GraphPanel is a JPanel which includes the plot of the simulations. For the
 * plot JFreeChart is used.
 * 
 * @author Josua
 *
 */
public class GraphPanel extends JPanel implements IModelListener {
    private XYSeriesCollection dataCollection = null;
    private JFreeChart chart = null;

	/**
	 * 
	 * @param controller
	 */
	public GraphPanel() {
		// super(new GridBagLayout());
		super(new BorderLayout());

        // collection holds XY data series
		dataCollection = new XYSeriesCollection();

		// axes
	    NumberAxis xAxis = new NumberAxis("Zeit");
	    NumberAxis yAxis = new NumberAxis("y(t)");
	    
		// renderer
		XYItemRenderer renderer = new StandardXYItemRenderer();
	    
	    // create plot
	 	 XYPlot plot = new XYPlot(dataCollection, xAxis, yAxis, renderer);

		

		// add plot into a new chart
		chart = new JFreeChart("Sprungantwort Geschlossener Regelkreis", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        // don't need the chart
        chart.getLegend().setVisible(false);

		// need a panel to add the chart to
		ChartPanel panel = new ChartPanel(chart);

		// TODO beste variante?
		// set prefered size 
		//panel.setPreferredSize(new java.awt.Dimension (800, 600));
		//panel.setMinimumSize(new Dimension(800, 600));

		// finally, add panel as an element in our GraphPanel
		this.add(panel);
	}
	
	public void autoScaleAxis(){
		chart.getPlot().zoom(100);
		//getRangeAxis().setAutoRange(true);
		//chart.getXYPlot().getDomainAxis().setAutoRange(true);

		System.out.println("autoscale");
	}
	
	private XYItemRenderer getDatasetRenderer() {
        return chart.getXYPlot().getRendererForDataset(dataCollection);
    }

    private int getSeriesIndex(ClosedLoop loop) {
        return dataCollection.getSeriesIndex(loop.getStepResponse().getKey());
    }

    private void setSeriesVisible(ClosedLoop loop, boolean flag) {
        getDatasetRenderer().setSeriesVisible(getSeriesIndex(loop), flag);
    }

    @Override
    public void onAddClosedLoop(ClosedLoop closedLoop) {
        try {
            dataCollection.addSeries(closedLoop.getStepResponse());

            // The closedLoop object specifies what color it wants to be rendered in
            getDatasetRenderer().setSeriesPaint(getSeriesIndex(closedLoop), closedLoop.getColor());

            // See issue #21 - make visible again
            setSeriesVisible(closedLoop, true);

        } catch(IllegalArgumentException e) {
            System.out.println("Can't add step response to graph, it's already in the graph");
        }
    }

    @Override
    public void onRemoveClosedLoop(ClosedLoop closedLoop) {
        if(closedLoop.getStepResponse() != null) {
            dataCollection.removeSeries(closedLoop.getStepResponse());
        }
    }

    @Override
    public void onSimulationBegin(int numberOfStepResponses) {
    }

    @Override
    public void onSimulationComplete() {
    }

    @Override
    public void onHideStepResponse(ClosedLoop closedLoop) {
        setSeriesVisible(closedLoop, false);
    }

    @Override
    public void onShowStepResponse(ClosedLoop closedLoop) {
        setSeriesVisible(closedLoop, true);
    }
}
