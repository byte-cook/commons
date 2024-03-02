package de.kobich.commons.parser.file.xml;

import java.util.Map;

import de.kobich.commons.parser.file.IParsingItem;


public class XMLParsingItem implements IParsingItem {
	private Map<String, String> keyMap;
	private Map<String, String> uniqueNameMap;
	
	public XMLParsingItem(Map<String, String> keyMap, Map<String, String> uniqueNameMap) {
		this.keyMap = keyMap;
		this.uniqueNameMap = uniqueNameMap;
	}

	/**
	 * Example: <p>
	 * <code>item.getValue("memos/memo[category]");</code><br>
	 * <code>item.getValue("name");</code>
	 */
	@Override
	public String getValue(String key) {
		if (keyMap.containsKey(key)) {
			return keyMap.get(key);
		}
		else if (uniqueNameMap.containsKey(key)) {
			return uniqueNameMap.get(key);
		}
		return null;
	}

}
