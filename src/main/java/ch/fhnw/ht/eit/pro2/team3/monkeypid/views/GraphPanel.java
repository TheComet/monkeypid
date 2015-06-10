package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.listeners.IModelListener;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

import java.awt.*;

/**
 * GraphPanel is a JPanel which includes the plot of the simulations. For the
 * plot JFreeChart is used.
 * @author Josua
 *
 */
public class GraphPanel extends JPanel implements IModelListener,
		ChartMouseListener {
	private static final long serialVersionUID = 1L;
	private XYSeriesCollection dataCollection = null;
	private JFreeChart chart = null;

	/**
	 * The constructor of GraphPanel creates a JPanel with BorderLayout and adds
	 * a JFreeChart-XY-Plot to the Panel
	 */
	public GraphPanel() {
		super(new BorderLayout());

		// collection holds XY data series
		dataCollection = new XYSeriesCollection();

		// titles for the axis
		NumberAxis xAxis = new NumberAxis("Zeit in Sekunden");
		NumberAxis yAxis = new NumberAxis("y(t)");

		// renderer
		XYItemRenderer renderer = new StandardXYItemRenderer();

		// create plot
		XYPlot plot = new XYPlot(dataCollection, xAxis, yAxis, renderer);

		// add plot into a new chart
		chart = new JFreeChart("Sprungantwort Geschlossener Regelkreis",
				JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		// don't need the chart
		chart.getLegend().setVisible(false);

		// need a panel to add the chart to
		ChartPanel panel = new ChartPanel(chart);
		panel.addChartMouseListener(this);

		// finally, add panel as an element in our GraphPanel
		this.add(panel);
	}

	/**
	 * Autoscales the axis (x and y) of the graph so all data points fit the
	 * visible area
	 */
	public void autoScaleAxis() {
		chart.getXYPlot().getDomainAxis().setAutoRange(true);
		chart.getXYPlot().getRangeAxis().setAutoRange(true);
	}

	/**
	 * Convenience method for retrieving JFreeChart's dataset renderer of the current data collection.
	 * @return The dataset renderer of the current data collection.
	 */
	private XYItemRenderer getDatasetRenderer() {
		return chart.getXYPlot().getRendererForDataset(dataCollection);
	}

	/**
	 * Convenience method for retrieving JFreeChart's series index of the specified closed loop object.
	 * @param loop The closed loop object to retrieve the index for.
	 * @return Returns the index of the series corresponding to the specified closed loop object.
	 */
	private int getSeriesIndex(ClosedLoop loop) {
		return dataCollection.getSeriesIndex(loop.getStepResponse().getKey());
	}

	/**
	 * Makes the specified closed loop object either visible or hidden.
	 * @param loop The closed loop object.
	 * @param flag True to show, false to hide.
	 */
	private void setSeriesVisible(ClosedLoop loop, boolean flag) {
		getDatasetRenderer().setSeriesVisible(getSeriesIndex(loop), flag);
	}

	/**
	 * Adds a closedLoop stepResponse curve to the graph If a curve with the
	 * same name is already in the graph, remove it first
	 */
	@Override
	public void onAddCalculation(ClosedLoop closedLoop, boolean visible) {
		SwingUtilities.invokeLater(() -> {
			dataCollection.addSeries(closedLoop.getStepResponse());

			// The closedLoop object specifies what color it wants to be
			// rendered in
			getDatasetRenderer().setSeriesPaint(getSeriesIndex(closedLoop),
					closedLoop.getColor());

			// getDatasetRenderer().setSeriesStroke(getSeriesIndex(closedLoop),
			// new BasicStroke(5.0f,BasicStroke.CAP_BUTT,
			// BasicStroke.JOIN_MITER, 10.0f, new float[] {1.0f, 0.0f},
			// 0.0f));
			getDatasetRenderer().setSeriesToolTipGenerator(
					getSeriesIndex(closedLoop), new XYToolTipGenerator() {

						@Override
						public String generateToolTip(XYDataset dataset,
													  int series, int item) {
							return closedLoop.getName();
						}
					});

			// See issue #21 - make visible again
			setSeriesVisible(closedLoop, visible);
		});
	}

	/**
	 * Removes the curve of a closed loop object from the graph.
	 * @param closedLoop The closed loop object to remove.
	 */
	@Override
	public void onRemoveCalculation(ClosedLoop closedLoop) {
		SwingUtilities.invokeLater(() -> {
			if (closedLoop.getStepResponse() != null) {
				dataCollection.removeSeries(closedLoop.getStepResponse());
			}
		});
	}

	/**
	 * This is called when a closed loop object has been recalculated and a new XYSeries object is present. This will
	 * switch the new XYSeries object with the old one. If there was no old one, then a new one will be added.
	 * @param closedLoop The closed loop object that has been recalculated.
	 */
	@Override
	public void onUpdateCalculation(ClosedLoop closedLoop) {
		SwingUtilities.invokeLater(() -> {
			XYSeries oldSeries = dataCollection.getSeries(closedLoop.getStepResponse().getKey());
			if (oldSeries != null)
				dataCollection.removeSeries(oldSeries);
			dataCollection.addSeries(closedLoop.getStepResponse());
		});
	}

	@Override
	public void onSimulationBegin(int numberOfStepResponses) {
	}

	/**
	 * Set lower y-Axis margin to 0.0 (from default 5%) -> display doesn't flicker, if slider is adjusted
	 */
	@Override
	public void onSimulationComplete() {
		SwingUtilities.invokeLater(() -> {
			ValueAxis yAxis = chart.getXYPlot().getRangeAxis();
			yAxis.setLowerMargin(0.00);
		});
	}

	@Override
	public void onHideCalculation(ClosedLoop closedLoop) {
		setSeriesVisible(closedLoop, false);
	}

	@Override
	public void onShowCalculation(ClosedLoop closedLoop) {
		setSeriesVisible(closedLoop, true);
	}

	@Override
	public void onNewPlant(Plant plant) {}

	/**
	 * This is called, if the user clicks with the mouse onto the graph If a
	 * curve is hit, its renderer-stroke is set wider Else, all curves
	 * renderer-strokes are set to the default wide (This is a basic
	 * highlighting-mechanism, it could be improved by also highliting the
	 * corresponding table row. And reverse also from the table to the graph.
	 * This highlighting-action should be controller over the controller and the
	 * model)
	 */
	@Override
	public void chartMouseClicked(ChartMouseEvent e) {
		if (e.getEntity().getClass() == XYItemEntity.class) {
			for (int i = 0; i < dataCollection.getSeriesCount(); i++) {
				getDatasetRenderer().setSeriesStroke(i, new BasicStroke(1.0f));
			}
			XYItemEntity entity = (XYItemEntity) e.getEntity();
			getDatasetRenderer().setSeriesStroke(entity.getSeriesIndex(),
					new BasicStroke(3.0f));
		} else {
			for (int i = 0; i < dataCollection.getSeriesCount(); i++) {
				getDatasetRenderer().setSeriesStroke(i, new BasicStroke(1.0f));
			}
		}
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent event) {}
}
