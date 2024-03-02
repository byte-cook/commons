package de.kobich.commons.collections;


public class TypedKey<T> {
	private final Class<T> type;
	private final Object key;

	public TypedKey(Object key, Class<T> type) {
		this.key = key;
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

	public Object getKey() {
		return key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypedKey<?> other = (TypedKey<?>) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		}
		else if (!type.equals(other.type))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		}
		else if (!key.equals(other.key))
			return false;
		return true;
	}

}
