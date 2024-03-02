package de.kobich.commons.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.kobich.commons.Reject;

/**
 * This class implements a cache to find objects by its keys. 
 * If a given condition is fulfilled the cache removes some objects automatically.
 * You can define when and what objects should be removed by different policy strategies. 
 * @param <Key> the key
 * @param <Value> the value
 */
public class PolicyCache<Key, Value> implements IObjectCache<Key, Value>, Serializable {
	private static final Logger logger = Logger.getLogger(PolicyCache.class);
	private static final long serialVersionUID = 4711L;
	/**
	 * The internal storage for all cache entries
	 */
	private Map<Key, PolicyValue<Value>> objectMap;
	/**
	 * The policy to use.
	 */
	private ICacheLimitationPolicy limitationPolicy;
	private ICacheDeletionPolicy deletionPolicy;

	/**
	 * Creates an ObjectCache instance which never holds more elements than <code>size</code>
	 */
	public PolicyCache() {
		this(new OutOfMemoryLimitationPolicy(), new StandardDeletionPolicy());
	}

	/**
	 * Creates an ObjectCache instance which never holds more elements than <code>size</code>
	 * @param freeMemoryFactor defines how many memory must be free in relation to the max. memory
	 * @param objects2RemoveFactor defines how many objects will be removed in relation to the entire object count
	 */
	public PolicyCache(ICacheLimitationPolicy limitationPolicy, ICacheDeletionPolicy deletionPolicy) {
		// Note: Hashtable is synchronized, whereas HashMap is not.
//		this.objectMap = new Hashtable<Key, CacheObject<Value>>();
		this.objectMap = new HashMap<Key, PolicyValue<Value>>();
		this.limitationPolicy = limitationPolicy;
		this.deletionPolicy = deletionPolicy;
	}

	/**
	 * Indicates wheater an object to the specified <code>key</code> exists. Note: Since the cache removes entries after reaching the maximum size, it
	 * is possible that inserted elements are not available. In this case the method returns false. So the caller have to reckon that inserted objects
	 * are later removed.
	 * @param key the key to which an object should be found
	 * @return true if an object to the key exists, otherwise false
	 */
	@Override
	public synchronized boolean containsKey(Key key) {
		return objectMap.containsKey(key);
	}

	/**
	 * Gets the corresponding value object to the specified <code>key</code>
	 * @param key the key to an object
	 * @return the corresponding object from the cache, or null if the key does not exist
	 */
	@Override
	public synchronized Value get(Key key) {
		return get(key, true);
	}

	/**
	 * Gets the corresponding object to the specified <code>key</code>
	 * @param key the key to an object
	 * @param incrementCount indicates, if the number of calls should be incremented
	 * @return the corresponding object from the cache, or null if the key does not exist
	 */
	protected synchronized Value get(Key key, boolean incrementCount) {
		if (objectMap.containsKey(key)) {
			if (incrementCount) {
				((PolicyValue<Value>) objectMap.get(key)).incrementAccessCount();
			}
			return ((PolicyValue<Value>) objectMap.get(key)).value();
		}
		else {
			return null;
		}
	}

	/**
	 * Adds a key/value-pair into the cache. Note: If the cache size is reached, this method removes the rarly used cache entry.
	 * @param key the key for the value
	 * @param value the corresponding value
	 */
	@Override
	public synchronized Value put(Key key, Value value) {
		logger.debug("Method put() called (key=" + key + ")");
		Reject.ifNull(key, "Key is null");

		if (limitationPolicy.isLimitReached(this)) {
			removeRarlyUsedElements();
		}
		PolicyValue<Value> previousValue = objectMap.put(key, new PolicyValue<Value>(value));
		logger.debug("Cache size: " + objectMap.size());
		if (previousValue != null) {
			return previousValue.getValue();
		}
		return null;
	}

	/**
	 * Removes the element which was used most rarly
	 */
	protected synchronized void removeRarlyUsedElements() {
		List<Key> elements2Remove = deletionPolicy.getKeys(this);
		for (Key key : elements2Remove) {
			if (key != null) {
				objectMap.remove(key);
			}
		}
	}
	
	public Map<Key, PolicyValue<Value>>  getInternalMap() {
		return objectMap;
	}

	/**
	 * Returns all keys
	 * @return keys
	 */
	// @Override
	// private synchronized Set<Key> getKeys() {
	// return objectMap.keySet();
	// }

	/**
	 * Returns all values
	 * @return values
	 */
	// @Override
	// public synchronized Set<Value> getValues() {
	// Set<Value> result = new HashSet<Value>();
	// Enumeration<CacheObject<Value>> it = objectMap.elements();
	// while (it.hasMoreElements()) {
	// CacheObject<Value> value = it.nextElement();
	// result.add(value.getValue());
	// }
	// return result;
	// }

	/**
	 * Tests if the cache is empty
	 * @return true or false
	 */
	@Override
	public synchronized boolean isEmpty() {
		return objectMap.isEmpty();
	}

	/**
	 * Returns the number of elements in the cache
	 * @return the numer of elements
	 */
	@Override
	public synchronized int size() {
		return objectMap.size();
	}

	@Override
	public synchronized Value remove(Key key) {
		PolicyValue<Value> previousValue = objectMap.remove(key);
		if (previousValue != null) {
			return previousValue.getValue();
		}
		return null;
	}

	@Override
	public void clear() {
		objectMap.clear();
	}
}
