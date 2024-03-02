package de.kobich.commons.cache;

import java.io.Serializable;

/**
 * This class is a wrapper class for objects which are inserted into the cache. This class store additional information like the creation time and the
 * number of calls for that object for analysis purposes.
 */
public class PolicyValue<Value> implements Serializable {

	/**
	 * generated attribute
	 */
	private static final long serialVersionUID = 2919482008353813275L;
	/**
	 * The time in milisecond when the object is inserted into the cache
	 */
	private final long created;
	/**
	 * The time when the object was called for the last time
	 */
	private long lastAccessed;
	/**
	 * The number of calls to this object, starting with 1
	 */
	private long accessCount;
	/**
	 * The actual value object
	 */
	private final Value obj;

	/**
	 * Creates a CacheObject.
	 * @param obj the actual cached object
	 */
	public PolicyValue(Value obj) {
		this.created = System.currentTimeMillis();
		this.accessCount = 1;
		this.obj = obj;
	}

	/**
	 * Increments the number of calls by 1
	 */
	public void incrementAccessCount() {
		++this.accessCount;
	}

	/**
	 * Return the actual value object
	 * @return the actual value object
	 */
	public Value value() {
		return obj;
	}

	/**
	 * Return the actual value object
	 * @return the actual value object
	 */
	public Value getValue() {
		return value();
	}

	/**
	 * @return the created
	 */
	public long getCreated() {
		return created;
	}

	/**
	 * @return the lastAccessed
	 */
	public long getLastAccessed() {
		return lastAccessed;
	}

	/**
	 * @return the accessCount
	 */
	public long getAccessCount() {
		return accessCount;
	}

}
