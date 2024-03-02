package de.kobich.commons.converter.primitive;

import org.apache.log4j.Logger;

import de.kobich.commons.converter.IConverter;

public class IntegerStringConverter implements IConverter<Integer, String> {
	private static final Logger logger = Logger.getLogger(DateStringConverter.class);

	@Override
	public String convert(Integer s) {
		return Integer.toString(s);
	}

	@Override
	public Integer reconvert(String t) {
		try {
			return Integer.parseInt(t);
		}
		catch (NumberFormatException e) {
			logger.warn("Integer cannot be parsed: " + t);
			return null;
		}
	}
}
