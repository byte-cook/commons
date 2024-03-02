package de.kobich.commons.cache;


public interface ICacheLimitationPolicy {
	/**
	 * Indicates if elements should be removed
	 * @return boolean
	 */
	public <Key, Value> boolean isLimitReached(PolicyCache<Key, Value> cache);
}
