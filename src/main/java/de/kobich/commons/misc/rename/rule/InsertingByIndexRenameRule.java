package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;

/**
 * Inserts text.
 * @author ckorn
 */
public class InsertingByIndexRenameRule implements IRenameRule {
	private final int index;
	private final String text;

	/**
	 * Constructor
	 * @param index the index
	 * @param text the text to insert
	 */
	public InsertingByIndexRenameRule(int index, String text) {
		this.index = index;
		this.text = text;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();
		
		int indexTmp = this.index;
		if (indexTmp < 0) {
			indexTmp = 0;
		}
		if (indexTmp > name.length()) {
			indexTmp = name.length();
		}
		name = name.substring(0, indexTmp) + text + name.substring(indexTmp, name.length());

		renameable.setName(name);
		chain.doFilter(renameable);
	}

}
