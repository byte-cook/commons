package de.kobich.commons;

import de.kobich.commons.utils.TimeUtils;


/**
 * Stop watch utility.
 * @author ckorn
 */
public class StopWatch {
	private long startTime;
	private long stopTime;
	private boolean running;

	public StopWatch() {
		this.startTime = -1;
		this.stopTime = -1;
		this.running = false;
	}

	/**
	 * @return <code>true</code> in case the stop watch is running; <code>false</code> otherwise.
	 */
	public boolean isRunning() {
		return this.running;
	}

	/**
	 * Starts the stop watch.
	 */
	public void start() {
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}

	/**
	 * Stops the stop watch.
	 */
	public long stop() {
		this.stopTime = System.currentTimeMillis();
		this.running = false;

		return getElapsedTime();
	}

	/**
	 * @return the elapsed time in <i>milliseconds</i> in case the stop watch was started; 0 otherwise.
	 */
	public long getElapsedTime() {
		if (this.startTime == -1) {
			return 0;
		}
		if (this.running) {
			return System.currentTimeMillis() - this.startTime;
		}
		else {
			return this.stopTime - this.startTime;
		}
	}

	/**
	 * Returns the formatted elapsed time. <br>
	 * <b>Format:</b> HH:mm:ss:SSS
	 * @return the formated elapsed time
	 */
	public String getFormattedElapsedTime() {
		return TimeUtils.format(getElapsedTime());
	}

	/**
	 * Resets the stop watch.
	 */
	public void reset() {
		this.startTime = -1;
		this.stopTime = -1;
		this.running = false;
	}

	/**
	 * Restart the stop watch. put start point to currentTime
	 */
	public long restart() {
		long currentTime = getElapsedTime();
		start();
		return currentTime;
	}
}