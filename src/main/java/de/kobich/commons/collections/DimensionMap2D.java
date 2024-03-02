package de.kobich.commons.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import de.kobich.commons.collections.Dimension.DimensionType;

/**
 * A two dimensional map. Each key can be assigned several values.
 * @author ckorn
 *
 * @param <K> key
 * @param <V> value
 */
public class DimensionMap2D<K, V> extends HashMap<K, Dimension<V>> {
	private static final long serialVersionUID = -9130344049287549527L;
	private final DimensionType type;
	
	public DimensionMap2D(DimensionType type) {
		this.type = type;
	}
	
	/**
	 * Adds a value mapped to the key 
	 * If the key is new, a new list will be created.
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addElement(K key, V value) {
		return addAllElements(key, Collections.singletonList(value));
	}
	
	/**
	 * Adds a collection of values mapped to the key
	 * If the key is new, a new list will be created. 
	 * @param key
	 * @param values
	 * @return
	 */
	public boolean addAllElements(K key, Collection<V> values) {
		if (!super.containsKey(key)) {
			Dimension<V> list = Dimension.createInstance(type); 
			super.put(key, list);
		}
		return super.get(key).addAll(values);
	}
	
	/**
	 * Removes one specific value mapped to the key
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean removeElement(K key, V value) {
		return removeAllElements(key, Collections.singletonList(value));
	}
	
	/**
	 * Removes a collection of values mapped to the key
	 * @param key
	 * @param values
	 * @return
	 */
	public boolean removeAllElements(K key, Collection<V> values) {
		if (super.containsKey(key)) {
			return super.get(key).removeAll(values);
		}
		return false;
	}
	
	/**
	 * Removes all values mapped to the key
	 * @param key
	 * @return
	 */
	public boolean removeKey(K key) {
		return super.remove(key) != null;
	}
	
	/**
	 * Returns all values
	 * @return
	 */
	public Collection<V> getAllValues() {
		Collection<V> values = new HashSet<V>();
		for (Dimension<V> v : super.values()) {
			values.addAll(v);
		}
		return values;
	}
}
