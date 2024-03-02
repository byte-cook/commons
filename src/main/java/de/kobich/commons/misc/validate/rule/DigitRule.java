package de.kobich.commons.misc.validate.rule;

import de.kobich.commons.misc.validate.IValidatable;


/**
 * Text is lower case.
 * @author ckorn
 */
public class DigitRule implements IValidationRule {

	public boolean checkRule(IValidatable validatable) {
		String text = validatable.getText();
		for (char ch : text.toCharArray()) {
			if (!Character.isDigit(ch)) {
				return false;
			}
		}
		return true;
	}

	public String getName() {
		return "Digit";
	}

	public String getFailedMessage(String text) {
		return text + " contains non-digits";
	}

	public void categoryChanged(String category) {
	}
}
