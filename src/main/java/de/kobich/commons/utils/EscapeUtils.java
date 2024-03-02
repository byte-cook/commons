package de.kobich.commons.utils;


public class EscapeUtils {
	/**
	 * Escapes search text
	 * @param text
	 * @return
	 * @deprecated use IntegrationUtils
	 */
	@Deprecated
	public static String escapeSearchText(String text) {
		if (StringUtils.hasText(text)) {
			text = text.replace("*", "%");
			text = text.replace("?", "_");
		}
		return text;
	}
	
	/**
	 * Escapes csv text
	 * @param text
	 * @return
	 */
	public static String escapeCSV(String text) {
		text = text.replace("\"", "\"\"");
		return "\"" + text + "\"";
	}
}
