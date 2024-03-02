package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;



/**
 * Change case.
 * @author ckorn
 */
public class CaseRenameRule implements IRenameRule {
	public static enum Case { UPPER_CASE, LOWER_CASE, START_CASE, SENTENCE_CASE }
	private final Case caze;

	/**
	 * Constructor
	 */
	public CaseRenameRule(Case caze) {
		this.caze = caze;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();
		
		if (Case.LOWER_CASE.equals(caze)) {
			name = name.toLowerCase();
		}
		else if (Case.UPPER_CASE.equals(caze)) {
			name = name.toUpperCase();
		}
		else if (Case.START_CASE.equals(caze)) {
			String[] words = name.split(" ");
			name = "";
			for (String word : words) {
				word = word.toLowerCase();
				
				int letterIndex = indexOfFirstLetters(word);
				if (letterIndex != -1) {
					word = word.substring(0, letterIndex + 1).toUpperCase() + word.substring(letterIndex + 1);
				}
				name += word + " ";
			}
			name = name.trim();
		}
		else if (Case.SENTENCE_CASE.equals(caze)) {
			int letterIndex = indexOfFirstLetters(name);
			if (letterIndex != -1) {
				String firstChar = name.substring(0, letterIndex + 1).toUpperCase();
				String restChars = name.substring(letterIndex + 1).toLowerCase();
				name = firstChar + restChars;
			}
		}
		
		renameable.setName(name);
		chain.doFilter(renameable);
	}
	
	/**
	 * Returns the index of the first letter or -1
	 * @param text
	 * @return
	 */
	private int indexOfFirstLetters(String text) {
		if (text.isEmpty()) {
			return -1;
		}
		
		for (int i = 0; i < text.length(); ++ i) {
			if (Character.isLetter(text.charAt(i))) {
				return i;
			}
		}
		return -1;
	}
}
