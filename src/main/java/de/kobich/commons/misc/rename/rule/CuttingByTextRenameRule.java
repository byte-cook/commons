package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;



/**
 * Crops names until a given text. The text itself remains.
 * @author ckorn
 */
public class CuttingByTextRenameRule implements IRenameRule {
	private final RenamePositionType position;
	private final String text;
	
	/**
	 * Constructor
	 * @param position the position
	 * @param text the text marking the end of cropping
	 */
	public CuttingByTextRenameRule(RenamePositionType position, String text) {
		this.position = position;
		this.text = text;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		if ("".equals(text)) {
			chain.doFilter(renameable);
			return;
		}
		
		String name = renameable.getName();

		// set position
		if (RenamePositionType.BEFORE.equals(position)) {
			int index = name.indexOf(text);
			if (index != -1) {
				index += text.length();
				int endIndex = name.length();
				name = name.substring(index, endIndex);
			}
		}
		else if (RenamePositionType.AFTER.equals(position)) {
			int index = name.lastIndexOf(text);
			if (index != -1) {
				name = name.substring(0, index);
			}
		}
		renameable.setName(name);
		chain.doFilter(renameable);
	}
}
