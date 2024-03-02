package de.kobich.commons.misc.extract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import de.kobich.commons.monitor.progress.IServiceProgressMonitor;
import de.kobich.commons.monitor.progress.ProgressData;
import de.kobich.commons.monitor.progress.ProgressSupport;
import de.kobich.commons.utils.RegularExpressionUtils;

/**
 * Defines methods to analyze structures.
 * @author ckorn
 */
public class Extractor {
	private static final Logger logger = Logger.getLogger(Extractor.class);
	
	public static ExtractStructureResponse extract(Set<IText> texts, String structurePattern, Collection<StructureVariable> variables) {
		return extract(texts, structurePattern, variables, null);
	}
	
	public static ExtractStructureResponse extract(Set<IText> texts, String structurePattern, Collection<StructureVariable> variables, @Nullable IServiceProgressMonitor monitor) {
		String fileStructurePattern = structurePattern;
		fileStructurePattern = RegularExpressionUtils.escapePattern(fileStructurePattern);
		logger.info("File structure pattern: " + fileStructurePattern);
		
		ProgressSupport support = new ProgressSupport(monitor);
		support.monitorBeginTask(new ProgressData("Reading file structure..."));

		// create patterns for each variable (track, album, etc.)
		Map<StructureVariable, Pattern> variable2Pattern = new HashMap<StructureVariable, Pattern>();
		for (StructureVariable variable : variables) {
			if (fileStructurePattern.contains(variable.getName())) {
				String pattern = fileStructurePattern;
				// ignore all variables except the current one
				for (StructureVariable variable2Ignore : variables) {
					if (!variable.equals(variable2Ignore)) {
						pattern = pattern.replaceAll(variable2Ignore.getName(), variable2Ignore.getIgnorePattern());
					}
					else {
//						pattern = pattern.replaceAll(variable2Ignore.getName(), "(.*)");
						pattern = pattern.replaceAll(variable2Ignore.getName(), variable2Ignore.getExtractPattern());
					}
				}
				logger.info("Pattern for " + variable.getName() + ": " + pattern);
				variable2Pattern.put(variable, Pattern.compile(pattern));
			}
		}

		// determine audio data for each file
		Map<IText, Map<StructureVariable, String>> succeededPatternFiles = new HashMap<IText, Map<StructureVariable, String>>();
		List<IText> failedPatternFiles = new ArrayList<IText>();
		for (IText text : texts) {
			String relativePath = text.getText();
			logger.debug("Relative path: " + relativePath);
			
			// monitor message
			support.monitorSubTask(new ProgressData("Read file structure for: " + text.getText()));
			Map<StructureVariable, String> fileStructure2Name = new HashMap<StructureVariable, String>();
			boolean patternSucceeded = false;
			// determine variable data by patterns
			for (StructureVariable variable : variable2Pattern.keySet()) {
				Pattern pattern = variable2Pattern.get(variable);
				Matcher matcher = pattern.matcher(relativePath);
				if (matcher.find()) {
					String name = matcher.group(1);
					if (name.contains("/")) {
						logger.debug("Pattern is wrong, too many slashes.");
						patternSucceeded = false;
						break;
					}
					patternSucceeded = true;
					logger.debug(variable.getName() + ": \t" + name);
					fileStructure2Name.put(variable, name);
				}
				else {
					logger.debug("Pattern is wrong.");
					patternSucceeded = false;
					break;
				}
			}
			if (patternSucceeded) {
				succeededPatternFiles.put(text, fileStructure2Name);
			}
			else {
				failedPatternFiles.add(text);
			}
		}
		support.monitorEndTask(new ProgressData("File structure read"));
		return new ExtractStructureResponse(succeededPatternFiles, failedPatternFiles);
	}
	
	public static String assemble(String structurePattern, Map<StructureVariable, String> variable2ValueMap) {
		String structure = structurePattern;
		for (StructureVariable variable : variable2ValueMap.keySet()) {
			String value = variable2ValueMap.get(variable);
			structure = structure.replace(variable.getName(), value);
		}
		logger.info("Structure: " + structure);
		return structure;
	}
}
