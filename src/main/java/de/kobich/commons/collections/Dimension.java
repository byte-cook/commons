package de.kobich.commons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Dimension<E> implements Collection<E> {
	public static enum DimensionType { LIST, SET }
	private final Collection<E> collection;
	private final DimensionType type;
	
	public static <E> Dimension<E> createInstance(DimensionType type) {
		switch (type) {
			case LIST:
				return new Dimension<E>(new ArrayList<E>(), type);
			case SET:
				return new Dimension<E>(new HashSet<E>(), type);
		}
		throw new IllegalArgumentException("Dimension type not found: " + type);
	}
	
	private Dimension(Collection<E> collection, DimensionType type) {
		this.collection = collection;
		this.type = type;
	}
	
	public List<E> asList() {
		if (this.collection instanceof List) {
			return (List<E>) collection;
		}
		throw new IllegalStateException("The current dimension type does not support this method: " + this.type);
	}

	public Set<E> asSet() {
		if (this.collection instanceof Set) {
			return (Set<E>) collection;
		}
		throw new IllegalStateException("The current dimension type does not support this method: " + this.type);
	}
	
	public DimensionType getDimensionType() {
		return this.type;
	}
	
	public E get(int index) {
		return this.asList().get(index);
	}

	@Override
	public boolean add(E e) {
		return collection.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return collection.addAll(c);
	}

	@Override
	public void clear() {
		collection.clear();
	}

	@Override
	public boolean contains(Object o) {
		return collection.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return collection.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return collection.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return collection.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return collection.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return collection.retainAll(c);
	}

	@Override
	public int size() {
		return collection.size();
	}

	@Override
	public Object[] toArray() {
		return collection.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return collection.toArray(a);
	}
	
	
}
