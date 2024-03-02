package de.kobich.commons.misc.validate;

/**
 * Represents a text to validate.
 * @author ckorn
 */
public interface IValidatable {
	/**
	 * Returns the id of this object
	 * @return
	 */
	public String getId();
	
	/**
	 * Returns the text to validate
	 * @return
	 */
	public String getText();
	
	/**
	 * Returns the category, e.g. file directory (allows to restart renaming filters if category changes)
	 * @return the category
	 */
	public String getCategory();
}
