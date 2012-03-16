import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Latency {

	private static final String DEFAULT_STATUS = "Status: Both websites are currently reachable";
	private static final String DOWN_STATUS = "Status: Both websites cannot be reached. Please check your connection";
	private static final String CHART_TITLE = "yahoo.com vs google.com latency";
	private static final String X_AXIS_LBL = "Time (ms since program start)";
	private static final String Y_AXIS_LBL = "Latency (ms)";
	private static final String GOOG_SERIES_NAME = "Google";
	private static final String YAHOO_SERIES_NAME = "Yahoo";
	private static final String GOOG_URL = "google.com";
	private static final String YAHOO_URL = "yahoo.com";
	private static final int MAX_ITEM_COUNT = 500;

	private final JFrame frame;
	private JFreeChart chart;
	private JLabel lblStatus;

	private final LatencyWorker workerGoogle;
	private final LatencyWorker workerYahoo;
	private XYSeries seriesGoogle;
	private XYSeries seriesYahoo;
	private long startTime;

	public Latency() {

		// Initialize worker threads
		workerGoogle = new LatencyWorker(GOOG_URL);
		workerYahoo = new LatencyWorker(YAHOO_URL);

		// Explicitly kill threads on program exit
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				super.run();
				workerGoogle.interrupt();
				workerYahoo.interrupt();
			}
		});

		// Setup dataset and chart
		seriesGoogle = new XYSeries(GOOG_SERIES_NAME);
		seriesYahoo = new XYSeries(YAHOO_SERIES_NAME);
		seriesGoogle.setMaximumItemCount(MAX_ITEM_COUNT);
		seriesYahoo.setMaximumItemCount(MAX_ITEM_COUNT);
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesGoogle);
		dataset.addSeries(seriesYahoo);
		chart = ChartFactory.createXYLineChart(CHART_TITLE, X_AXIS_LBL,
				Y_AXIS_LBL, dataset, PlotOrientation.VERTICAL, true, true,
				false);

		// Setup the window
		ChartPanel panel = new ChartPanel(chart);
		lblStatus = new JLabel(DEFAULT_STATUS);
		frame = new JFrame("Latency");
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.NORTH);
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(600, 400);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		// Setup bottom panel with status label
		JPanel pnlBottom = new JPanel();
		pnlBottom.setLayout(new FlowLayout());
		pnlBottom.add(lblStatus);
		frame.add(pnlBottom, BorderLayout.SOUTH);

	}

	public void run() {

		startTime = System.currentTimeMillis();
		workerGoogle.start();
		workerYahoo.start();
		frame.setVisible(true);

		// Main program loop: Get latencies from the workers,
		// add them to the chart, and sleep for 100ms.
		boolean googSuccess;
		boolean yahooSuccess;
		while (true) {

			// Make sure that we have display the same number of data
			// points for each series so that the graph looks nice.
			Long[][] latenciesGoogle = workerGoogle.getLatencies();
			Long[][] latenciesYahoo = workerYahoo.getLatencies();
			int maxPoints = latenciesGoogle.length;
			if (latenciesYahoo.length < maxPoints) {
				maxPoints = latenciesYahoo.length;
			}

			googSuccess = addToSeries(latenciesGoogle, seriesGoogle, maxPoints);
			yahooSuccess = addToSeries(latenciesYahoo, seriesYahoo, maxPoints);

			// Set the status label in case we have no Internet connection
			if (googSuccess && yahooSuccess) {
				lblStatus.setText(DEFAULT_STATUS);
				lblStatus.setForeground(Color.BLACK);
			} else {
				lblStatus.setText(DOWN_STATUS);
				lblStatus.setForeground(Color.RED);
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Add recorded latencies to a series. Return true if success,
	// false if website is unreachable.
	private boolean addToSeries(Long[][] latencies, XYSeries series,
			int maxPoints) {
		for (int i = 0; i < maxPoints; i++) {
			if (latencies[i][1] != -1) {
				series.add((double) latencies[i][0] - startTime,
						(double) latencies[i][1]);
			}
		}

		if ((latencies.length > 0) && latencies[latencies.length - 1][1] == -1) {
			return false;
		}

		return true;
	}

	public static void main(String args[]) {
		(new Latency()).run();
	}
}
