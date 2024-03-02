package de.kobich.commons.misc.rename;

/**
 * Represents a file to rename.
 * @author ckorn
 */
public interface IRenameable {
	/**
	 * Returns the name
	 * @return
	 */
	public String getName();
	
	/**
	 * Sets the name
	 * @param name
	 */
	public void setName(String name);

	/**
	 * Returns the original name (= the old name)
	 * @return
	 */
	public String getOriginalName();

	/**
	 * Returns the category, e.g. file directory (allows to restart renaming filters if category changes)
	 * @return the category
	 */
	public String getCategory();
	
	/**
	 * Returns the value of the given attribute or null
	 * @param name
	 * @return
	 */
	public String getAttribute(String attribute);
}
