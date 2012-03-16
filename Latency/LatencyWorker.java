import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/*
 * A worker that repeatedly connects to a given url on
 * port 80 and gathers a list of <timestamp, latency>
 * pairs.
 */
public class LatencyWorker extends Thread {

	private ArrayList<Long[]> latencies = new ArrayList<Long[]>();
	private String url;
	boolean clear = false;

	// Input: url of site to be measured
	public LatencyWorker(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		while (true) {
			Long[] latency = new Long[2];
			latency[0] = System.currentTimeMillis();
			latency[1] = checkLatency();
			latencies.add(latency);
		}
	}

	// Connects to the given url on port 80 and measures the time
	// (in ms) it takes to do. If the site is down, -1 is recorded
	// as the latency.
	private synchronized long checkLatency() {

		if (clear) {
			latencies.clear();
			clear = false;
		}

		Socket s;
		final long startTime = System.currentTimeMillis();
		long duration = -1;
		try {
			s = new Socket(url, 80);
			duration = System.currentTimeMillis() - startTime;
			s.close();
		} catch (UnknownHostException e) {
			System.err.println("Cannot connect to host: " + url);
		} catch (IOException e) {
			System.err.println("IOException");
		}
		return duration;
	}

	// Returns an array of <timestamp, latency> pairs.
	public synchronized Long[][] getLatencies() {
		clear = true;
		return latencies.toArray(new Long[latencies.size()][2]);
	}

}