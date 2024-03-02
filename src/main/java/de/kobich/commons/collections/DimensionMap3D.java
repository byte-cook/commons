package de.kobich.commons.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import de.kobich.commons.collections.Dimension.DimensionType;

/**
 * A three dimensional map. Two keys can be assigned several values.
 * @author ckorn
 *
 * @param <K1> key 1
 * @param <K2> key 2
 * @param <V> value
 */
public class DimensionMap3D<K1, K2, V> extends HashMap<K1, DimensionMap2D<K2, V>> { 
	private static final long serialVersionUID = 1318963898448119818L;
	private final DimensionType type;
	
	public DimensionMap3D(DimensionType type) {
		this.type = type;
	}
	
	/**
	 * Adds a value mapped to two keys
	 * @param key1
	 * @param key2
	 * @param value
	 * @return
	 */
	public boolean addElement(K1 key1, K2 key2, V value) {
		return addAllElements(key1, key2, Collections.singletonList(value));
	}
	
	/**
	 * Adds a collection of values mapped to two keys
	 * @param key1
	 * @param key2
	 * @param values
	 * @return
	 */
	public boolean addAllElements(K1 key1, K2 key2, Collection<V> values) {
		if (!super.containsKey(key1)) {
			DimensionMap2D<K2, V> map2D = new DimensionMap2D<K2, V>(type);
			super.put(key1, map2D);
		}
		DimensionMap2D<K2, V> map2D = super.get(key1);
		return map2D.addAllElements(key2, values);
	}
	
	/**
	 * Removes a value mapped to two keys
	 * @param key1
	 * @param key2
	 * @param value
	 * @return
	 */
	public boolean removeElement(K1 key1, K2 key2, V value) {
		return removeAllElements(key1, key2, Collections.singletonList(value));
	}
	
	/**
	 * Removes a collection of values mapped to two keys
	 * @param key1
	 * @param key2
	 * @param values
	 * @return
	 */
	public boolean removeAllElements(K1 key1, K2 key2, Collection<V> values) {
		if (super.containsKey(key1)) {
			DimensionMap2D<K2, V> map2D = super.get(key1);
			return map2D.removeAllElements(key2, values);
		}
		return false;
	}
	
	/**
	 * Removes all values mapped to the second key
	 * @param key1
	 * @param key2
	 * @return
	 */
	public boolean removeKey(K1 key1, K2 key2) {
		if (super.containsKey(key1)) {
			DimensionMap2D<K2, V> map2D = super.get(key1);
			return map2D.remove(key2) != null;
		}
		return false;
	}
	
	/**
	 * Removes all values mapped to the first key
	 * @param key1
	 * @return
	 */
	public boolean removeKey(K1 key1) {
		return super.remove(key1) != null;
	}
	
	/**
	 * Returns all values
	 * @return
	 */
	public Collection<V> getAllValues() {
		Collection<V> values = new HashSet<V>();
		for (K1 key1 : super.keySet()) {
			DimensionMap2D<K2, V> map2D = super.get(key1);
			values.addAll(map2D.getAllValues());
		}
		return values;
	}
}
