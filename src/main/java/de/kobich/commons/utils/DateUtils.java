package de.kobich.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.kobich.commons.Reject;

public class DateUtils {
	public final static String DEFAULT_DATEFORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

	/**
	 * Normalizes a date by setting its time
	 * @param date
	 * @return
	 */
	public static Date normalizeDate(Date date, int hour, int min, int sec, int ms) {
		Reject.ifNull(date);

		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, min);
		c.set(Calendar.SECOND, sec);
		c.set(Calendar.MILLISECOND, ms);
		return c.getTime();
	}
	
	/**
	 * Creates a date 
	 * @param year
	 * @param month
	 * @param day
	 * @return date 
	 */
	public static Date createDate(int year, int month, int day) {
		return createDate(year + "-" + month + "-" + day + " 00:00:00:0000", DEFAULT_DATEFORMAT);
	}
	
	/**
	 * Creates a date 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param min
	 * @return date 
	 */
	public static Date createDate(int year, int month, int day, int hour, int min) {
		return createDate(year + "-" + month + "-" + day + " " + hour + ":" + min + ":00:0000", DEFAULT_DATEFORMAT);
	}

	/**
	 * Creates a date 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param min
	 * @param sec
	 * @param ms
	 * @return date 
	 */
	public static Date createDate(int year, int month, int day, int hour, int min, int sec, int ms) {
		return createDate(year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec + ":" + ms, DEFAULT_DATEFORMAT);
	}

	/**
	 * Creates a date
	 * @param date
	 * @param pattern
	 * @return date
	 */
	public static Date createDate(String date, String pattern) {
		Reject.ifNull(date);
		Reject.ifNull(pattern);

		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date createdDate = format.parse(date);
			return createdDate;
		}
		catch (ParseException e) {
			throw new IllegalArgumentException();
		}

	}

}
