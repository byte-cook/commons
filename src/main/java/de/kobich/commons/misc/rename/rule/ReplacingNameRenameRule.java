package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;

/**
 * Set base name.
 * @author ckorn
 */
public class ReplacingNameRenameRule implements IRenameRule {
	private final String baseName;
	
	/**
	 * Constructor
	 * @param baseName the new base name
	 */
	public ReplacingNameRenameRule(String baseName) {
		this.baseName = baseName;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		renameable.setName(baseName);
		chain.doFilter(renameable);
	}
}
