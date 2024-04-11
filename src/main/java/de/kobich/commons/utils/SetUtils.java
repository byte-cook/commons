package de.kobich.commons.utils;

import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetUtils {
	
	public static record SetDiff<T>(Set<T> addedElements, Set<T> removeElements) {};
	
	/**
	 * Returns the difference of two sets
	 * @param <T>
	 * @param sourceSet
	 * @param targetSet
	 * @return
	 */
	public static <T> SetDiff<T> compareSet(Set<T> sourceSet, Set<T> targetSet) {
		Set<T> addedElements = new HashSet<>();
		addedElements.addAll(sourceSet);
		addedElements.removeAll(targetSet);
		
		Set<T> removeElements = new HashSet<>();
		removeElements.addAll(targetSet);
		removeElements.removeAll(sourceSet);
		return new SetDiff<>(addedElements, removeElements);
	}
	
	/**
	 * Synchronizes the sourceSet to the targetSet
	 * @param <T>
	 * @param sourceSet
	 * @param targetSet
	 */
	public static <T> void syncSets(Set<T> sourceSet, Set<T> targetSet) {
		SetDiff<T> diff = compareSet(sourceSet, targetSet);
		targetSet.addAll(diff.addedElements);
		targetSet.removeAll(diff.removeElements);
	}

}
