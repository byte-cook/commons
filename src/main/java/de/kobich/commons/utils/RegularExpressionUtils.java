package de.kobich.commons.utils;

public class RegularExpressionUtils {
	private static final String ESCAPE = "\\";
	
	/**
	 * Escapes regular expression patterns
	 * @param pattern
	 * @return the pattern
	 */
	public static String escapePattern(String pattern) {
		// first replace escape char itself
		pattern = pattern.replace(ESCAPE, ESCAPE + ESCAPE);
		// second replace other regular expression characters
		String[] specialChars = {"$", "*", ".", "?", "+", "-", "(", ")", "[", "]", "{", "}", ":", "/"};
		for (String specialChar : specialChars) {
			pattern = pattern.replace(specialChar, ESCAPE + specialChar);
		}
		return pattern;
	}
}
