package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.beans.AnnotationPropertyProvider;
import de.kobich.commons.beans.GetProperty;
import de.kobich.commons.beans.IPropertyProvider;
import de.kobich.commons.beans.ReflectionPropertyProvider;
import de.kobich.commons.beans.SetProperty;

public class PropertyProviderTest {
	private final String NAME_PROPERTY = "name";
	private final String COUNT_PROPERTY = "count";
	private final String INCREMENT_PROPERTY = "increment";
	
	@BeforeEach
	public void setUp() {
	}
	
	/**
	 * Tear down
	 * @testng.after-method
	 */
	@AfterEach
	public void tearDown() {
	}

	/**
	 * Tests the cache size after adding equal keys
	 * @testng.test
	 */
	@Test
	public void testReflection() {
		ReflectionPropertyProvider<TestBean> pp = new ReflectionPropertyProvider<TestBean>(TestBean.class);
		pp.setReadMethod(NAME_PROPERTY, "getName");
		pp.setWriteMethod(NAME_PROPERTY, "setName", String.class);
		pp.setReadMethod(COUNT_PROPERTY, "getCount");
		pp.setWriteMethod(COUNT_PROPERTY, "setCount", int.class);
		pp.setWriteMethod(INCREMENT_PROPERTY, "incrementCount", int.class, int.class);
		test(pp);
	}
	
	@Test
	public void testAnnotation() {
		AnnotationPropertyProvider<TestBean> pp = new AnnotationPropertyProvider<TestBean>(TestBean.class);
		test(pp);
	}
	
	private void test(IPropertyProvider<TestBean> pp) {
		TestBean entity = new TestBean("test", 15);
		// read
		assertEquals("test", pp.getProperty(entity, NAME_PROPERTY));
		assertEquals(Integer.valueOf(15), pp.getProperty(entity, COUNT_PROPERTY, Integer.class));
		// write
		pp.setProperty(entity, NAME_PROPERTY, "newName");
		assertEquals("newName", pp.getProperty(entity, NAME_PROPERTY));
		pp.setProperty(entity, COUNT_PROPERTY, Integer.valueOf(20));
		assertEquals(Integer.valueOf(20), pp.getProperty(entity, COUNT_PROPERTY, Integer.class));
		pp.setProperty(entity, INCREMENT_PROPERTY, 10, 20);
		assertEquals(Integer.valueOf(30), pp.getProperty(entity, COUNT_PROPERTY, Integer.class));
	}
	
	public class TestBean {
		private String name;;
		private int count;
		
		public TestBean(String value, int count) {
			this.name = value;
			this.count = count;
		}
		@GetProperty(name={"name", "name-2"})
		public String getName() {
			return name;
		}
		@SetProperty(name=NAME_PROPERTY)
		public void setName(String value) {
			this.name = value;
		}
		@GetProperty(name="count")
		public int getCount() {
			return count;
		}
		@SetProperty(name="count")
		public void setCount(int count) {
			this.count = count;
		}
		@SetProperty(name="increment")
		public void incrementCount(int count, int increment) {
			this.count = count + increment;
		}
	}
}
