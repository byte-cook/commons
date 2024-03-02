package de.kobich.commons.collections;

import java.util.Hashtable;
import java.util.Map;

/**
 * This class represents a three-dimensional map.
 * @author ckorn
 *
 * @param <K>  the key
 * @param <E>  value for key as map (2. Dimension)
 * @param <N>  value for 2. Dimension map (3. Dimension)
 */
@Deprecated
public class ThreeDimensionalMap<K, M, E> extends Hashtable<K, Map<M, E>> implements Map<K, Map<M, E>> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ThreeDimensionalMap() {
		super();
	}
	
	/**
	 * Adds a value element into the map,  mapped to the key parameter. 
	 * If the key is new, a new map will be created.
	 * @param key the key of the value
	 * @param value the value element to add
	 * @return the value element
	 */
	public synchronized E addElement(K key, M valuemap, E value) {
		if (!this.containsKey(key)) {
			Map<M, E> map = new Hashtable<M, E>();
			super.put(key, map);
		}
		Map<M, E> map = this.get(key);
		map.put(valuemap, value);
    	return value;
    }
	
	public E getValue(K key, M valueMap){
		Map<M, E> map = this.get(key);
		E value = map.get(valueMap);
		return value;
	}
	
	public boolean existValue (K key, M valueMap){
		boolean result;
		result =  this.containsKey(key);
		if(result){
			Map<M, E> map = this.get(key);
			result =map.containsKey(valueMap);
		}
		return result;
	}
	/**
	 * Adds a list of value elements into the list mapped to the key parameter. 
	 * If the key is new, a new list will be created.
	 * @param key the key of the value
	 * @param values the list of value elements to add
	 * @return the list of value element
	 *
	public synchronized List<E> addAllElements(K key, M valuemap, List<E> values) {
		if (!this.containsKey(key)) {
			Map<M, E> map = new Hashtable<M, E>();
			super.put(key, map);
		}
		this.get(key).addAll(values);
    	return values;
    }*/
}
