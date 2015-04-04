package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class GraphPanel extends JPanel implements ActionListener {

	// Eingabefeld Ks Tu Tg
	private JLabel lbEingabeTitel = new JLabel(
			"Darstellung des Graphen");

	public GraphPanel(Controller controller) {
		super(new GridBagLayout());

        // Create a test series of data
        XYSeries series = new XYSeries("Test Series");
        series.add(0, 2);
        series.add(1, 4);
        series.add(2, 3);
        series.add(3, 5);

        // add series to collection (collection derives from XYDataset)
        XYSeriesCollection data = new XYSeriesCollection();
        data.addSeries(series);

        // renderer
        XYItemRenderer renderer = new StandardXYItemRenderer();

        // axis
        NumberAxis yAxis = new NumberAxis("Test Range");

        // create plot
        XYPlot plot = new XYPlot(data, null, yAxis, renderer);

        // add plot into a new chart
        JFreeChart chart = new JFreeChart("Test Plot", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        // need a panel to add the chart to
        ChartPanel panel = new ChartPanel(chart);

        // finally, add panel as an element in our GraphPanel
        this.add(panel);
	}

	/*public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager
							.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				JFrame frame = new JFrame();
				frame.setUndecorated(true);
				frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("TopView");
				frame.getContentPane().add(new GraphDisplayPanel(null));
				frame.pack();
				frame.setVisible(true);
			}
		});
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
