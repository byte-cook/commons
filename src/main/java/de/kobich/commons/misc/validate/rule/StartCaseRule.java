package de.kobich.commons.misc.validate.rule;

import de.kobich.commons.misc.validate.IValidatable;


/**
 * Each word begins with upper case, the rest is lower case.
 * @author ckorn
 */
public class StartCaseRule implements IValidationRule {

	public boolean checkRule(IValidatable validatable) {
		String text = validatable.getText();
		String[] words = text.split(" ");
		
		for (String word : words) {
			// skip non-letters, e.g. (['
			word = ValidationRuleUtils.skipNonLetters(word);
			
			// skip empty words
			if (word.isEmpty()) {
				continue;
			}
			
			// first character of each word must be upper case
			char firstChar = word.charAt(0);
			if (Character.isLowerCase(firstChar)) {
				return false;
			}
			// the rest must be lower case
			word = word.substring(1);
			for (char ch : word.toCharArray()) {
				if (Character.isUpperCase(ch)) {
					return false;
				}
			}
		}
		return true;
	}

	public String getName() {
		return "Start Case";
	}

	public String getFailedMessage(String text) {
		return text + " does not start with upper case followed by lower case for each word";
	}

	public void categoryChanged(String category) {
	}
}
