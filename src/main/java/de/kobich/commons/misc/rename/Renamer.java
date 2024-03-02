package de.kobich.commons.misc.rename;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.kobich.commons.misc.rename.rule.IRenameRule;
import de.kobich.commons.monitor.progress.IServiceProgressMonitor;
import de.kobich.commons.monitor.progress.ProgressData;
import de.kobich.commons.monitor.progress.ProgressSupport;


/**
 * Defines methods to rename.
 * @author ckorn
 */
public class Renamer {
	/**
	 * Renames one or more items
	 * @param renameables
	 * @param rules
	 */
	public static void rename(Collection<? extends IRenameable> renameables, List<IRenameRule> rules) {
		rename(renameables, rules, new DefaultRenameableComparator(), null);
	}

	/**
	 * Renames one or more items
	 * @param renameables
	 * @param rules
	 * @param monitor
	 */
	public static void rename(Collection<? extends IRenameable> renameables, List<IRenameRule> rules, IServiceProgressMonitor monitor) {
		rename(renameables, rules, new DefaultRenameableComparator(), monitor);
	}
	
	/**
	 * Renames one or more items
	 * @param renameables
	 * @param rules
	 * @param monitor
	 */
	public static void rename(Collection<? extends IRenameable> renameables, List<IRenameRule> rules, Comparator<? super IRenameable> comparator, IServiceProgressMonitor monitor) {
		ProgressSupport progressSupport = new ProgressSupport(monitor);
		
		List<IRenameable> renameableList = new ArrayList<IRenameable>(renameables);
		Collections.sort(renameableList, comparator);
		
		String lastCategory = "";
		for (IRenameable renameable : renameableList) {
			// monitor sub task
			ProgressData monitorData = new ProgressData("Renaming: " + renameable.getName());
			progressSupport.monitorSubTask(monitorData);
			
			// category
			String newCategory = renameable.getCategory();
			boolean categoryChanged = !lastCategory.equals(newCategory);
			lastCategory = newCategory;
			
			RenameFilterChain filterChain = new RenameFilterChain(rules, categoryChanged);
			filterChain.doFilter(renameable);
		}
	}
}
