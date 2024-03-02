package de.kobich.commons;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.kobich.commons.collections.Dimension.DimensionType;
import de.kobich.commons.collections.DimensionMap2D;

/**
 * Listener list with support of key types.
 * @author ckorn
 *
 * @param <K> the key type
 * @param <L> the listener type
 */
public class KeyListenerList<K, L> extends ListenerList<L> implements Iterable<L> {
	private final DimensionMap2D<K, L> listenersMap;

	public KeyListenerList() {
		this.listenersMap = new DimensionMap2D<K, L>(DimensionType.SET);
	}

	/**
	 * Adds a listener to the list registered for the given key
	 * @param listener
	 */
	public void addListener(K key, L listener) {
		this.listenersMap.addElement(key, listener);
	}
	
	/**
	 * Returns the listeners registered for the given key
	 * @param key
	 * @return
	 */
	public Set<L> getListeners(K key) {
		Set<L> listeners = new HashSet<L>();
		listeners.addAll(this.getListeners());
		listeners.addAll(listenersMap.get(key));
		return listeners;
	}

	/**
	 * Removes a listener from the list, if it has previously been added
	 * @param listener
	 */
	public void removeListener(K key, L listener) {
		this.listenersMap.removeElement(key, listener);
	}
	
	@Override
	public void removeAllListeners() {
		super.removeAllListeners();
		this.listenersMap.clear();
	}

	@Override
	public boolean contains(L listener) {
		return super.contains(listener) || this.listenersMap.containsValue(listener);
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty() && this.listenersMap.isEmpty();
	}

	@Override
	public Iterator<L> iterator() {
		Set<L> it = new HashSet<L>();
		it.addAll(super.getListeners());
		it.addAll(listenersMap.getAllValues());
		return it.iterator();
	}

}

