package de.kobich.commons.misc.validate.rule;

import de.kobich.commons.misc.validate.IValidatable;

/**
 * Text has auto numbering.
 * @author ckorn
 */
public class AutoNumberingRule implements IValidationRule {
	private int currentNumber;
	private int startAt;
	private int increment;
	private boolean restart4Category;
	
	public AutoNumberingRule(int startAt, int increment) {
		this.startAt = startAt;
		this.currentNumber = startAt;
		this.increment = increment;
		this.restart4Category = true;
	}

	public boolean checkRule(IValidatable validatable) {
		String text = validatable.getText();
		int value = 0;
		try {
			value= Integer.parseInt(text);
		}
		catch (NumberFormatException exc) {
			return false;
		}
		if (currentNumber != value) {
			return false;
		}
		currentNumber += increment;
		
		return true;
	}
	
	public void categoryChanged(String category) {
		if (restart4Category) {
			currentNumber = startAt;
		}
	}

	public String getFailedMessage(String text) {
		return text + " has no consecutively numbering";
	}

	public String getName() {
		return "Numbering";
	}
}
