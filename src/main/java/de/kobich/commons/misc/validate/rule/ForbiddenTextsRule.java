package de.kobich.commons.misc.validate.rule;

import de.kobich.commons.misc.validate.IValidatable;


/**
 * Path element is upper case.
 * @author ckorn
 */
public class ForbiddenTextsRule implements IValidationRule {
	private String[] forbiddenTexts;
	
	public ForbiddenTextsRule(String[] forbiddenTexts) {
		this.forbiddenTexts = forbiddenTexts;
	}

	public boolean checkRule(IValidatable validatable) {
		String text = validatable.getText();
		for (String forbiddenText : forbiddenTexts) {
			if (text.toLowerCase().contains(forbiddenText.toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	public String getName() {
		return "Forbidden Text";
	}

	public String getFailedMessage(String text) {
		return text + " contains forbidden text";
	}

	/**
	 * @return the forbiddenTexts
	 */
	public String[] getForbiddenTexts() {
		return forbiddenTexts;
	}

	public void categoryChanged(String category) {
	}
}
