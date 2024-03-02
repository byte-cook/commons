package de.kobich.commons.collections;

import java.util.Hashtable;
import java.util.Map;

public class TypedMap extends Hashtable<TypedKey<?>, TypedValue<?>> implements Map<TypedKey<?>, TypedValue<?>> {
	private static final long serialVersionUID = -3457155171920443015L;

	public TypedMap() {
	}
	
	public <T> void addElement(TypedKey<T> key, T value) {
		super.put(key, new TypedValue<T>(value, key.getType()));
	}
	
	public <T> T getElement(TypedKey<T> key) {
		if (!super.containsKey(key)) {
			return null;
		}
		TypedValue<?> value = super.get(key);
		if (value.getType().equals(key.getType())) {
			return key.getType().cast(value.getValue());
		}
		throw new IllegalArgumentException("Illegal key type");
	}
}
