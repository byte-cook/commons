package de.kobich.commons.misc.extract;

import java.util.List;
import java.util.Map;

/**
 * Response of text structure analysis.
 * @author ckorn
 */
public class ExtractStructureResponse {
	private final Map<IText, Map<StructureVariable, String>> succeededTexts;
	private final List<IText> failedTexts;
	
	/**
	 * Constructor
	 * @param succeededTexts the texts for which the pattern succeeded
	 * @param failedTexts the texts for which the pattern failed
	 */
	public ExtractStructureResponse(Map<IText, Map<StructureVariable, String>> succeededPatternFiles, List<IText> failedPatternFiles) {
		this.succeededTexts = succeededPatternFiles;
		this.failedTexts = failedPatternFiles;
	}

	/**
	 * @return the succeededTexts
	 */
	public Map<IText, Map<StructureVariable, String>> getSucceededTexts() {
		return succeededTexts;
	}

	/**
	 * @return the failedTexts
	 */
	public List<IText> getFailedTexts() {
		return failedTexts;
	}
}
