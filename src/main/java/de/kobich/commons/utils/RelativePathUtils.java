package de.kobich.commons.utils;

import java.io.File;

/**
 * Modify relative paths.
 */
public class RelativePathUtils {
	public static final String separator = "/";

	/**
	 * Returns the parent path e.g. <br>
	 * http://localhost is the parent path of http://localhost/images
	 * @param relativePath the path name
	 * @return the resulting path
	 */
	public static String getParent(String relativePath) {
		int index = relativePath.lastIndexOf(separator);
		if (index != -1) {
			return relativePath.substring(0, index);
		}
		return null;
	}

	/**
	 * Returns the last path component; e.g. <br>
	 * java.exe is the last path component of D:/Programs/Java/java.exe
	 * @param pathName the path name
	 * @return the last path component
	 */
	public static String getLastPathComponent(String pathName) {
		return new File(pathName).getName();
	}

	/**
	 * Convert backslashes to slashes: e.g. <br>
	 * D:\\Programs\\Java to D:/Programs/Java
	 * @param relativePath the path name
	 * @return the resulting path
	 */
	public static String convertBackslashToSlash(String relativePath) {
		if (relativePath == null) {
			return null;
		}
		String result = relativePath.replace("\\", separator);
		return result;
	}

	/**
	 * Ensures that the path starts with slash: e.g. <br>
	 * Programs  -> /Programs
	 * @param relativePath the path name
	 * @return the resulting path
	 */
	public static String ensureStartingSlash(String relativePath) {
		if (relativePath == null) {
			return null;
		}
		if (!relativePath.startsWith("/")) {
			relativePath = "/" + relativePath;
		}
		return relativePath;
	}

	/**
	 * Removes the ending slash if available; e.g. <br>
	 * D:/Programs/Java is the result of D:/Programs/Java/
	 * @param pathName the path name
	 * @return the resulting path
	 */
	public static String removeEndingSlash(String pathName) {
		if (pathName.endsWith(separator)) {
			pathName = pathName.substring(0, pathName.length() - 1);
		}
		return pathName;
	}

	/**
	 * Removes the starting slash if available; e.g. <br>
	 * Java/java.exe is the result of /Java/java.exe
	 * @param pathName the path name
	 * @return the resulting path
	 */
	public static String removeStartingSlash(String pathName) {
		if (pathName.startsWith(separator)) {
			pathName = pathName.substring(1);
		}
		return pathName;
	}

	/**
	 * Removes the extension of a path and returns the resulting path e.g. D:/Programs/Java/java is the result of D:/Programs/Java/java.exe
	 * @param pathName the path name
	 * @return the new path name without extension
	 */
	public static String removeExtension(String pathName) {
		int lastIndex = pathName.lastIndexOf('.');
		if (lastIndex != -1) {
			return pathName.substring(0, lastIndex);
		}
		return pathName;
	}

	/**
	 * Changes the extension of a path and returns it e.g. D:/Programs/Java/java.xml is the result of D:/Programs/Java/java.exe
	 * @param pathName the path name to change
	 * @param newExtension the new extension without dot, e.g. <code>xml</code>
	 * @return the new path
	 */
	public static String changeExtension(String pathName, String newExtension) {
		String pathNameWithoutExtension = removeExtension(pathName);
		return pathNameWithoutExtension + "." + newExtension;
	}

	/**
	 * Get the extension of a path e.g. exe is the result of D:/Programs/Java/java.exe
	 * @param pathName the path name
	 * @return the extension of the path
	 */
	public static String getExtension(String pathName) {
		int lastIndex = pathName.lastIndexOf('.');
		return pathName.substring(lastIndex + 1);
	}

	/**
	 * Indicates if the given pathName has an extension like .exe etc.
	 * @param pathName the path name
	 * @return true if the given path name has an extension like .exe etc.
	 */
	public static boolean hasExtension(String pathName) {
		String extension = getExtension(pathName);
		return !extension.equals(pathName);
	}

	/**
	 * Returns the path without the catalog name
	 * @param path the given path
	 * @return the path without catalog name
	 */
	public static String pathWithoutFirstPathComponent(String path) {
		int cutpos = path.indexOf("/");
		String pathwitoutcatalog = path;
		if (cutpos > -1) {
			pathwitoutcatalog = path.substring(cutpos);
		}
		return pathwitoutcatalog;
	}

	/**
	 * Returns the first part from the path e.g. D: is the result of D:/Programs/Java/java.exe
	 * @param path the given path
	 * @return the catalog name
	 */
	public static String getFirstPathComponent(String path) {
		String catalog = path;
		int cutpos = path.indexOf("/");
		if (cutpos > -1) {
			catalog = path.substring(0, cutpos);
		}
		return catalog;
	}

	/**
	 * Indicates if the relative path is the root element
	 * @param path the relative path
	 * @return boolean
	 */
	public static boolean isRoot(String path) {
		return path.equals("");
	}

	/**
	 * Indicates if the relative path contains of only one path component
	 * @param path the relative path
	 * @return boolean
	 */
	public static boolean hasOnlyOnePathComponent(String path) {
		return !isRoot(path) && !path.contains("/");
	}
}
