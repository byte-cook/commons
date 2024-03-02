package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;



/**
 * Crops names.
 * @author ckorn
 */
public class CuttingFromToIndexRenameRule implements IRenameRule {
	private final int beginIndex;
	private final int endIndex;
	
	/**
	 * Constructor
	 * @param from the from index
	 * @param to the to index
	 */
	public CuttingFromToIndexRenameRule(int from, int to) {
		this.beginIndex = from;
		this.endIndex = to;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();
		
		int begin = this.beginIndex;
		int end = this.endIndex;
		
		if (begin < 0) {
			begin = 0;
		}
		if (end > name.length()) {
			end = name.length();
		}
		if (begin > end) {
			chain.doFilter(renameable);
			return;
		}
		name = name.substring(0, begin) + name.substring(end, name.length());
		
		renameable.setName(name);
		chain.doFilter(renameable);
	}
}
