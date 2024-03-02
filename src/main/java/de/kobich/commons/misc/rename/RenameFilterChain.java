package de.kobich.commons.misc.rename;

import java.util.Collections;
import java.util.List;

import de.kobich.commons.misc.rename.rule.IRenameRule;

public class RenameFilterChain {
	private final List<IRenameRule> filters;
	private final boolean categoryChanged;
	private int currentIndex;
	
	public RenameFilterChain(List<IRenameRule> filters, boolean categoryChanged) {
		this.filters = Collections.unmodifiableList(filters);
		this.categoryChanged = categoryChanged;
		this.currentIndex = 0;
	}
	
	/**
	 * Causes the next filter in the chain to be invoked
	 * @param file 
	 */
	public void doFilter(IRenameable name) {
		if (filters.size() > currentIndex) {
			// 1. increment
			++ currentIndex;
			// 2. use filter
			filters.get(currentIndex - 1).rename(name, this, categoryChanged);
		}
	}

}
