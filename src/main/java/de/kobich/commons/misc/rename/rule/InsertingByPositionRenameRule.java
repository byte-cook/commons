package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;



/**
 * Inserts text.
 * @author ckorn
 */
public class InsertingByPositionRenameRule implements IRenameRule {
	private final RenamePositionType position;
	private final String text;

	/**
	 * Constructor
	 * @param position the position
	 * @param text the text to insert
	 */
	public InsertingByPositionRenameRule(RenamePositionType position, String text) {
		this.position = position;
		this.text = text;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();

		// set position
		if (RenamePositionType.BEFORE.equals(position)) {
			name = text + name;
		}
		else if (RenamePositionType.AFTER.equals(position)) {
			name = name + text;
		}

		renameable.setName(name);
		chain.doFilter(renameable);
	}
}
