package de.kobich.commons.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedList;

import de.kobich.commons.Reject;

/**
 * This class implements a cache to find objects by its keys. 
 * If free memory runs low the cache removes rarly used objects automatically by using <code>SoftReference</code>'s.
 * So it is guaranteed that this cache cannot run out of memory. 
 * @author ckorn
 * @param <Key> the key
 * @param <Value> the value
 */
public class ReferenceCache<Key, Value> implements IObjectCache<Key, Value> {
	/** The internal HashMap that will hold the SoftReference. */
	private final HashMap<Key, SoftReference<Value>> objectMap = new HashMap<Key, SoftReference<Value>>();
	/** The number of "hard" references to hold internally. */
	private final int hardSize;
	/** The FIFO list of hard references, order of last access. */
	private final LinkedList<Value> hardCache = new LinkedList<Value>();
	/** Reference queue for cleared SoftReference objects. */
	private final ReferenceQueue<Value> queue = new ReferenceQueue<Value>();

	/**
	 * Default constructor.
	 */
	public ReferenceCache() {
		this(0);
	}

	/**
	 * @param hardSize The number of "hard" references to hold internally. 
	 */
	public ReferenceCache(int hardSize) {
		this.hardSize = hardSize;
	}

	@Override
	public synchronized Value get(Key key) {
		Value result = null;
		// We get the SoftReference represented by that key
		SoftReference<Value> softRef = (SoftReference<Value>) objectMap.get(key);
		if (softRef != null) {
			// From the SoftReference we get the value, which can be
			// null if it was not in the map, or it was removed in
			// the processQueue() method defined below
			result = softRef.get();
			if (result == null) {
				// If the value has been garbage collected, remove the
				// entry from the HashMap.
				objectMap.remove(key);
			}
			else {
				// We now add this object to the beginning of the hard
				// reference queue. One reference can occur more than
				// once, because lookups of the FIFO queue are slow, so
				// we don't want to search through it each time to remove
				// duplicates.
				hardCache.addFirst(result);
				if (hardCache.size() > hardSize) {
					// Remove the last entry if list longer than HARD_SIZE
					hardCache.removeLast();
				}
			}
		}
		return result;
	}

	@Override
	public synchronized Value put(Key key, Value value) {
		Reject.ifNull(key, "Key is null");

		processQueue(); // throw out garbage collected values first
		SoftReference<Value> previousValue = objectMap.put(key, new SoftValue<Key, Value>(value, key, queue));
		if (previousValue != null) {
			return previousValue.get();
		}
		return null;
	}

	@Override
	public synchronized boolean containsKey(Key key) {
		processQueue(); // throw out garbage collected values first
		return objectMap.containsKey(key);
	}

	@Override
	public synchronized boolean isEmpty() {
		processQueue(); // throw out garbage collected values first
		return objectMap.isEmpty();
	}

	@Override
	public synchronized void clear() {
		hardCache.clear();
		processQueue(); // throw out garbage collected values
		objectMap.clear();
	}

	@Override
	public synchronized Value remove(Key key) {
		processQueue(); // throw out garbage collected values first
		SoftReference<Value> previousValue = objectMap.remove(key);
		if (previousValue != null) {
			return previousValue.get();
		}
		return null;
	}

	@Override
	public synchronized int size() {
		processQueue(); // throw out garbage collected values first
		return objectMap.size();
	}

	/**
	 * Here we go through the ReferenceQueue and remove garbage collected SoftValue objects from the HashMap by looking them up using the
	 * SoftValue.key data member.
	 */
	@SuppressWarnings("unchecked")
	private void processQueue() {
		SoftValue<Key, Value> sv;
		while ((sv = (SoftValue<Key, Value>) queue.poll()) != null) {
			objectMap.remove(sv.key); // we can access private data!
		}
	}

	/**
	 * We define our own subclass of SoftReference which contains not only the value but also the key to make it easier to find the entry in the
	 * HashMap after it's been garbage collected.
	 */
	private static class SoftValue<Key, Value> extends SoftReference<Value> {
		private final Key key; // always make data member final

		/**
		 * Did you know that an outer class can access private data members and methods of an inner class? I didn't know that! I thought it was only
		 * the inner class who could access the outer class's private information. An outer class can also access private members of an inner class
		 * inside its inner class.
		 */
		private SoftValue(Value value, Key key, ReferenceQueue<Value> q) {
			super(value, q);
			this.key = key;
		}
	}
}
