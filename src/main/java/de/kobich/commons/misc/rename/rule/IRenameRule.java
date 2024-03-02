package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;


/**
 * Rename filter.
 * @author ckorn
 */
public interface IRenameRule {
	/**
	 * Rename the file
	 * @param renameable 
	 * @param chain
	 */
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged);
	
	/**
	 * Called when the category changes (e.g. file path) 
	 * @param category the new category
	 */
//	public void categoryChanged(String directory);
}
