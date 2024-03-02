package de.kobich.commons.monitor.scanner;

import java.util.EventListener;
import java.util.TimerTask;

import javax.swing.event.EventListenerList;

/**
 * This is an abstract base class for MonitorScanner
 * including standard implementation of some methods.
 */
public abstract class AbstractScanner<Listener extends EventListener> extends TimerTask implements IScanner<Listener> {
	private EventListenerList listenerList;
	private Class<Listener> listenerClass;
	
	/**
	 * Constructor
	 * @param listenerClass the class of the listener
	 */
	protected AbstractScanner(Class<Listener> listenerClass) {
		this.listenerList = new EventListenerList();
		this.listenerClass = listenerClass;
	}
	
	/**
     * Adds a listener 
     * @param l the listener to be added
     */
	public synchronized void addListener(Listener listener) {
		listenerList.add(listenerClass, listener);
	}

	/**
     * Removes a listener 
     * @param l the listener to be removed
     */
	public synchronized void removeListener(Listener listener) {
		listenerList.remove(listenerClass, listener);
	}
	
	/**
	 * Returns the timer task
	 * @return the timer task
	 */
	public TimerTask getTimerTask() {
		return this;
	}
	
	/**
	 * Returns the listener list
	 * @return the listener list
	 */
	protected synchronized Listener[] getListeners() {
		return listenerList.getListeners(listenerClass);
	}
}
