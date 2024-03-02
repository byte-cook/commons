package de.kobich.commons.parser.file.csv;

import java.io.File;

public class CSVParsingRequest {
	private File file;
	private boolean readHeader;

	/**
	 * @param file
	 * @param readHeader
	 */
	public CSVParsingRequest(File file, boolean readHeader) {
		this.file = file;
		this.readHeader = readHeader;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @return the readHeader
	 */
	public boolean isReadHeader() {
		return readHeader;
	}

}
