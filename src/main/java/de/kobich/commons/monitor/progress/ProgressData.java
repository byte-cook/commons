package de.kobich.commons.monitor.progress;

/**
 * Progress monitor data.
 * @author ckorn
 */
public class ProgressData {
	public static int INDETERMINATE_MODE = -1; 
	private String message;
	private int count;
	
	/**
	 * Constructor
	 * @param message
	 */
	public ProgressData(String message) {
		this(message, INDETERMINATE_MODE);
	}
	
	/**
	 * Constructor
	 * @param message
	 * @param count
	 */
	public ProgressData(String message, int count) {
		this.message = message;
		this.count = count;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Indicates if this progress is in indeterminate mode (length of a long-running task cannot be determined)
	 * @return
	 */
	public boolean isIndeterminate() {
		return getCount() == INDETERMINATE_MODE;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	
}
