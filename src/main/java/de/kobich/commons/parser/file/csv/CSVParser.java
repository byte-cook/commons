package de.kobich.commons.parser.file.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.kobich.commons.parser.file.IParsingListener;

/**
 * CSV parser.
 * @author ckorn
 */
public class CSVParser {
	public static final String SEPARATOR = ";";
	private IParsingListener parsingListener;
	
	/**
	 * Parses a file
	 * @param file
	 * @param header
	 * @throws IOException
	 */
	public void parse(CSVParsingRequest request) throws IOException {
		BufferedReader is = null;
		try {
			File file = request.getFile();
			boolean readHeader = request.isReadHeader();
			
			// read complete file
			List<String> headerTokens = null;
			String line = "";
			is = new BufferedReader(new FileReader(file));
			while ((line = is.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				if (parsingListener != null) {
					if (readHeader) {
						headerTokens = extractHeaderTokens(line);
						readHeader = false;
					}
					else {
						extractParsingItem(line, headerTokens);
					}
				}
			}
			is.close();
		}
		finally {
			try {
				if (is != null)
					is.close();
			}
			catch (IOException exc) {
			}
		}
	}
	
	/**
	 * Extracts header tokens
	 * @param line
	 * @return
	 */
	private List<String> extractHeaderTokens(String line) {
		List<String> header = new ArrayList<String>();
		String[] tokens = line.split(SEPARATOR);
		for (String token : tokens) {
			token = escapeToken(token);
			header.add(token);
		}
		return header;
	}
	
	/**
	 * Extracts parsing items
	 * @param line
	 * @param headerTokens
	 */
	private void extractParsingItem(String line, List<String> headerTokens) {
		Map<String, String> keyMap = new HashMap<String, String>();
		Map<Integer, String> indexMap = new HashMap<Integer, String>();
		String[] tokens = line.split(SEPARATOR);
		for (int i = 0; i < tokens.length; ++ i) {
			String token = tokens[i]; 
			token = escapeToken(token);
			String key = "" + i;
			if (headerTokens != null && headerTokens.size() > i) {
				key = headerTokens.get(i);
			}
			keyMap.put(key, token);
			indexMap.put(i, token);
		}
		
		CSVParsingItem item = new CSVParsingItem(keyMap, indexMap);
		parsingListener.itemParsed(item);
	}
	
	private String escapeToken(String token) {
		if (token.startsWith("\"") && token.endsWith("\"")) {
			token = token.substring(1, token.length() - 1);
		}
		token = token.replace("\"\"", "\"");
		return token;
	}

	/**
	 * @param parsingListener the parsingListener to set
	 */
	public void setParsingListener(IParsingListener parsingListener) {
		this.parsingListener = parsingListener;
	}
}
