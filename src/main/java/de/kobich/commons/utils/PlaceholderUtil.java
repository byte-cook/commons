package de.kobich.commons.utils;

/**
 * This util class provides methods to replace placeholders by concrete strings.
 */
public class PlaceholderUtil {
	public static final String PLACEHOLDER_BEGIN = "{";
	public static final String PLACEHOLDER_END = "}";

	/**
	 * Constructor
	 */
	private PlaceholderUtil() {}

	/**
	 * Replaces placeholders by the given text
	 * @param text the text to replace
	 * @param contentPlaceholder the content of the placeholders
	 * @return the replaced string
	 */
	public static String replace(String text, String... contentPlaceholder) {
		return replace(text, PLACEHOLDER_BEGIN, PLACEHOLDER_END, contentPlaceholder);
	}
	
	/**
	 * Replaces placeholders by the given text
	 * @param text the text to replace
	 * @param contentPlaceholder the content of the placeholders
	 * @return the replaced string
	 */
	public static String replace(String text, String placeholderBegin, String placeholderEnd, String... contentPlaceholder) {
		String replacedText = text;
		for (int i = 0; i < contentPlaceholder.length; ++ i) {
			replacedText = replacedText.replace(placeholderBegin + i + placeholderEnd, contentPlaceholder[i]);
		}
		return replacedText;
	}

	/**
	 * Replaces the placeholder by the given replace string
	 * @param text the text to replace
	 * @param placeholder the content of the placeholders
	 * @param replaceString the string to use
	 * @return the replaced string
	 */
	public static String replace(String text, String placeholder, String replaceString) {
		return replace(text, placeholder, PLACEHOLDER_BEGIN, PLACEHOLDER_END, replaceString);
	}


	/**
	 * Replaces the placeholder by the given replace string
	 * @param text the text to replace
	 * @param placeholder the content of the placeholders
	 * @param replaceString the string to use
	 * @return the replaced string
	 */
	public static String replace(String text, String placeholder, String placeholderBegin, String placeholderEnd, String replaceString) {
		String replacedText = text;
		replacedText = replacedText.replaceAll("\\" + placeholderBegin + placeholder + "\\" + placeholderEnd, replaceString);
		return replacedText;
	}
	
	/**
	 * Indicates if the placeholder is in text 
	 * @param text
	 * @param placeholder
	 * @return
	 */
	public static boolean containsPlaceholder(String text, String placeholder) {
		return containsPlaceholder(text, placeholder, PLACEHOLDER_BEGIN, PLACEHOLDER_END);
	}
	
	/**
	 * Indicates if the placeholder is in text
	 * @param text
	 * @param placeholder
	 * @param placeholderBegin
	 * @param placeholderEnd
	 * @return
	 */
	public static boolean containsPlaceholder(String text, String placeholder, String placeholderBegin, String placeholderEnd) {
		String s = placeholderBegin + placeholder + placeholderEnd;
		return text.contains(s);
	}
}
