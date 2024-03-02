package de.kobich.commons.collections;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
 * @param <K> the key to identify a list of element objects
 * @param <V> the value as list
 */
@Deprecated
public class TwoDimensionalMap<K, E> extends Hashtable<K, List<E>> implements Map<K, List<E>> {
	private static final long serialVersionUID = 1L;
	private final boolean allowDuplicates;

	public TwoDimensionalMap() {
		super();
		this.allowDuplicates = true;
	}
	public TwoDimensionalMap(boolean allowDuplicates) {
		super();
		this.allowDuplicates = allowDuplicates;
	}
	
	/**
	 * Adds a value element into the list mapped to the key parameter. 
	 * If the key is new, a new list will be created.
	 * @param key the key of the value
	 * @param value the value element to add
	 * @return the value element
	 */
	public synchronized E addElement(K key, E value) {
		if (!this.containsKey(key)) {
			List<E> list = new ArrayList<E>();
			super.put(key, list);
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
	 * Adds a list of value elements into the list mapped to the key parameter. 
	 * If the key is new, a new list will be created.
	 * @param key the key of the value
	 * @param values the list of value elements to add
	 * @return the list of value element
	 */
	public synchronized List<E> addAllElements(K key, List<E> values) {
		if (!this.containsKey(key)) {
			put(key, new ArrayList<E>());
		}
		if (!allowDuplicates) {
			List<E> list = this.get(key);
			for (E e : values) {
				if (!list.contains(e)) {
					list.add(e);
				}
			}
		}
		else {
			this.get(key).addAll(values);
		}
    	return values;
    }
	
	public synchronized List<E> setElements(K key, List<E> values) {
		super.remove(key);
		return addAllElements(key, values);
	}
}
