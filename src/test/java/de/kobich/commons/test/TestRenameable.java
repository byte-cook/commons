package de.kobich.commons.test;

import de.kobich.commons.misc.rename.IRenameable;


public class TestRenameable implements IRenameable {
	private final String category;
	private String name;
	private final String originalName;
	
	public TestRenameable(String category, String name) {
		this.category = category;
		this.name = name;
		this.originalName = name;
	}

	@Override
	public String getCategory() {
		return category;
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
	public String getAttribute(String attribute) {
		return null;
	}

	@Override
	public String getOriginalName() {
		return originalName;
	}

}
