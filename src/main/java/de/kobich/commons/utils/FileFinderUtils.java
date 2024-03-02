package de.kobich.commons.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.kobich.commons.Reject;

public class FileFinderUtils {
	private static final Logger logger = Logger.getLogger(FileFinderUtils.class);
	
	/**
	 * Searches for a file by given names in user home and working dir 
	 * @param fileNames
	 * @return
	 * @throws FileNotFoundException
	 */
	public static File findFile(List<String> fileNames) throws FileNotFoundException {
		return findFile(null, null, fileNames);
	}

	/**
	 * Searches for a file by given names in user home and working dir 
	 * @param homeSubFolder may be null
	 * @param workingSubFolder may be null
	 * @param fileNames
	 * @return file or null
	 * @throws FileNotFoundException
	 */
	public static File findFile(String homeSubFolder, String workingSubFolder, List<String> fileNames) {
		List<File> dirs = new ArrayList<File>();
		String userHome = System.getProperty("user.home");
		if (!StringUtils.isEmpty(homeSubFolder)) {
			userHome += File.separator + homeSubFolder;
		}
		dirs.add(new File(userHome));
		String workingDir = System.getProperty("user.dir");
		if (!StringUtils.isEmpty(workingSubFolder)) {
			workingDir += File.separator + workingSubFolder;
		}
		dirs.add(new File(workingDir));
		return findFile(dirs, fileNames);
	}
	
	/**
	 * Searches for a file in the given directories and given names 
	 * @param dirs
	 * @param fileNames
	 * @return file or null
	 */
	public static File findFile(List<File> dirs, List<String> fileNames) {
		Reject.ifNull(dirs, "Dirs is null");
		Reject.ifNull(fileNames, "File names is null");
		for (File dir : dirs) {
			for (String fileName : fileNames) {
				File file = new File(dir, fileName);
				logger.debug("File to check: " + file.getAbsolutePath());
				if (file.exists()) {
					return file;
				}
			}
		}
		return null;
	}
	
	/**
	 * Search for a file within a jar
	 * @param clazz class within the jar to search for
	 * @param fileNames
	 * @return
	 */
	public static InputStream findInputStream(Class<?> clazz, List<String> fileNames) {
		Reject.ifNull(clazz, "Clazz is null");
		Reject.ifNull(fileNames, "File names is null");
		for (String fileName : fileNames) {
			InputStream is = clazz.getResourceAsStream(fileName);
			if (is != null) {
				return is;
			}
		}
		return null;
	}
	
	/**
	 * Creates file name combinations
	 * @param name
	 * @param version
	 * @param postfix
	 * @return
	 */
	public static List<String> createFileNames(String name, String version, String postfix) {
		List<String> fileNames = new ArrayList<String>();
		String os = SystemUtils.getOperationSystem();
		fileNames.add(name + "-" + version + "-" + os + postfix);
		fileNames.add(name + "-" + version + postfix);
		fileNames.add(name + "-" + os + postfix);
		fileNames.add(name + postfix);
		return fileNames;
	}
	
	/**
	 * Creates directory combinations based on user home dir
	 * @param subFolder
	 * @return
	 */
	public static List<File> createDirectories(String... subFolders) {
		File directory = new File(System.getProperty("user.home"));
		return createDirectories(directory, subFolders);
	}
	
	/**
	 * Creates directory combinations
	 * @param subFolder
	 * @return
	 */
	public static List<File> createDirectories(File directory, String... subFolders) {
		List<File> dirs = new ArrayList<File>();
		dirs.add(directory);
		for (String subFolder : subFolders) {
			dirs.add(new File(directory, subFolder));
		}
		return dirs;
	}
	
}
