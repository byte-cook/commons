package de.kobich.commons.collections;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a two-dimensional map.<br>
 * <br>
 * <b>Example:</b><br>
 * Think on a 2 dimensional array<br>
 * <pre>
 * e.g. String[][]	<br>
 * 1	a<br>
 *  	b<br>
 *  	c<br>
 * 2	a<br>
 *  	b<br>
 * ...<br>
 * </pre>
 * 
 * @author ckorn
 * @param <K> the key to identify a set of element objects
 * @param <V> the value as set
 */
public class TwoDimensionalSetMap<K, E> extends Hashtable<K, Set<E>> implements Map<K, Set<E>> {
	private static final long serialVersionUID = 1L;
	private final boolean allowDuplicates;

	public TwoDimensionalSetMap() {
		super();
		this.allowDuplicates = true;
	}
	public TwoDimensionalSetMap(boolean allowDuplicates) {
		super();
		this.allowDuplicates = allowDuplicates;
	}
	
	/**
	 * Adds a value element into the set mapped to the key parameter. 
	 * If the key is new, a new set will be created.
	 * @param key the key of the value
	 * @param value the value element to add
	 * @return the value element
	 */
	public synchronized E addElement(K key, E value) {
		if (!this.containsKey(key)) {
			Set<E> set = new HashSet<E>();
			super.put(key, set);
		}
		if (!allowDuplicates) {
			if (!this.get(key).contains(value)) {
				this.get(key).add(value);
			}
		}
		else {
			this.get(key).add(value);
		}
    	return value;
    }
	
	/**
	 * Adds a set of value elements into the set mapped to the key parameter. 
	 * If the key is new, a new set will be created.
	 * @param key the key of the value
	 * @param values the set of value elements to add
	 * @return the set of value element
	 */
	public synchronized Set<E> addAllElements(K key, Set<E> values) {
		if (!this.containsKey(key)) {
			put(key, new HashSet<E>());
		}
		if (!allowDuplicates) {
			if (!this.get(key).containsAll(values)) {
				this.get(key).addAll(values);
			}
		}
		else {
			this.get(key).addAll(values);
		}
    	return values;
    }
}
