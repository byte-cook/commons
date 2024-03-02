package de.kobich.commons;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Listener list
 * @author ckorn
 *
 * @param <L> the listener type
 */
public class ListenerList<L> implements Iterable<L> {
	private final Set<L> listeners;

	public ListenerList() {
		this.listeners = new HashSet<L>();
	}

	/**
	 * Adds a listener to the list, if it has not previously been added
	 * @param listener
	 */
	public void addListener(L listener) {
		this.listeners.add(listener);
	}

	/**
	 * Removes a listener from the list, if it has previously been added
	 * @param listener
	 */
	public void removeListener(L listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * Removes all listeners
	 */
	public void removeAllListeners() {
		this.listeners.clear();
	}

	/**
	 * Tests the existence of a listener in the list
	 * @param listener
	 * @return
	 */
	public boolean contains(L listener) {
		return this.listeners.contains(listener);
	}

	/**
	 * Tests the emptiness of the list
	 * @return
	 */
	public boolean isEmpty() {
		return this.listeners.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<L> iterator() {
		return this.listeners.iterator();
	}

	/**
	 * @return the listeners
	 */
	protected Set<L> getListeners() {
		return listeners;
	}

}

