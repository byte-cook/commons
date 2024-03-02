package de.kobich.commons.misc.rename.rule;

import java.util.Set;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;



/**
 * Replaces attributes of renameables.
 * @author ckorn
 */
public class AttributeRenameRule implements IRenameRule {
	private final String pattern;
	private final Set<String> attributes;

	/**
	 * Constructor
	 */
	public AttributeRenameRule(String pattern, Set<String> attributes) {
		this.pattern = pattern;
		this.attributes = attributes;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = pattern;
		for (String a : attributes) {
			if (pattern.contains(a)) {
				String value = renameable.getAttribute(a);
				if (value != null) {
					name = name.replace(a, value);
				}
			}
		}
		
		renameable.setName(name);
		chain.doFilter(renameable);
	}
}
