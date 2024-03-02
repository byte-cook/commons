package de.kobich.commons.parser.file.xml;

import java.io.File;

public class XMLParsingRequest {
	private File file;
	private String rootElementTag;

	/**
	 * @param file
	 * @param rootElementTag
	 */
	public XMLParsingRequest(File file, String rootElementTag) {
		this.file = file;
		this.rootElementTag = rootElementTag;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @return the rootTag
	 */
	public String getRootElementTag() {
		return rootElementTag;
	}

}
