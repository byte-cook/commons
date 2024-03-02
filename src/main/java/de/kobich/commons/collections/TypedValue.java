package de.kobich.commons.collections;

public class TypedValue<T> {
	private final Class<T> type;
	private final T value;

	public TypedValue(T value, Class<T> type) {
		this.value = value;
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
	

}
