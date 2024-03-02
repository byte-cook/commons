package de.kobich.commons.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Standard policy: This policy weights all properties equal. The properties are: 
 * <ul>
 * <li>The time in millisecond when the object is inserted into the cache</li> 
 * <li>The time when the object was called for the last time</li>
 * <li>The number of calls to this object</li>
 * </ul>
 * @author ckorn
 */
public class StandardDeletionPolicy implements ICacheDeletionPolicy {
	private final static Logger logger = Logger.getLogger(StandardDeletionPolicy.class);
	/**
	 * The factor to calculate the number of objects to delete if min. free memory is gone below
	 */
	private double objects2RemoveFactor;
	
	public StandardDeletionPolicy() {
		this(0.3);
	}
	public StandardDeletionPolicy(double objects2RemoveFactor) {
		this.objects2RemoveFactor = objects2RemoveFactor;
	}

	@Override
	public <Key, Value> List<Key> getKeys(PolicyCache<Key, Value> cache) {
		logger.debug("Method getKeys2Remove() called");

		int numberObjects2Remove = (int) (cache.size() * objects2RemoveFactor);
		List<Key> keys2Remove = new ArrayList<Key>(numberObjects2Remove);
		// if no objects are available to remove
		if (numberObjects2Remove <= 0) {
			logger.error("In the cache are no objects to remove");
			logger.error("Current cache size: " + cache.size());
			return keys2Remove;
		}

		PolicyValue<Value> obj2Remove = null;
		Key currentBestKey2Remove = null;
		PolicyValue<Value> currentBestValue2Remove = null;
		Iterator<Key> keyIter = cache.getInternalMap().keySet().iterator();
		while (keyIter.hasNext()) {
			Key currentKey = keyIter.next();
			PolicyValue<Value> currentObj = cache.getInternalMap().get(currentKey);
			if (currentObj != null) {
				if (keys2Remove.size() < numberObjects2Remove) {
					keys2Remove.add(currentKey);
				}
				else {
					if (currentBestKey2Remove == null) {
						currentBestKey2Remove = keys2Remove.get(0);
						currentBestValue2Remove = cache.getInternalMap().get(currentBestKey2Remove);
						for (Key key : keys2Remove) {
							PolicyValue<Value> value = cache.getInternalMap().get(key);
							if (!compareObjectWeight(currentBestValue2Remove, value)) {
								currentBestKey2Remove = key;
								currentBestValue2Remove = value;
							}
						}
					}
					if (!compareObjectWeight(currentBestValue2Remove, obj2Remove)) {
						keys2Remove.remove(currentBestKey2Remove);
						keys2Remove.add(currentKey);
						currentBestKey2Remove = null;
						currentBestValue2Remove = null;
					}
				}
			}
		}
		logger.debug(keys2Remove.size() + " elements will be removed (" + numberObjects2Remove + " should be removed)");
		return keys2Remove;
	}
	
	protected <Value> boolean compareObjectWeight(PolicyValue<Value> value1, PolicyValue<Value> value2) {
		if (value2 == null) {
			return false;
		}
		return (getObjectWeight(value1) > getObjectWeight(value2));
	}
	
	protected <Value> double getObjectWeight(PolicyValue<Value> value) {
		double lifetime = System.currentTimeMillis() - value.getCreated();
		double lastAccessedTime = System.currentTimeMillis() - value.getLastAccessed();
		if (lifetime == 0.0) {
			lifetime = 1;
		}
		if (lastAccessedTime == 0.0) {
			lastAccessedTime = 1;
		}
		return value.getAccessCount() / (lifetime * lastAccessedTime);
	}

}
