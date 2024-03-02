package de.kobich.commons.ui.memento;

/**
 * Serialize and deserialize objects.
 * @author ckorn
 *
 * @param <T> object to save/restore
 */
public interface IMementoItemSerializer<T> {
	/**
	 * Saves an object
	 * @param t
	 * @param mementoItem
	 */
	public void save(T t, IMementoItem mementoItem);

	/**
	 * Restores an object which is already given
	 * @param t
	 * @param mementoItem
	 */
	public void restore(T t, IMementoItem mementoItem);

}
