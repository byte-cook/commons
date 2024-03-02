package de.kobich.commons.utils;

import java.util.ArrayList;
import java.util.List;

public class EnumUtils {
	public static List<String> getEnumNames(Enum<?>[] values) {
		List<String> names = new ArrayList<String>();
		for (Enum<?> v : values) {
			names.add(v.name());
		}
		return names;
	}
}
