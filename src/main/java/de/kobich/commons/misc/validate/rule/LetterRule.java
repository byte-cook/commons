package de.kobich.commons.misc.validate.rule;

import de.kobich.commons.misc.validate.IValidatable;


/**
 * Text is letter.
 * @author ckorn
 */
public class LetterRule implements IValidationRule {

	public boolean checkRule(IValidatable validatable) {
		String text = validatable.getText();
		for (char ch : text.toCharArray()) {
			if (!Character.isLetter(ch)) {
				return false;
			}
		}
		return true;
	}

	public String getName() {
		return "Letter";
	}

	public String getFailedMessage(String text) {
		return text + " contains non-letters";
	}

	public void categoryChanged(String category) {
	}
}
