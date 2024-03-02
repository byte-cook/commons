package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.collections.Dimension;
import de.kobich.commons.collections.DimensionMap2D;
import de.kobich.commons.collections.DimensionMap2DGeneric;
import de.kobich.commons.collections.DimensionMap3D;
import de.kobich.commons.collections.Dimension.DimensionType;

public class MultiDimensionCollectionTest {
	@BeforeEach
	public void setUp() {
		BasicConfigurator.configure();
	}
	
	@Test
	public void testListMap2D() {
		DimensionMap2D<Integer, String> map2D = new DimensionMap2D<Integer, String>(DimensionType.LIST);
		fillMap2D(map2D);
		assertEquals(4, map2D.get(1).size());
		assertTrue(map2D.get(1).containsAll(Arrays.asList("1.0", "1.1", "1.2")));
		assertEquals(2, map2D.get(2).size());
		assertEquals(1, map2D.get(3).size());
		assertTrue(map2D.get(100) == null);
		System.out.println("list map: ");
		for (String s : map2D.get(1)) {
			System.out.print(s);
			System.out.println(" -> " + map2D.get(1).asList().indexOf(s));
		}
	}

	@Test
	public void testSetMap2D() {
		DimensionMap2D<Integer, String> map2D = new DimensionMap2D<Integer, String>(DimensionType.SET);
//		SetMap2D<Integer, String> map2D = new SetMap2D<Integer, String>();
		fillMap2D(map2D);
		assertEquals(3, map2D.get(1).size());
		assertTrue(map2D.get(1).containsAll(Arrays.asList("1.0", "1.1", "1.2")));
		assertEquals(2, map2D.get(2).size());
		assertEquals(1, map2D.get(3).size());
		assertTrue(map2D.get(100) == null);
		System.out.println("set map: ");
		for (String s : map2D.get(1)) {
			System.out.println(s);
		}
	}
	
	@Test
	public void testListMap3D() {
		DimensionMap3D<Integer, String, String> map3D = new DimensionMap3D<Integer, String, String>(DimensionType.LIST);
		map3D.addElement(1, "1.0", "1.0.0");
		map3D.addElement(1, "1.0", "1.0.1");
		map3D.addElement(1, "1.0", "1.0.2");
		map3D.addElement(1, "1.1", "1.1.0");
		map3D.addElement(1, "1.1", "1.1.1");
		map3D.addElement(2, "2.0", "2.1.0");
		assertEquals(3, map3D.get(1).get("1.0").size());
		assertTrue(map3D.get(2).get("1.0") == null);
		assertEquals(1, map3D.get(2).get("2.0").size());
		System.out.println("set map 3D: ");
		for (Integer i : map3D.keySet()) {
			DimensionMap2D<String, String> map1 = map3D.get(i);
			for (String s1 : map1.keySet()) {
				for (String s2 : map1.get(s1)) {
					System.out.println(i + " -> " + s1 + " -> " + s2);
				}
			}
		}
		
	}

	@Test
	public void testGenericMap2D() {
		final String _1 = "1";
		final String _2 = "2";
		final String _3 = "3";
		final String _4 = "4";
		
		DimensionMap2DGeneric<String> map = new DimensionMap2DGeneric<String>(DimensionType.LIST);
		map.addElement(_1, 1);
		map.addElement(_2, 2);
		map.addElement(_3, "value-text");
		Object o = Long.valueOf(4);
		map.addElement(_4, o);
		
		Dimension<Integer> i1 = map.getElements(_1, Integer.class);
		assertEquals(Integer.valueOf(1), i1.get(0));
		Dimension<String> s3 = map.getElements(_3, String.class);
		assertEquals("value-text", s3.get(0));
		Dimension<Long> l4 = map.getElements(_4, Long.class);
		assertEquals(Long.valueOf(4), l4.get(0));
	}

	private void fillMap2D(DimensionMap2D<Integer, String> map2D) {
		map2D.addElement(1, "1.2");
		map2D.addElement(1, "1.0");
		map2D.addElement(1, "1.1");
		map2D.addElement(1, "1.2");
		map2D.addElement(2, "2.0");
		map2D.addElement(2, "2.1");
		map2D.addElement(3, "3.0");
	}
}
