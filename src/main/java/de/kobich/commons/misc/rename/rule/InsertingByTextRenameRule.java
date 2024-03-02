package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;

/**
 * Inserts text.
 * @author ckorn
 * @deprecated use {@link ReplacingTextRenameRule}
 */
@Deprecated 
public class InsertingByTextRenameRule implements IRenameRule {
	private final RenamePositionType position;
	private final String positionText;
	private final String text;

	/**
	 * Constructor
	 * @param positionText the positionText
	 * @param text the text to insert
	 */
	public InsertingByTextRenameRule(RenamePositionType position, String positionText, String text) {
		this.position = position;
		this.positionText = positionText;
		this.text = text;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();
		
		// set position
		if (RenamePositionType.BEFORE.equals(position)) {
			name = name.replace(positionText, text + positionText);
		}
		else if (RenamePositionType.AFTER.equals(position)) {
			name = name.replace(positionText, positionText + text);
		}

		renameable.setName(name);
		chain.doFilter(renameable);
	}
}
