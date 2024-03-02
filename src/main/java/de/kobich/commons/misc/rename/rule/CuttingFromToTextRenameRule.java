package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;



/**
 * Crops names.
 * @author ckorn
 */
public class CuttingFromToTextRenameRule implements IRenameRule {
	private final String beginText;
	private final String endText;
	
	/**
	 * Constructor
	 * @param beginText the from text
	 * @param endText the to text
	 */
	public CuttingFromToTextRenameRule(String beginText, String endText) {
		this.beginText = beginText;
		this.endText = endText;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();
		
		int beginIndex = name.indexOf(beginText);
		if (beginIndex == -1) {
			chain.doFilter(renameable);
			return;
		}
		int endIndex = name.lastIndexOf(endText);
		if (endIndex == -1) {
			chain.doFilter(renameable);
			return;
		}
		else {
			endIndex += endText.length();
		}
		if (beginIndex > endIndex) {
			chain.doFilter(renameable);
			return;
		}
		name = name.substring(0, beginIndex) + name.substring(endIndex, name.length());
		
		renameable.setName(name);
		chain.doFilter(renameable);
	}
}
