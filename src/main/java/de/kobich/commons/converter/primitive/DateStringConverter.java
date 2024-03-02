package de.kobich.commons.converter.primitive;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import de.kobich.commons.converter.IConverter;

public class DateStringConverter implements IConverter<Date, String> {
	private static final Logger logger = Logger.getLogger(DateStringConverter.class);
	private final List<SimpleDateFormat> formatters;

	public DateStringConverter() {
		this(Arrays.asList("yyyy-MM-dd", "yyyy-MM", "yyyy", "yyyy-MM-dd HH:mm:ss.S"));
	}

	public DateStringConverter(String pattern) {
		this(Collections.singletonList(pattern));
	}

	public DateStringConverter(List<String> patterns) {
		this.formatters = new ArrayList<SimpleDateFormat>();
		for (String pattern : patterns) {
			this.formatters.add(new SimpleDateFormat(pattern));
		}
	}

	@Override
	public String convert(Date s) {
		return formatters.get(0).format(s);
	}

	@Override
	public Date reconvert(String t) {
		for (SimpleDateFormat formatter : formatters) {
			try {
				return formatter.parse(t);
			}
			catch (ParseException exc) {
				logger.warn("Date cannot be parsed: " + t);
			}
		}
		return null;
	}

}
