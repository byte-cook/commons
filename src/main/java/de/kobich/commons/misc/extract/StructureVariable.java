package de.kobich.commons.misc.extract;

/**
 * Structure variable (e.g. %medium%, %track%)
 * @author ckorn
 */
public class StructureVariable {
	private String name;
	private String extractPattern;
	private String ignorePattern;
	
	public StructureVariable(String name) {
		this(name, "(.*)", ".*");
	}
	
	public StructureVariable(String name, String extractPattern, String ignorePattern) {
		this.name = name;
		this.extractPattern = extractPattern;
		this.ignorePattern = ignorePattern;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the extractPattern
	 */
	public String getExtractPattern() {
		return extractPattern;
	}

	/**
	 * @return the ignorePattern
	 */
	public String getIgnorePattern() {
		return ignorePattern;
	}

	/**
	 * @param extractPattern the extractPattern to set
	 */
	public void setExtractPattern(String extractPattern) {
		this.extractPattern = extractPattern;
	}

	/**
	 * @param ignorePattern the ignorePattern to set
	 */
	public void setIgnorePattern(String ignorePattern) {
		this.ignorePattern = ignorePattern;
	}
}
