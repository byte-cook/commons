package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.kobich.commons.utils.FilenameUtils;
import de.kobich.commons.utils.SystemUtils;

public class FilenameUtilsTest {
	
	@Test
	public void testParentFolders() throws IOException {
		if (SystemUtils.isWindowsSystem()) {
			List<File> folders = FilenameUtils.getParentFolders(new File("C:/folder1/folder2/file.txt"));
			assertEquals(4, folders.size());
		}
	}
}
