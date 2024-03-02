package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;

/**
 * Replaces text.
 * @author ckorn
 */
public class ReplacingTextRenameRule implements IRenameRule {
	private final RenameOccurrenceType occurrence;
	private final String replaceText;
	private final String newText;

	/**
	 * Constructor
	 * @param replaceText the text to replace
	 * @param newText the new text
	 */
	public ReplacingTextRenameRule(String replaceText, String newText, RenameOccurrenceType occurrence) {
		this.replaceText = replaceText;
		this.newText = newText;
		this.occurrence = occurrence;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();

		if (RenameOccurrenceType.ALL.equals(occurrence)) {
			name = name.replace(replaceText, newText);
		}
		else if (RenameOccurrenceType.FIRST.equals(occurrence)) {
			String tmpName = name;
			int pos = tmpName.indexOf(replaceText);
			int length = tmpName.length();
			if (pos > -1) {
				name = tmpName.substring(0, pos);
				name += newText;
				int startPos = pos + replaceText.length();
				if (startPos < length) {
					name += tmpName.substring(startPos, length);
				}
			}
		}
		else if (RenameOccurrenceType.LAST.equals(occurrence)) {
			String tmpName = name;
			int pos = tmpName.lastIndexOf(replaceText);
			int length = tmpName.length();
			if (pos > -1) {
				name = tmpName.substring(0, pos);
				name += newText;
				int startPos = pos + replaceText.length();
				if (startPos < length) {
					name += tmpName.substring(startPos, length);
				}
			}
		}
		renameable.setName(name);
		chain.doFilter(renameable);
	}
}
