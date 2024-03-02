package de.kobich.commons.misc.rename.rule;

import org.apache.commons.lang3.StringUtils;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.utils.FilenameUtils;

/**
 * Wrapper class to rename files.
 * @author ckorn
 */
public class FileRenameable implements IRenameable {
	private IRenameable renameable;
	
	/**
	 * Constructor
	 * @param renameable
	 */
	public FileRenameable(IRenameable renameable) {
		this.renameable = renameable;
	}
	
	/**
	 * Returns the base name
	 * E.g. example.txt -> example
	 * @return
	 */
	public String getBaseName() {
		return FilenameUtils.getBaseName(renameable.getName());
	}
	
	/**
	 * Returns the extension
	 * E.g. example.txt -> txt
	 * @return
	 */
	public String getExtension() {
		return FilenameUtils.getExtension(renameable.getName());
	}
	
	/**
	 * Sets the base name
	 * @param baseName
	 */
	public void setBaseName(String baseName) {
		String fileName = baseName;
		if (StringUtils.isNotBlank(getExtension())) {
			fileName += FilenameUtils.EXTENSION_SEPARATOR + getExtension();
		}
		renameable.setName(fileName);
	}
	
	/**
	 * Sets extension
	 * @param extension
	 */
	public void setExtension(String extension) {
		String fileName = getBaseName();
		if (StringUtils.isNotBlank(extension)) {
			fileName += FilenameUtils.EXTENSION_SEPARATOR + extension;
		}
		renameable.setName(fileName);
	}

	@Override
	public String getCategory() {
		return renameable.getCategory();
	}

	@Override
	public String getName() {
		return renameable.getName();
	}

	@Override
	public void setName(String name) {
		renameable.setName(name);
	}

	@Override
	public String getAttribute(String attribute) {
		return renameable.getAttribute(attribute);
	}

	@Override
	public String getOriginalName() {
		return renameable.getOriginalName();
	}
}
