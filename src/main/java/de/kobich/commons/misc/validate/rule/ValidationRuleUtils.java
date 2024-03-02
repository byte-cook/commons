package de.kobich.commons.misc.validate.rule;

public class ValidationRuleUtils {
	/**
	 * Returns the text without non letters in the beginning, e.g. "(hello)" -> "hello)"
	 * @param text
	 * @return
	 */
	public static String skipNonLetters(String text) {
		if (text.isEmpty()) {
			return text;
		}
		
		while (!Character.isLetter(text.charAt(0))) {
			text = text.substring(1);
			if (text.isEmpty()) {
				break;
			}
		}
		return text;
	}
}
