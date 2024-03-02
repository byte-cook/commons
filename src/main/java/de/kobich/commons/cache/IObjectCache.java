package de.kobich.commons.cache;

/**
 * This interface defines methods which are necessary for a cache implementation.
 */
public interface IObjectCache<Key, Value> {
	/**
     * Indicates whether an object to the specified <code>key</code> exists.
     * 
     * Note: Since the cache removes entries after reaching the maximum size, 
     * it is possible that inserted elements are not available. In this case 
     * the method returns false. 
     * 
     * So the caller have to reckon that inserted objects are later removed. 
     * 
     * @param key the key to which an object should be found
     * @return true if an object to the key exists, otherwise false
     */
    public boolean containsKey(Key key);
    
    /**
     * Gets the corresponding value object to the specified <code>key</code>
     * 
     * @param key the key to an object
     * @return the corresponding object from the cache, or null if the key does not exist
     */
    public Value get(Key key);
    
    /**
     * Adds a key/value-pair into the cache.
     * 
     * Note: If the cache size is reached, this method removes the rarly used cache entry. 
     * 
     * @param key the key for the value
     * @param value the corresponding value
     */
    public Value put(Key key, Value value);
    
    /**
     * Removes the key from the cache
     * @param key
     */
    public Value remove(Key key);
    
    /**
     * Returns all keys
     * @return keys
     */
//    public Set<Key> getKeys();
    
    /**
     * Returns all values
     * @return values
     */
//    public Set<Value> getValues();
    
    /**
     * Tests if the cache is empty
     * @return true or false
     */
    public boolean isEmpty();
    
    /**
     * Returns the current number of elements in the cache
     * @return the number of elements
     */
    public int size();
    
    /**
     * Clears this cache
     */
    public void clear();

}
