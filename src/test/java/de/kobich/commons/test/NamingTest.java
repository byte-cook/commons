package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.Renamer;
import de.kobich.commons.misc.rename.rule.AutoNumberRenameRule;
import de.kobich.commons.misc.rename.rule.CaseRenameRule;
import de.kobich.commons.misc.rename.rule.CuttingByIndexRenameRule;
import de.kobich.commons.misc.rename.rule.IRenameRule;
import de.kobich.commons.misc.rename.rule.InsertingByPositionRenameRule;
import de.kobich.commons.misc.rename.rule.RenamePositionType;
import de.kobich.commons.misc.rename.rule.CaseRenameRule.Case;
import de.kobich.commons.monitor.progress.IServiceProgressMonitor;
import de.kobich.commons.monitor.progress.SysoutProgressMonitor;

public class NamingTest {
	private static final IServiceProgressMonitor PROGRESS_MONITOR = new SysoutProgressMonitor();
	private List<IRenameable> audioFiles;
	
	/**
	 * Set up
	 * @testng.before-method
	 */
	@BeforeEach
	public void setUp() {
		audioFiles = new ArrayList<IRenameable>();
	}

	/**
	 * Tear down
	 * @testng.after-method
	 */
	@AfterEach
	public void tearDown() {
		audioFiles = null;
	}

	/**
	 * Tests rename files
	 * @throws IOException 
	 * @testng.test
	 */
	@Test
	public void testRename() throws IOException {
		final String rootRelativePath1 = "/dir/best of/";
		final String rootRelativePath2 = "/dir/album 2/";
		for (int i = 1; i < 20; ++ i) {
			String fileName;
			String category;
			if (i > 9) {
				fileName = "track" + i;
				category = rootRelativePath2;
			}
			else {
				fileName = "track0" + i;
				category = rootRelativePath1;
			}
			IRenameable file = new TestRenameable(category, fileName);
			audioFiles.add(file);
		}
		
		List<IRenameRule> renameFilters = new ArrayList<IRenameRule>();
		renameFilters.add(new InsertingByPositionRenameRule(RenamePositionType.BEFORE, "-"));
		renameFilters.add(new AutoNumberRenameRule(RenamePositionType.BEFORE, 1, 1, 2, true, false));
		renameFilters.add(new CuttingByIndexRenameRule(RenamePositionType.AFTER, 2));
		Renamer.rename(audioFiles, renameFilters, PROGRESS_MONITOR);

		for (IRenameable file : audioFiles) {
			System.out.println(file.getName() + "\t" + file.getCategory());
			boolean matches = file.getName().matches("\\d{2}-track");
			assertEquals(true, matches);
		}
	}

	/**
	 * Tests rename files for auto numbering
	 * @throws IOException 
	 * @testng.test
	 */
	@Test
	public void testRenameAutoNumbering() throws IOException {
		String relativePath1 = "/dir/best of/track1";
		IRenameable d1 = new TestRenameable(relativePath1, "track");
		audioFiles.add(d1);
		String relativePath2 = "/dir/simply the best/track1";
		IRenameable d2 = new TestRenameable(relativePath2, "track");
		audioFiles.add(d2);
		
		List<IRenameRule> renameFilters = new ArrayList<IRenameRule>();
		renameFilters.add(new InsertingByPositionRenameRule(RenamePositionType.BEFORE, "-"));
		renameFilters.add(new AutoNumberRenameRule(RenamePositionType.BEFORE, 1, 1, 2, true, false));
		Renamer.rename(audioFiles, renameFilters, PROGRESS_MONITOR);

		for (IRenameable file : audioFiles) {
			System.out.println(file.getName() + "\t" + file.getCategory());
			boolean matches = file.getName().matches("01-track");
			assertEquals(true, matches);
		}
	}

	/**
	 * Tests rename files for auto numbering
	 * @throws IOException 
	 * @testng.test
	 */
	@Test
	public void testRenameCase() throws IOException {
		String relativePath1 = "/dir/best of/track1";
		String name = "stones - let it bleed - song 2";
		IRenameable d1 = new TestRenameable(relativePath1, name);
		audioFiles.add(d1);
		
		List<IRenameRule> renameFilters = new ArrayList<IRenameRule>();
		renameFilters.add(new CaseRenameRule(Case.START_CASE));
		Renamer.rename(audioFiles, renameFilters, PROGRESS_MONITOR);
		
		for (IRenameable file : audioFiles) {
			assertEquals("Stones - Let It Bleed - Song 2", file.getName());
		}
	}
}
