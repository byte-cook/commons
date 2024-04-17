package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;

/**
 * Fills text.
 */
public class FillRenameRule implements IRenameRule {
	private final RenamePositionType position;
	private final char fillCharacter;
	private final int digits;

	/**
	 * Constructor
	 * @param position the position
	 * @param fillCharacter the text to insert
	 * @param digits the number of digits
	 */
	public FillRenameRule(RenamePositionType position, char fillCharacter, int digits) {
		this.position = position;
		this.fillCharacter = fillCharacter;
		this.digits = digits;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();

		// set position
		while (name.length() < digits) {
			if (RenamePositionType.BEFORE.equals(position)) {
				name = fillCharacter + name;
			}
			else if (RenamePositionType.AFTER.equals(position)) {
				name = name + fillCharacter;
			}
		}

		renameable.setName(name);
		chain.doFilter(renameable);
	}
}
