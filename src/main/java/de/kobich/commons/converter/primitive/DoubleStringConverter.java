package de.kobich.commons.converter.primitive;

import de.kobich.commons.converter.IConverter;

public class DoubleStringConverter implements IConverter<Double, String> {

	@Override
	public String convert(Double s) {
		return Double.toString(s);
	}

	@Override
	public Double reconvert(String t) {
		return Double.parseDouble(t);
	}
}
