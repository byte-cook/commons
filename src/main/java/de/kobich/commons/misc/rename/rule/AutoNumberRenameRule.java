package de.kobich.commons.misc.rename.rule;

import java.util.HashMap;
import java.util.Map;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;
import de.kobich.commons.utils.FilenameUtils;



/**
 * Adds auto numbering.
 * @author ckorn
 */
public class AutoNumberRenameRule implements IRenameRule {
	private RenamePositionType position;
	private int currentNumber;
	private final int startAt;
	private final int increment;
	private final int digits;
	private final boolean restart4EachDirectory;
	private final boolean useEqualNumberIfExtensionDiffersOnly;
	private final Map<String, Integer> path2Number;

	/**
	 * Constructor
	 * @param position the position
	 * @param startAt the start number
	 * @param increment the increment
	 * @param digits the number of digits
	 * @param restart4EachDirectory indicates if for each directory should be started from new
	 */
	public AutoNumberRenameRule(RenamePositionType position, int startAt, int increment, int digits, boolean restart4EachDirectory, boolean useEqualNumberIfExtensionDiffersOnly) {
		this.position = position;
		this.startAt = startAt;
		this.currentNumber = startAt;
		this.increment = increment;
		this.digits = digits;
		this.restart4EachDirectory = restart4EachDirectory;
		this.useEqualNumberIfExtensionDiffersOnly = useEqualNumberIfExtensionDiffersOnly;
		this.path2Number = new HashMap<String, Integer>();
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		if (categoryChanged) {
			if (restart4EachDirectory) {
				currentNumber = startAt;
			}
		}
		String name = renameable.getName();

		// set number
		boolean doIncrement = true;
		String path = FilenameUtils.removeExtension(renameable.getCategory() + renameable.getOriginalName());
		String autoNumber = "" + currentNumber;
		if (useEqualNumberIfExtensionDiffersOnly && path2Number.containsKey(path)) { 
			doIncrement = false;
			autoNumber = "" + path2Number.get(path);
		}

		// set digits
		if (autoNumber.length() > digits) {
			while (autoNumber.length() > digits) {
				autoNumber = autoNumber.substring(1);
			}
		}
		else {
			while (autoNumber.length() < digits) {
				autoNumber = "0" + autoNumber;
			}
		}

		// set position
		if (RenamePositionType.BEFORE.equals(position)) {
			name = autoNumber + name;
		}
		else if (RenamePositionType.AFTER.equals(position)) {
			name = name + autoNumber;
		}

		// increment
		if (doIncrement) {
			path2Number.put(path, currentNumber);
			currentNumber += increment;
		}

		renameable.setName(name);
		chain.doFilter(renameable);
	}

}
