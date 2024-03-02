package de.kobich.commons.converter.primitive;

import de.kobich.commons.converter.IConverter;

public class StringStringConverter implements IConverter<String, String> {

	public StringStringConverter() {
	}

	@Override
	public String convert(String s) {
		return s;
	}

	@Override
	public String reconvert(String t) {
		return t;
	}

}
