package de.kobich.commons.monitor.scanner;

import java.net.URL;
import java.util.EventListener;

/**
 * Interface for listening to url availability changes.
 */
public interface IURLListener extends EventListener {
	/**
	 * Called when one of the monitored urls are available.
	 * @param url url which has been changed.
	 */
	void urlAvailable(URL url);

	/**
	 * Called when one of the monitored urls are unavailable.
	 * @param url url which has been changed.
	 */
	void urlUnavailable(URL url);
}
