package de.kobich.commons.misc.validate.rule;

import de.kobich.commons.misc.validate.IValidatable;


/**
 * Text is upper case.
 * @author ckorn
 */
public class UpperCaseRule implements IValidationRule {

	public boolean checkRule(IValidatable validatable) {
		String text = validatable.getText();
		for (char ch : text.toCharArray()) {
			if (Character.isLowerCase(ch)) {
				return false;
			}
		}
		return true;
	}

	public String getName() {
		return "Upper Case";
	}

	public String getFailedMessage(String text) {
		return text + " contains lower case";
	}

	public void categoryChanged(String category) {
	}
}
