package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.kobich.commons.collections.GenericMap;

public class GenericMapTest {
	@Test
	public void testValid() {
		final String _1 = "1";
		final String _2 = "2";
		final String _3 = "3";
		final String _4 = "4";
		
		GenericMap<String> map = new GenericMap<String>();
		map.addGenericElement(_1, 1);
		map.addGenericElement(_2, 2);
		map.addGenericElement(_3, "value-text");
		Object o = Long.valueOf(4);
		map.addGenericElement(_4, o);
		
		int i1 = map.getGenericElement(_1, Integer.class);
		assertEquals(1, i1);
		String s3 = map.getGenericElement(_3, String.class);
		assertEquals("value-text", s3);
		long l4 = map.getGenericElement(_4, Long.class);
		assertEquals(4, l4);
	}
}
