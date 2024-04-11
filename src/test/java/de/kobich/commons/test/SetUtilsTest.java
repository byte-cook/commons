package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.kobich.commons.utils.SetUtils;
import de.kobich.commons.utils.SetUtils.SetDiff;

public class SetUtilsTest {
	
	@Test
	public void testSyncSet() throws FileNotFoundException, URISyntaxException {
		Set<Integer> sourceSet = Set.of(1, 2, 3);
		Set<Integer> targetSet = new HashSet<>();
		SetUtils.syncSets(sourceSet, targetSet);
		assertEquals(sourceSet, targetSet);
		
		targetSet.remove(2);
		SetDiff<Integer> diff = SetUtils.compareSet(sourceSet, targetSet);
		assertEquals(Set.of(2), diff.addedElements());
		assertTrue(diff.removeElements().isEmpty());
		
		targetSet.add(7);
		sourceSet = Set.of(1, 2, 3, 4);
		diff = SetUtils.compareSet(sourceSet, targetSet);
		assertEquals(Set.of(2, 4), diff.addedElements());
		assertEquals(Set.of(7), diff.removeElements());
	}

}
