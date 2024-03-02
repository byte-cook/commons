package de.kobich.commons.ui.memento;

/**
 * Serialize and deserialize objects.
 * @author ckorn
 *
 * @param <T> object to save/restore
 */
public interface IMementoItemSerializer2<T> extends IMementoItemSerializer<T> {

	/**
	 * Restores an object
	 * @param mementoItem
	 * @return
	 */
	public T restore(IMementoItem mementoItem);
}
