package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;



/**
 * Crops names.
 * @author ckorn
 */
public class CuttingByIndexRenameRule implements IRenameRule {
	private final RenamePositionType position;
	private final int index;
	
	/**
	 * Constructor
	 * @param position the position
	 * @param index the index to crop
	 */
	public CuttingByIndexRenameRule(RenamePositionType position, int index) {
		this.position = position;
		this.index = index;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();
		
		// set position
		if (RenamePositionType.BEFORE.equals(position)) {
			int endIndex = name.length();
			int beginIndex = index;
			if (beginIndex >= endIndex) {
				beginIndex = endIndex;
			}
			name = name.substring(beginIndex, endIndex);
		}
		else if (RenamePositionType.AFTER.equals(position)) {
			int beginIndex = 0; 
			int endIndex = name.length() - index; //-10
			if (beginIndex >= endIndex) {
				endIndex = beginIndex;
			}
			name = name.substring(beginIndex, endIndex);
		}
		
		renameable.setName(name);
		chain.doFilter(renameable);
	}
}
