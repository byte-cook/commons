package de.kobich.commons.cache;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import org.apache.log4j.Logger;

public class OutOfMemoryLimitationPolicy implements ICacheLimitationPolicy {
	private static final Logger logger = Logger.getLogger(OutOfMemoryLimitationPolicy.class);
	private static final double MEGA_BYTE = 1024 * 1024;
	/**
	 * The factor to calculate the minimal free memory
	 */
	private double freeMemoryFactor;
	
	public OutOfMemoryLimitationPolicy() {
		this(0.1);
	}
	public OutOfMemoryLimitationPolicy(double freeMemoryFactor) {
		this.freeMemoryFactor = freeMemoryFactor;
	}

	@Override
	public <Key, Value> boolean isLimitReached(PolicyCache<Key, Value> cache) {
		// check available memory
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		double maxMemory = heapMemoryUsage.getMax(); 
		double freeMemory = maxMemory - heapMemoryUsage.getUsed(); 
		double minimalFreeMemory = maxMemory * freeMemoryFactor;
		if (freeMemory < minimalFreeMemory) {
			if (logger.isDebugEnabled()) {
				double totalMemory = Runtime.getRuntime().totalMemory();
				logger.debug("Free amount of memory:    " + freeMemory / MEGA_BYTE + " MB");
				logger.debug("Total amount of memory:   " + totalMemory / MEGA_BYTE + " MB");
				logger.debug("Maximum amount of memory: " + maxMemory / MEGA_BYTE + " MB");
				logger.debug("Minimal free amount of memory:   " + minimalFreeMemory / MEGA_BYTE + " MB");
			}
			logger.info("Current size: " + cache.size());
			return true;
		}

		return false;
	}

}
