package de.kobich.commons.utils;

import javax.annotation.Nullable;

public class CompareUtils {
	/**
	 * Indicates if two objects are equal (both are null or equal)  
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean equals(@Nullable Object o1, @Nullable Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		if (o1 != null && o2 != null && o1.equals(o2)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Compares two objects
	 * @param <T>
	 * @param c1
	 * @param c2
	 * @param nullIsGreater true if compare(null, non-null) should return 1
	 * @return
	 */
	public static <T extends Comparable<T>> int compare(@Nullable T c1, @Nullable T c2, boolean nullIsGreater) {
		if (c1 != null && c2 != null) {
			return c1.compareTo(c2);
		}
		else if (c1 != null) {
			return nullIsGreater ? -1 : 1;
		}
		else if (c2 != null) {
			return nullIsGreater ? 1 : -1;
		}
		return 0;
	}
}
