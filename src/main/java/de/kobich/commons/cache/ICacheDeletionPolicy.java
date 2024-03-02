package de.kobich.commons.cache;

import java.util.List;

public interface ICacheDeletionPolicy {
	public <Key, Value> List<Key> getKeys(PolicyCache<Key, Value> cache);
}
