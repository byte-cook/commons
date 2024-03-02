package de.kobich.commons.monitor.scanner;

import java.util.EventListener;
import java.util.TimerTask;

/**
 * The interface for a scanner. 
 */
public interface IScanner<Listener extends EventListener> {
	/**
	 * Returns the timmer task
	 * @return the timer task
	 */
	public TimerTask getTimerTask();

	/**
	 * Adds a listener
	 * @param listener a listener
	 */
	public void addListener(Listener listener);

	/**
	 * Removes a listener
	 * @param listener a listener
	 */
	public void removeListener(Listener listener);
}
