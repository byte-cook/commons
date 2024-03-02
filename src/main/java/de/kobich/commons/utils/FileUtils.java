package de.kobich.commons.utils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileUtils {

	/**
	 * Checks, whether the child directory is a subdirectory of the base directory.
	 * @param base the base directory.
	 * @param child the suspected child directory.
	 * @return true, if the child is a subdirectory of the base directory.
	 * @throws IOException if an IOError occured during the test.
	 */
	public static boolean isSubDirectory(File base, File child) throws IOException {
		base = base.getCanonicalFile();
		child = child.getCanonicalFile();

		File parentFile = child;
		while (parentFile != null) {
			if (base.equals(parentFile)) {
				return true;
			}
			parentFile = parentFile.getParentFile();
		}
		return false;
	}
	
	/**
	 * Returns the first existing parent file of the given path
	 * @param path
	 * @return
	 */
	public static Optional<File> getFirstExistingPath(final String path) {
		if (path == null) {
			return Optional.empty();
		}
		
		File f = new File(path);
		do {
			if (f.exists()) {
				return Optional.of(f);
			}
			f = f.getParentFile();
		}
		while (f != null);
		return Optional.empty();
	}
}
