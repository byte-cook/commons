package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.kobich.commons.collections.TypedKey;
import de.kobich.commons.collections.TypedMap;

public class TypedMapTest {
	@Test
	public void testValid() {
		TypedKey<Integer> ONE = new TypedKey<Integer>("one", Integer.class);
		TypedKey<Integer> TWO = new TypedKey<Integer>("two", Integer.class);
		TypedKey<String> TEXT = new TypedKey<String>("text", String.class);
		
		TypedMap map = new TypedMap();
		map.addElement(ONE, 1);
		map.addElement(TWO, 2);
		map.addElement(TEXT, "value-text");
		
		Object obj = map.get(ONE).getValue();
		assertEquals(Integer.class, obj.getClass());
		
		String result = map.getElement(TEXT);
		assertEquals("value-text", result);
		int one = map.getElement(ONE);
		assertEquals(1, one);
		assertEquals(Integer.valueOf(2), map.getElement(TWO));
	}
}
