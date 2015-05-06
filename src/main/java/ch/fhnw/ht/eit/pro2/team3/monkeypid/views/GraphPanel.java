package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IController;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.interfaces.IControllerCalculator;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ClosedLoop;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.Plant;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.SaniCurves;
import ch.fhnw.ht.eit.pro2.team3.monkeypid.models.ZellwegerPI;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * GraphPanel is a JPanel which includes the plot of the simulations. For the
 * plot JFreeChart is used.
 * 
 * @author Josua
 *
 */
public class GraphPanel extends JPanel implements ActionListener {

	/**
	 * 
	 * @param controller
	 */
	public GraphPanel() {
		// super(new GridBagLayout());
		super(new BorderLayout());

        SaniCurves sani = new SaniCurves();
		Plant plant = new Plant(2, 6, 1, sani);
        IControllerCalculator cc = new ZellwegerPI(plant, 45);
        cc.calculate();
        IController c = cc.getController();

		// create a test series of data
		ClosedLoop system = new ClosedLoop(plant, c);
		XYSeries series = system.calculateStepResponse(2 * 1024);

		// add series to collection (collection derives from XYDataset)
		XYSeriesCollection data = new XYSeriesCollection();
		data.addSeries(series);

		// renderer
		XYItemRenderer renderer = new StandardXYItemRenderer();

		// axes
		NumberAxis xAxis = new NumberAxis("Y Data");
		NumberAxis yAxis = new NumberAxis("X Data");

		// create plot
		XYPlot plot = new XYPlot(data, xAxis, yAxis, renderer);

		// add plot into a new chart
		JFreeChart chart = new JFreeChart("Test Plot",
				JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		// need a panel to add the chart to
		ChartPanel panel = new ChartPanel(chart);

		// TODO beste variante?
		// set prefered size to 600x400
		panel.setPreferredSize(new java.awt.Dimension(600, 400));

		// finally, add panel as an element in our GraphPanel
		this.add(panel);
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
