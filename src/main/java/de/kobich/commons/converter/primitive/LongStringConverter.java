package de.kobich.commons.converter.primitive;

import de.kobich.commons.converter.IConverter;

public class LongStringConverter implements IConverter<Long, String> {

	@Override
	public String convert(Long s) {
		return Long.toString(s);
	}

	@Override
	public Long reconvert(String t) {
		return Long.parseLong(t);
	}
}
