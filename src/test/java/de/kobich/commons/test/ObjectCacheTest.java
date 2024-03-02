package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import de.kobich.commons.cache.ICacheDeletionPolicy;
import de.kobich.commons.cache.ICacheLimitationPolicy;
import de.kobich.commons.cache.IObjectCache;
import de.kobich.commons.cache.PolicyCache;
import de.kobich.commons.cache.ReferenceCache;

/**
 * Tests class IObjectCache.
 */
public class ObjectCacheTest {
	private static final Logger logger = Logger.getLogger(ObjectCacheTest.class);
	
	/**
	 * Set up
	 * @testng.before-method
	 */
	@BeforeEach
	public void setUp() {
		BasicConfigurator.configure();
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
	 */
	@ParameterizedTest
	@MethodSource("createData")
	public void testSizeWithEqualKeys(IObjectCache<String, Object> cache) {
		logger.debug("Method testSizeWithEqualKeys() called");
		final String key = "key1";
		cache.put(key, new Object());
		cache.put(key, new Object());
		cache.put(key, new Object());
		assertEquals(1, cache.size());
	}

	/**
	 * Tests the cache size after adding different keys
	 * (more keys the max. allowed)
	 */
	@ParameterizedTest
	@MethodSource("createData")
	public void testSize(IObjectCache<String, Object> cache) {
		logger.debug("Method testSize() called");
		int keySizeToAdd = 300 + 1;
		for (int i = 0; i < keySizeToAdd; ++ i) {
			cache.put("key" + i, new Object());
		}
		assertNotNull(cache.get("key" + (keySizeToAdd - 1)));
	}

	/**
	 * Tests the cache size after adding different keys
	 * (more keys the max. allowed)
	 */
//	@ParameterizedTest
//	@MethodSource("createData")
//	public void testOutOfMemory(IObjectCache<String, Object> cache) {
//		logger.debug("Method testOutOfMemory() called: " + cache);
//		int lastSize = cache.size();
//		int i = 0;
//		do {
//			String key = "key" + i;
//			// allocate 10 MB
//			int size = (int) (10 * Units.MEGA_BYTE);
//			byte[] block = new byte[size];
//			cache.put(key, block);
//			++ i;
//			
//			cache.get(key);
//			
//			if (lastSize < cache.size()) {
//				lastSize = cache.size();
//				logger.info("Size: " + lastSize);
//				MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
//				MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
//				logger.info("Free: " + (heapMemoryUsage.getMax() - heapMemoryUsage.getUsed()));
//			}
//			else {
//				break;
//			}
//		} while (true);
//		assertNull(cache.get("key" + 0));
//		assertNotNull(cache.get("key" + (i - 1)));
//	}
	
	public static Object[][] createData() {
		return new Object[][] {
				{	new PolicyCache<String, Object>() }, 
				{	new PolicyCache<String, Object>(new TestPolicy(3, 1), new TestPolicy(3, 1))}, 
				{	new ReferenceCache<String, Object>(0) }, 
		};
	}
	
	private static class TestPolicy implements ICacheLimitationPolicy, ICacheDeletionPolicy {
		private int limitSize;
		private int numberObjects2Remove;
		
		public TestPolicy(int limitSize, int numberObjects2Remove) {
			this.limitSize = limitSize;
			this.numberObjects2Remove = numberObjects2Remove;
		}

		@Override
		public <Key, Value> List<Key> getKeys(PolicyCache<Key, Value> cache) {
			List<Key> keys = new ArrayList<Key>();
			Set<Key> usedKeys = cache.getInternalMap().keySet();
			for (Key k : usedKeys) {
				keys.add(k);
				if (keys.size() > numberObjects2Remove) {
					break;
				}
			}
			return keys;
		}

		@Override
		public <Key, Value> boolean isLimitReached(PolicyCache<Key, Value> cache) {
			return cache.size() > limitSize;
		}
		
	}
}
