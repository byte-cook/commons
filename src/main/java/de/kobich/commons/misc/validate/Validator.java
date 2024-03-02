package de.kobich.commons.misc.validate;

import java.util.List;

import org.apache.log4j.Logger;

import de.kobich.commons.misc.validate.ValidateMessage.MessageType;
import de.kobich.commons.misc.validate.rule.IValidationRule;
import de.kobich.commons.ui.callback.ICallback;


/**
 * Defines methods to validate.
 * @author ckorn
 */
public class Validator {
	private static final Logger logger = Logger.getLogger(Validator.class);
	
	/**
	 * Validates items
	 * @param request
	 * @return response
	 */
	public static boolean validate(List<IValidatable> validatables, List<IValidationRule> rules, ICallback<ValidateMessage, Void> callback) {
		boolean state = true;
		String lastCategory = "";
		for (IValidatable validatable : validatables) {
			String category = validatable.getCategory();
			if (!lastCategory.equals(category)) {
				lastCategory = validatable.getCategory();
				for (IValidationRule rule : rules) {
					rule.categoryChanged(category);
				}
			}

			for (IValidationRule rule : rules) {
				state = rule.checkRule(validatable);
				if (!state) {
					String msg = rule.getFailedMessage(validatable.getId() + ": \"" + validatable.getText() + "\"");
					ValidateMessage validateMessage = new ValidateMessage(MessageType.ERROR, msg);
					callback.execute(validateMessage);
					
					logger.debug(msg);
					break;
				}
				else {
					logger.debug(validatable + " succeded for " + rule.getName());
				}
			}
		}
		return state;
	}
	
}
