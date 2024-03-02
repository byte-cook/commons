package de.kobich.commons.monitor.progress;

public class ProgressCancelException extends RuntimeException {
	private static final long serialVersionUID = 9102819445226945450L;

	/**
	 * @param message
	 */
	public ProgressCancelException() {
		super("Action cancelled");
	}
	/**
	 * @param message
	 */
	public ProgressCancelException(String message) {
		super(message);
	}
}
