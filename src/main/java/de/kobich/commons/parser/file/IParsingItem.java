package de.kobich.commons.parser.file;

/**
 * Parsing item
 * @author ckorn
 */
public interface IParsingItem {
	/**
	 * Returns the value to a given key
	 * @param key
	 * @return
	 */
	public String getValue(String key);
}
