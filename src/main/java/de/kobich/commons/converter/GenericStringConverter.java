package de.kobich.commons.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.kobich.commons.converter.primitive.BooleanStringConverter;
import de.kobich.commons.converter.primitive.DateStringConverter;
import de.kobich.commons.converter.primitive.DoubleStringConverter;
import de.kobich.commons.converter.primitive.IntegerStringConverter;
import de.kobich.commons.converter.primitive.LongStringConverter;
import de.kobich.commons.converter.primitive.StringStringConverter;

public class GenericStringConverter implements IConverter<Object, String> {
	private Map<Class<?>, IConverter<?, String>> converters;

	public GenericStringConverter() {
		converters = new HashMap<Class<?>, IConverter<?,String>>();
		converters.put(String.class, new StringStringConverter());
		converters.put(Boolean.class, new BooleanStringConverter());
		converters.put(Date.class, new DateStringConverter());
		converters.put(Double.class, new DoubleStringConverter());
		converters.put(Integer.class, new IntegerStringConverter());
		converters.put(Long.class, new LongStringConverter());
	}

	@SuppressWarnings("unchecked")
	@Override
	public String convert(Object s) {
		Class<?> type = s.getClass();
		if (converters.containsKey(type)) {
			IConverter<Object, String> converter = (IConverter<Object, String>) converters.get(type);
			return converter.convert(s);
		}
		return s.toString();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T reconvert(String t, Class<T> type) {
		if (converters.containsKey(type)) {
			IConverter<Object, String> converter = (IConverter<Object, String>) converters.get(type);
			return type.cast(converter.reconvert(t));
		}
		return null;
	}

	@Override
	public Object reconvert(String t) {
		return null;
	}
	
	public <T> void addConverter(Class<T> type, IConverter<T, String> converter) {
		converters.put(type, converter);
	}
	
	public <T> void removeConverter(Class<T> type, IConverter<T, String> converter) {
		converters.remove(type);
	}

}
