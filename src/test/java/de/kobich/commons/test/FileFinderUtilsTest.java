package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.utils.FileFinderUtils;

public class FileFinderUtilsTest {
	private static final Logger logger = Logger.getLogger(FileFinderUtilsTest.class);
	public static final String ROOT_DIR = System.getProperty("user.dir");
	
	@BeforeEach
	public void init() {
		BasicConfigurator.configure();
	}
	
	@Test
	public void testValid() throws FileNotFoundException, URISyntaxException {
		List<File> dirs = new ArrayList<File>();
		dirs.add(new File(FileFinderUtilsTest.class.getResource("/filefinder/dir1").toURI()));
		dirs.add(new File(FileFinderUtilsTest.class.getResource("/filefinder/dir2").toURI()));
		dirs.add(new File(FileFinderUtilsTest.class.getResource("/filefinder").toURI()));
		
		List<String> filesNames = new ArrayList<String>();
		filesNames.add("name_1.2_win.properties");
		filesNames.add("name_win.properties");
		filesNames.add("name.properties");
		
		File f = FileFinderUtils.findFile(dirs, filesNames);
		logger.info("Found file: " + f.getAbsolutePath());
		assertNotNull(f);
	}
	
}
