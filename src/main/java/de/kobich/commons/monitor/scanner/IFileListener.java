package de.kobich.commons.monitor.scanner;

import java.io.File;
import java.util.EventListener;

/**
 * Interface for listening to disk file changes.
 */
public interface IFileListener extends EventListener {
	/**
	 * Called when one of the monitored files are created.
	 * @param file File which has been changed.
	 */
	void fileCreated(File file);

	/**
	 * Called when one of the monitored files are modified.
	 * @param file File which has been changed.
	 */
	void fileModified(File file);

	/**
	 * Called when one of the monitored files are deleted.
	 * @param file File which has been changed.
	 */
	void fileDeleted(File file);
}
