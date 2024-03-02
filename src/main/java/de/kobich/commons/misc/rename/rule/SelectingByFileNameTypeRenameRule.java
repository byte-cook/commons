package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;

/**
 * Selects a part to rename.
 * @author ckorn
 */
public class SelectingByFileNameTypeRenameRule implements IRenameRule {
	private final RenameFileNameType fileNameType;

	/**
	 * Constructor
	 */
	public SelectingByFileNameTypeRenameRule(RenameFileNameType fileNameType) {
		this.fileNameType = fileNameType;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		FileRenameable fileRenameable = new FileRenameable(renameable);
		String name = "";
		if (RenameFileNameType.FULLNAME.equals(fileNameType)) {
			name = fileRenameable.getName();
		}
		else if (RenameFileNameType.BASENAME.equals(fileNameType)) {
			name = fileRenameable.getBaseName();
		}
		else if (RenameFileNameType.EXTENSION.equals(fileNameType)) {
			name = fileRenameable.getExtension();
		}
		
		// rename part
		DummyRenameable dummy = new DummyRenameable(renameable);
		dummy.setName(name);
		chain.doFilter(dummy);
		
		if (RenameFileNameType.FULLNAME.equals(fileNameType)) {
			fileRenameable.setName(dummy.getName());
		}
		else if (RenameFileNameType.BASENAME.equals(fileNameType)) {
			fileRenameable.setBaseName(dummy.getName());
		}
		else if (RenameFileNameType.EXTENSION.equals(fileNameType)) {
			fileRenameable.setExtension(dummy.getName());
		}
	}

}
