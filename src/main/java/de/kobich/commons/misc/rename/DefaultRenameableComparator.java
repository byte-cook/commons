package de.kobich.commons.misc.rename;

import java.util.Comparator;


/**
 * Adapter from audio file to file provider.
 * @author ckorn
 */
public class DefaultRenameableComparator implements Comparator<IRenameable> {
	/**
	 * Constructor
	 */
	public DefaultRenameableComparator() {}

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(IRenameable o1, IRenameable o2) {
		int result = o1.getCategory().compareTo(o2.getCategory());
		if (result == 0) {
			result = o1.getOriginalName().compareTo(o2.getOriginalName());
		}
		return result;
	}

}
