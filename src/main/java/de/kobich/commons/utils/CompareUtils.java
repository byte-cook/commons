package de.kobich.commons.utils;


public class CompareUtils {
	/**
	 * Indicates if two objects are equal (both are null or equal)  
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean equals(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		if (o1 != null && o2 != null && o1.equals(o2)) {
			return true;
		}
		return false;
	}
}
