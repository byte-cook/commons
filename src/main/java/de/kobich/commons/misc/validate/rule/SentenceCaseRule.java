package de.kobich.commons.misc.validate.rule;

import de.kobich.commons.misc.validate.IValidatable;


/**
 * Text begins with upper case, the rest is lower case.
 * @author ckorn
 */
public class SentenceCaseRule implements IValidationRule {

	public boolean checkRule(IValidatable validatable) {
		String text = validatable.getText();

		// skip non-letters, e.g. ([�
		text = ValidationRuleUtils.skipNonLetters(text);

		// skip empty words
		if (text.isEmpty()) {
			return true;
		}

		// first character must be upper case
		char firstChar = text.charAt(0);
		if (!Character.isUpperCase(firstChar)) {
			return false;
		}
		// the rest must be lower case
		text = text.substring(1);
		for (char ch : text.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				return false;
			}
		}
		return true;
	}

	public String getName() {
		return "Sentence Case";
	}

	public String getFailedMessage(String text) {
		return text + " does not start with upper case followed by lower case";
	}

	public void categoryChanged(String category) {
	}
}
