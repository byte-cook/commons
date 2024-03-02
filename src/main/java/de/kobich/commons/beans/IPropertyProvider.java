package de.kobich.commons.beans;

/**
 * Allows to read/write properties
 * @author ckorn
 *
 * @param <T>
 */
public interface IPropertyProvider<T> {
	/**
	 * Returns the property
	 * @param obj the object to invoke
	 * @param name property name
	 * @param args arguments
	 * @return
	 */
	public Object getProperty(T obj, String name, Object... args);

	/**
	 * Returns the property
	 * @param <R> return type
	 * @param obj the object to invoke
	 * @param name property name
	 * @param returnType
	 * @param args arguments
	 * @return
	 */
	public <R> R getProperty(T obj, String name, Class<R> returnType, Object... args);

	/**
	 * Sets the property
	 * @param obj the object to invoke
	 * @param name property name
	 * @param value the value
	 * @param args arguments
	 */
	public void setProperty(T obj, String name, Object... args);
}
