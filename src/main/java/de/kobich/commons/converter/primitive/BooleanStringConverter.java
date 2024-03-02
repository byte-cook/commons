package de.kobich.commons.converter.primitive;

import de.kobich.commons.converter.IConverter;

public class BooleanStringConverter implements IConverter<Boolean, String> {

	public BooleanStringConverter() {
	}

	@Override
	public String convert(Boolean s) {
		return s.toString();
	}

	@Override
	public Boolean reconvert(String t) {
		return Boolean.valueOf(t);
	}

}
