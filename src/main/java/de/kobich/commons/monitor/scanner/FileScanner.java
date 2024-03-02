package de.kobich.commons.monitor.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This is the timer thread which is executed every n milliseconds according to the setting of the file monitor. 
 * It investigates the file in question and notify listeners if changed.
 */
public class FileScanner extends AbstractScanner<IFileListener> {
	private HashMap<File, Long> files;
	
	private final int FILE_NOT_EXIST = -1;
	private enum ModificationType {
		FILE_CREATED, FILE_MODIFIED, FILE_DELETED
	}

	/**
	 * Constructor
	 */
	public FileScanner() {
		super(IFileListener.class);
		this.files = new HashMap<File, Long>();
	}
	
	/**
	 * Add file to listen for. File may be any java.io.File (including a directory) and may well be a non-existing file in the case where the creating
	 * of the file is to be trepped.
	 * <p>
	 * More than one file can be listened for. When the specified file is created, modified or deleted, listeners are notified.
	 * @param file File to listen for
	 */
	public synchronized void addFile(File file) {
		if (!files.containsKey(file)) {
			long modifiedTime = file.exists() ? file.lastModified() : -1;
			files.put(file, Long.valueOf(modifiedTime));
		}
	}
	
	/**
	 * Add several files to listen for. Files may be any java.io.File (including a directory) and may well be a non-existing file in the case where the creating
	 * of the file is to be trepped.
	 * <p>
	 * More than one file can be listened for. When the specified file is created, modified or deleted, listeners are notified.
	 * @param files files to listen for
	 */
	public void addFiles(File[] files) {
		for (File f : files) {
			addFile(f);
		}
	}

	/**
	 * Remove specified file for listening.
	 * @param file File to remove
	 */
	public synchronized void removeFile(File file) {
		files.remove(file);
	}
	
	/**
	 * Remove several files for listening.
	 * @param files files to remove
	 */
	public void removeFiles(File[] files) {
		for (File f : files) {
			removeFile(f);
		}
	}

	/**
	 * Loops over the registered files and see which have changed.
	 * Note:
	 * This method uses a copy of the listener list in case listener wants to alter the
	 * list within its event method.
	 */
	public void run() {
		synchronized (files) {
			Collection<File> filesCollection = new ArrayList<File>(files.keySet());
			Iterator<File> i = filesCollection.iterator();
			while (i.hasNext()) {
				File file = i.next();
				long lastModifiedTime = ((Long) files.get(file)).longValue();
				long newModifiedTime = file.exists() ? file.lastModified() : FILE_NOT_EXIST;
	
				// Chek if file has changed
				if (newModifiedTime != lastModifiedTime) {
					// Register new modified time
					files.put(file, Long.valueOf(newModifiedTime));
					
					// Notify listeners
					ModificationType modificationType = ModificationType.FILE_MODIFIED;
					if (lastModifiedTime == FILE_NOT_EXIST) {
						modificationType = ModificationType.FILE_CREATED;
					}
					else if (newModifiedTime == FILE_NOT_EXIST) {
						modificationType = ModificationType.FILE_DELETED;
					}
					notifyListeners(file, modificationType);
				}
			}
		}
	}
	
	/**
	 * Notifies all registered listeners
	 * @param file the file that was changed
	 * @param modificationType the type of the modification
	 */
	private void notifyListeners(File file, ModificationType modificationType) {
		IFileListener[] listeners = getListeners();
        for (int i = 0; i < listeners.length; ++ i) {
			IFileListener listener = listeners[i];
			if (modificationType == ModificationType.FILE_CREATED) {
				listener.fileCreated(file);
			}
			else if (modificationType == ModificationType.FILE_MODIFIED) {
				listener.fileModified(file);
			}
			else if (modificationType == ModificationType.FILE_DELETED) {
				listener.fileDeleted(file);
			}
		}
	}
}
