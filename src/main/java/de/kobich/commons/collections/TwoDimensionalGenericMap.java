package de.kobich.commons.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
public class TwoDimensionalGenericMap<K> extends TwoDimensionalMap<K, TwoDimensionalGenericMap<K>.GenericValue> {
	private static final long serialVersionUID = -3457155171920443015L;

	public void addGenericElement(K key, Object value) {
		super.addElement(key, new GenericValue(value));
	}
	
	public void setGenericElements2(K key, List<Object> values) {
		List<GenericValue> genericValues = new ArrayList<TwoDimensionalGenericMap<K>.GenericValue>(values.size());
		for (Object value : values) {
			genericValues.add(new GenericValue(value));
		}
		super.setElements(key, genericValues);
	}
	
	public <T> void setGenericElements(K key, List<T> values) {
		List<GenericValue> genericValues = new ArrayList<TwoDimensionalGenericMap<K>.GenericValue>(values.size());
		for (T value : values) {
			genericValues.add(new GenericValue(value));
		}
		super.setElements(key, genericValues);
	}
	
	public <T> List<T> getGenericElement(K key, Class<T> clazz) {
		return getGenericElement(key, clazz, null);
	}
	
	public <T> List<T> getGenericElement(K key, Class<T> clazz, T defaultValue) {
		if (!super.containsKey(key)) {
			return Collections.singletonList(defaultValue);
		}
		List<GenericValue> values = super.get(key);
		List<T> result = new ArrayList<T>(values.size());
		for (GenericValue value : values) {
			T t = clazz.cast(value.getValue());
			result.add(t);
		}
		return result;
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
