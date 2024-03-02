package de.kobich.commons.utils;

public class StringUtils {
	/**
	 * Checks if a String is empty ("") or null
	 * @param str
	 * @return
	 * @deprecated use String.isEmpty()
	 */
	@Deprecated
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	/**
	 * Checks if a String is whitespace, empty ("") or null
	 * @param str
	 * @return
	 * @deprecated use org.apache.commons.lang3.StringUtils.isBlank
	 */
	@Deprecated
	public static boolean isBlank(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0)) {
			return true;
		}
		for (int i = 0; i < strLen; ++i) {
			if (!(Character.isWhitespace(str.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks whether the given String has actual text.
	 * @param str
	 * @return
	 * @deprecated use org.apache.commons.lang3.StringUtils.isNotBlank
	 */
	@Deprecated
	public static boolean hasText(String str) {
		return !isBlank(str);
	}

	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = "";
		}

		StringBuffer buf = new StringBuffer();
		int arraySize = array.length;
		for (int i = 0; i < arraySize; ++i) {
			if (i > 0) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}
}
