package de.kobich.commons.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import de.kobich.commons.Reject;
import de.kobich.commons.collections.Dimension.DimensionType;

/**
 * A two dimensional map. Each key can be assigned several values.
 * The value can be any object, however not null.
 * @author ckorn
 *
 * @param <K> key
 */
public class DimensionMap2DGeneric<K> extends HashMap<K, Dimension<DimensionMap2DGeneric.GenericValue>> {
	private static final long serialVersionUID = -1825638279335630605L;
	private final DimensionType type;
	
	public DimensionMap2DGeneric(DimensionType type) {
		this.type = type;
	}
	
	/**
	 * Adds a value mapped to the key
	 * If the key is new, a new list will be created.
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addElement(K key, Object value) {
		return addAllElements(key, Collections.singletonList(value));
	}
	
	/**
	 * Adds a collection of values mapped to the key
	 * If the key is new, a new list will be created.
	 * @param key
	 * @param values values must not be null
	 * @return
	 */
	public boolean addAllElements(K key, Collection<?> values) {
		// 1. create generic collection (can cause NullPointerException)
		Dimension<GenericValue> genericValues = Dimension.createInstance(type); 
		for (Object value : values) {
//			if (value != null) {
				genericValues.add(new GenericValue(value));
//			}
		}
		// 2. add collection to this map
		if (!super.containsKey(key)) {
			Dimension<GenericValue> list = Dimension.createInstance(type); 
			super.put(key, list);
		}
		return super.get(key).addAll(genericValues);
	}
	
	/**
	 * Sets a collection of values mapped to the key. 
	 * All previous mapped values to the key will be removed.
	 * @param key
	 * @param values
	 */
	public void setElements(K key, Collection<?> values) {
		Dimension<GenericValue> dim = Dimension.createInstance(type);
		for (Object value : values) {
//			if (value != null) {
				dim.add(new GenericValue(value));
//			}
		}
		super.remove(key);
		super.put(key, dim);
	}
	
	/**
	 * Returns a collection of values mapped to the key or null if this map contains no mapping to the key.
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> Dimension<T> getElements(K key, Class<T> clazz) {
		return getElements(key, clazz, null);
	}
	
	/**
	 * Returns a collection of values mapped to the key. 
	 * If this map contains no mapping to the key and the default value is not null, the default value will be returned.
	 * Otherwise, null will be returned.
	 * @param key
	 * @param clazz
	 * @param defaultValue
	 * @return
	 */
	public <T> Dimension<T> getElements(K key, Class<T> clazz, T defaultValue) {
		if (!super.containsKey(key)) {
			if (defaultValue != null) {
				Dimension<T> dim = Dimension.createInstance(type);
				dim.add(defaultValue);
				return dim;
			}
			return null;
		}
		Dimension<GenericValue> values = super.get(key);
		Dimension<T> result = Dimension.createInstance(type);
		for (GenericValue value : values) {
//			if (value != null) {
			T t = value.getValue(clazz);
			if (t != null) {
				result.add(t);
			}
//			}
		}
		return result;
	}
	
	/**
	 * Removes a value mapped to the key
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean removeElement(K key, Object value) {
		return removeAllElements(key, Collections.singletonList(value));
	}
	
	/**
	 * Removes a collection of values mapped to the key
	 * @param key
	 * @param values
	 * @return
	 */
	public boolean removeAllElements(K key, Collection<?> values) {
		if (super.containsKey(key)) {
			return super.get(key).removeAll(values);
		}
		return false;
	}
	
//	public boolean removeKey(K key) {
//		return super.remove(key) != null;
//	}
	
	/**
	 * Returns all values
	 * @return
	 */
	public Collection<Object> getAllValues() {
		Collection<Object> values = new HashSet<Object>();
		for (Dimension<GenericValue> dimValues : super.values()) {
			for (GenericValue value : dimValues) {
				values.add(value.getValue());
			}
		}
		return values;
	}
	
	/**
	 * Generic value.
	 * @author ckorn
	 */
	protected static final class GenericValue {
		private final Object value;

		public GenericValue(Object value) {
			Reject.ifNull(value);
			this.value = value;
		}

		public Object getValue() {
			return value;
		}
		
		public <T> T getValue(Class<T> type) {
			if (type != null && type.isAssignableFrom(this.getType())) {
				return type.cast(getValue());
			}
			return null;
		}

		public Class<?> getType() {
			return value.getClass();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GenericValue other = (GenericValue) obj;
			if (value == null) {
				if (other.value != null)
					return false;
			}
			else if (!value.equals(other.value))
				return false;
			return true;
		}
	}
}
