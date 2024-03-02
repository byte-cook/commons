package de.kobich.commons.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class TimeUtils {
	public static final String DEFAULT_FORMAT = "HH:mm:ss:SSS";
	
	public static String format(long millis) {
		return format(millis, DEFAULT_FORMAT);
	}
	public static String format(long millis, String format) {
		StringBuffer buffer = new StringBuffer();
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		buffer.append(df.format(new Date(millis)));
		return buffer.toString();
	}
}
