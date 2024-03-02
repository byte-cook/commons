package de.kobich.commons.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class ConverterUtils {
	/**
	 * Checks if the value type is compatible to the clazz type
	 * @param value
	 * @param clazz
	 */
	public static boolean checkType(String value, Class<?> clazz) {
		return getObjectValue(value, clazz) != null;
	}
	
	/**
	 * Returns a value object
	 * @param value
	 * @param clazz
	 * @return
	 */
	public static <T> T getObjectValue(String value, Class<T> clazz) {
		Object obj = null;
		if (String.class.equals(clazz)) {
			obj = value;
		}
		else if (Boolean.class.equals(clazz)) {
			obj = Boolean.parseBoolean(value);
		}
		else if (Integer.class.equals(clazz)) {
			try {
				obj = Integer.parseInt(value);
			}
			catch (NumberFormatException exc) {
			}
		}
		else if (Long.class.equals(clazz)) {
			try {
				obj = Long.parseLong(value);
			}
			catch (NumberFormatException exc) {
			}
		}
		else if (Double.class.equals(clazz)) {
			try {
				obj = Double.parseDouble(value);
			}
			catch (NumberFormatException exc) {
			}
		}
		else if (File.class.equals(clazz)) {
			obj = new File(value);
		}
		else if (URL.class.equals(clazz)) {
			try {
				obj = new URL(value);
			}
			catch (MalformedURLException exc) {
			}
		}
		else if (Date.class.equals(clazz)) {
			try {
				obj = DateUtils.createDate(value, "yyyy-MM-dd");
			}
			catch (IllegalArgumentException exc) {
			}
		}
		return clazz.cast(obj);
	}
}
