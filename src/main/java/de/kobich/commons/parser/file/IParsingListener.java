package de.kobich.commons.parser.file;

/**
 * Parsing listener
 * @author ckorn
 */
public interface IParsingListener {
	/**
	 * Invoked for each parsing item
	 * @param item
	 */
	public void itemParsed(IParsingItem item);
}
