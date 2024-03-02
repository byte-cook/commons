package de.kobich.commons.misc.validate.rule;

import de.kobich.commons.misc.validate.IValidatable;


/**
 * Text is lower case.
 * @author ckorn
 */
public class LowerCaseRule implements IValidationRule {

	public boolean checkRule(IValidatable validatable) {
		String text = validatable.getText();
		for (char ch : text.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				return false;
			}
		}
		return true;
	}

	public String getName() {
		return "Lower Case";
	}

	public String getFailedMessage(String text) {
		return text + " contains upper case";
	}

	public void categoryChanged(String category) {
	}
}
