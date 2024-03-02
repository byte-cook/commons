package de.kobich.commons.misc.validate.rule;

import de.kobich.commons.misc.validate.IValidatable;

/**
 * @author ckorn
 */
public interface IValidationRule {
	/**
	 * Returns the rule name
	 * @return
	 */
	public String getName();
	
	/**
	 * Indicates if the text fulfills this rule 
	 * @param text
	 * @return boolean
	 */
	public boolean checkRule(IValidatable validatable);
	
	/**
	 * Returns the failed message
	 * @param text
	 * @return
	 */
	public String getFailedMessage(String text);
	
	/**
	 * Called when the category changes (e.g. file path) 
	 * @param category the new category
	 */
	public void categoryChanged(String category);
}
