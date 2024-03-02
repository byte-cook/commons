package de.kobich.commons.ui.memento;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class StringMapSerializer implements IMementoItemSerializer2<Map<String, String>> {
	private static final String COUNT_POSTFIX = "-Count";
	private static final String KEY_POSTFIX = "-Key";
	private static final String VALUE_POSTFIX = "-Value";
	private final String stateName;

	/**
	 * @param stateName
	 */
	public StringMapSerializer(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public Map<String, String> restore(IMementoItem mementoItem) {
		Map<String, String> map = new HashMap<String, String>();
		
		int count = mementoItem.getInteger(stateName + COUNT_POSTFIX, 0);
		for (int i = 0; i < count; ++ i) {
			String key = mementoItem.getString(stateName + i + KEY_POSTFIX, "");
			String value = mementoItem.getString(stateName + i + VALUE_POSTFIX, "");
			map.put(key, value);
		}
		
		return map;
	}

	@Override
	public void restore(Map<String, String> map, IMementoItem mementoItem) {
		map.putAll(restore(mementoItem));
	}

	@Override
	public void save(Map<String, String> map, IMementoItem mementoItem) {
		int i = 0;
		for (Entry<String, String> entry : map.entrySet()) {
			mementoItem.putString(stateName + i + KEY_POSTFIX, entry.getKey());
			mementoItem.putString(stateName + i + VALUE_POSTFIX, entry.getValue());
			i ++;
		}
		mementoItem.putInteger(stateName + COUNT_POSTFIX, i);
	}

}
