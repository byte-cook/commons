package de.kobich.commons.converter;

import java.util.Arrays;
import java.util.List;


public class ListIndexConverter<T> implements IConverter<T, Integer> {
	private List<T> items;
	
	public ListIndexConverter(T[] items) {
		this.items = Arrays.asList(items);
	}
	public ListIndexConverter(List<T> items) {
		this.items = items;
	}

	@Override
	public Integer convert(T t) {
		return items.indexOf(t);
	}

	@Override
	public T reconvert(Integer index) {
		return items.get(index);
	}
	
}	
