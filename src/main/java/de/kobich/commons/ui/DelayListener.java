package de.kobich.commons.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * Collects events of the same type in a specified time period in order to handle them at once. 
 * @param <E> the event
 */
public abstract class DelayListener<E> {
	private static final Logger logger = Logger.getLogger(DelayListener.class);
	private final long delayMillis;
	private final List<E> events;
	private final Executor executor;
	private long lastEventMillis;
	
	public DelayListener(long delay, TimeUnit unit) {
		this.delayMillis = TimeUnit.MILLISECONDS.convert(delay, unit);
		this.events = new ArrayList<E>();
		this.executor = CompletableFuture.delayedExecutor(delay, unit);
	}
	
	public void delayEvent(E event) {
		this.lastEventMillis = System.currentTimeMillis();
		this.events.add(event);
		
		CompletableFuture.runAsync(() -> fireEvent(), executor);
	}
	
	public abstract void handleEvent(List<E> events);
	
	private void fireEvent() {
//		logger.debug(System.currentTimeMillis() - delayMillis - lastEventMillis);
		boolean doit = (System.currentTimeMillis() - delayMillis) >= lastEventMillis;
		if (doit && !events.isEmpty()) {
			// only one thread can handle incoming events
			synchronized (events) {
				if (!events.isEmpty()) {
					logger.debug("do handle event");
					// use a copy in order to avoid synchronization (it guarantees that all handled events get deleted)  
					List<E> copy = new ArrayList<E>(events); 
					handleEvent(copy);
					events.removeAll(copy);
				}
			}
		}
		else {
			logger.debug("do nothing");
		}
	}
	
}
