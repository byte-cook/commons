package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;



/**
 * Set extension.
 * @author ckorn
 */
@Deprecated
public class ExtensionRenameRule implements IRenameRule {
	private final String extension;
	
	/**
	 * Constructor
	 * @param extension the new extension
	 */
	public ExtensionRenameRule(String extension) {
		this.extension = extension;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		FileRenameable fileRenameable = new FileRenameable(renameable);
		fileRenameable.setExtension(extension);
		chain.doFilter(renameable);
	}
}
