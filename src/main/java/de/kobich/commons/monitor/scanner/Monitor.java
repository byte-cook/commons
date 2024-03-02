package de.kobich.commons.monitor.scanner;

import java.util.EventListener;
import java.util.Timer;

/**
 * This class is responsible for starting and stopping
 * a scanner.
 */
public class Monitor<Listener extends EventListener> {
	private int pollingInterval;
	private boolean running;
	private Timer timer;
	private IScanner<Listener> scanner;
	
	/**
	 * Default constructor with a polling interval of 1 second
	 */
	public Monitor() {
		this(1000);
	}

	/**
	 * Constructor
	 * @param pollingInterval interval for polling in milliseconds 
	 */
	public Monitor(int pollingInterval) {
		this.pollingInterval = pollingInterval;
		this.running = false;
	}
	
	/**
	 * Sets the scanner 
	 * @param scanner the scanner
	 */
	public void setScanner(IScanner<Listener> scanner) {
		this.scanner = scanner;
	}
	
	/**
	 * Starts the scanner
	 */
	public void start() {
		if (scanner == null) {
			throw new IllegalStateException("The scanner is not set");
		}
		timer = null;
		timer = new Timer(true);
		timer.schedule(scanner.getTimerTask(), 0, pollingInterval);
		running = true;
	}

	/**
	 * Stops the scanner
	 */
	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		running = false;
	}
	
	/**
	 * Indicates if the monitor is running
	 * @return boolean
	 */
	public boolean isRunning() {
		return running;
	}
}
