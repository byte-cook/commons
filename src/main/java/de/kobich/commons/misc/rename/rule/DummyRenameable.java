package de.kobich.commons.misc.rename.rule;

import de.kobich.commons.misc.rename.IRenameable;

/**
 * Wraps a Renameable to modify its name.
 */
public class DummyRenameable implements IRenameable {
	private final IRenameable renameable;
	private String name;
	
	public DummyRenameable(IRenameable renameable) {
		this.renameable = renameable;
	}

	@Override
	public String getCategory() {
		return renameable.getCategory();
	}

	@Override
	public String getAttribute(String attribute) {
		return renameable.getAttribute(attribute);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getOriginalName() {
		return renameable.getOriginalName();
	}

}
