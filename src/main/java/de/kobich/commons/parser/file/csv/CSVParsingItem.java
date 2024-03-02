package de.kobich.commons.parser.file.csv;

import java.util.Map;

import de.kobich.commons.parser.file.IParsingItem;

public class CSVParsingItem implements IParsingItem {
	private Map<Integer, String> indexMap;
	private Map<String, String> keyMap;
	
	public CSVParsingItem(Map<String, String> keyMap, Map<Integer, String> indexMap) {
		this.keyMap = keyMap;
		this.indexMap = indexMap;
	}

	/**
	 * Example: <p>
	 * <code>item.getValue("name")</code><br>
	 * <code>item.getValue("0")</code>
	 */
	@Override
	public String getValue(String key) {
		if (keyMap.containsKey(key)) {
			return keyMap.get(key);
		}
		else {
			try {
				int index = Integer.parseInt(key);
				return indexMap.get(index);
			}
			catch (NumberFormatException exc) {
				return null;
			}
		}
	}

}
