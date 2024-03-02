package de.kobich.commons.persistence;

public class DataSourceException extends RuntimeException {
	private static final long serialVersionUID = 1252322485401962971L;

	/**
	 * 
	 */
	public DataSourceException() {
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DataSourceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DataSourceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DataSourceException(Throwable cause) {
		super(cause);
	}

}
