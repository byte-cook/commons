package de.kobich.commons.runtime.executor.command;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.kobich.commons.utils.FileFinderUtils;
import de.kobich.commons.utils.SystemUtils;

/**
 * Command line tool.
 */
public class CommandLineTool {
	public static final String FILE_POSTFIX = "-command.xml";
	private final String label;
	private final String baseName;
	private final String version;

	/**
	 * @param label
	 * @param version
	 * @param baseName
	 */
	public CommandLineTool(String label, String version, String baseName) {
		this.label = label;
		this.version = version;
		this.baseName = baseName;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the baseName
	 */
	public String getBaseName() {
		return baseName;
	}
	
	public String getFileName() {
		return baseName + FILE_POSTFIX;
	}
	
	/**
	 * Returns the internal definition stream or null
	 * @param type
	 * @return
	 */
	public InputStream getInternalDefinitionStream(Class<?> type) {
		String baseName = getBaseName();
		String os = SystemUtils.getOperationSystem();
		
		List<String> resourceFileNames = new ArrayList<String>();
		resourceFileNames.add(baseName + "-" + os + FILE_POSTFIX);
		resourceFileNames.add(baseName + FILE_POSTFIX);
		return FileFinderUtils.findInputStream(type, resourceFileNames);
	}
}
