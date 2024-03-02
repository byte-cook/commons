package de.kobich.commons.collections;

import java.util.Hashtable;

public class GenericMap<K> extends Hashtable<K, GenericMap<K>.GenericValue> {
	private static final long serialVersionUID = -3457155171920443015L;

	public void addGenericElement(K key, Object value) {
		super.put(key, new GenericValue(value));
	}
	
	public void addAllGenericElements(GenericMap<K> map) {
		super.putAll(map);
	}
	
	public <T> T getGenericElement(K key, Class<T> clazz) {
		return getGenericElement(key, clazz, null);
	}
	
	public <T> T getGenericElement(K key, Class<T> clazz, T defaultValue) {
		if (!super.containsKey(key)) {
			return defaultValue;
		}
		GenericValue value = super.get(key);
//		if (clazz.isAssignableFrom(value.getType())) {
			return clazz.cast(value.getValue());
//		}
//		return null;
	}
	
	protected final class GenericValue {
		private final Object value;
		private final Class<?> type;

		public GenericValue(Object value) {
			this.value = value;
			this.type = value.getClass();
		}

		public Class<?> getType() {
			return type;
		}

		public Object getValue() {
			return value;
		}
	}
}
