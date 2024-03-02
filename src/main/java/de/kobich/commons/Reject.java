package de.kobich.commons;

import java.util.Collection;


/**
 * @author ckorn
 */
public class Reject {
	
	public static void ifNull(Object object) {
		ifNull(object, null);
	}
	
	public static void ifNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void ifTrue(boolean condition) {
		ifTrue(condition, null);
	}
	
	public static void ifTrue(boolean condition, String message) {
		if (condition) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void ifFalse(boolean condition) {
		ifFalse(condition, null);
	}
	
	public static void ifFalse(boolean condition, String message) {
		if (!condition) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void ifEmpty(Collection<?> collection) {
		ifEmpty(collection, null);
	}
	
	public static void ifEmpty(Collection<?> collection, String message) {
		if (collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}
	public static void ifNotEmpty(Collection<?> collection) {
		ifNotEmpty(collection, null);
	}
	
	public static void ifNotEmpty(Collection<?> collection, String message) {
		if (!collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}
}