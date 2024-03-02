package de.kobich.commons.ui.memento;


/**
 * Interface to a memento used for saving the important state of an object
 * in a form that can be persisted in the file system.
 * @author ckorn
 */
public interface IMementoItem {
	
	/**
	 * Returns a string
	 * @param key
	 * @return
	 */
	public String getString(String key);

	/**
	 * Returns a string with default value
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key, String defaultValue);

	/**
	 * Puts a string
	 * @param key
	 * @param value
	 */
	public void putString(String key, String value);
	
	/**
	 * Returns a boolean
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key);

	/**
	 * Puts a boolean
	 * @param key
	 * @param value
	 */
	public void putBoolean(String key, boolean value);

	/**
	 * Returns an integer
	 * @param key
	 * @return
	 */
	public int getInteger(String key);
	
	/**
	 * Returns an integer with default value
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInteger(String key, int defaultValue);

	/**
	 * Puts an integer
	 * @param key
	 * @return
	 */
	public void putInteger(String key, int value);
	
	/**
	 * Returns an array
	 * @param key
	 * @return
	 */
	public String[] getArray(String key);

	/**
	 * Puts an array
	 * @param key
	 * @param array
	 */
	public void putArray(String key, String[] array);
	

	
}
