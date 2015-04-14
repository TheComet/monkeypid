package ch.fhnw.ht.eit.pro2.team3.monkeypid.views;

import ch.fhnw.ht.eit.pro2.team3.monkeypid.controllers.Controller;
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
		//super(new GridBagLayout());
		super(new BorderLayout());

        // Create a test series of data
        XYSeries series = new XYSeries("");
        series.add(0, 2);
        series.add(1, 4);
        series.add(2, 3);
        series.add(3, 5);

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
        JFreeChart chart = new JFreeChart("Test Plot", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        // need a panel to add the chart to
        ChartPanel panel = new ChartPanel(chart);
        
       panel.setPreferredSize(new java.awt.Dimension( 600 , 400 ) );
        
        //panel.setMinimumDrawWidth( 0 );
        //panel.setMinimumDrawHeight( 0 );
        //panel.setMaximumDrawWidth( 500 );
        //panel.setMaximumDrawHeight( 500 );
        
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