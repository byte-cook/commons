package de.kobich.commons.parser.file.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.kobich.commons.parser.file.IParsingListener;

/**
 * CSV parser.
 * @author ckorn
 */
public class XMLParser {
	public static final String TAG_SEPARATOR = "/";
	public static final String ATTRIBUTE_BEGIN = "[@";
	public static final String ATTRIBUTE_END = "]";
	private IParsingListener parsingListener;
	
	/**
	 * Parses a file
	 * @param file
	 * @param header
	 * @throws ParsingException
	 */
	public void parse(XMLParsingRequest request) throws IOException, XMLStreamException {
		BufferedReader is = null;
		try {
			File file = request.getFile();
			String rootElementTag = request.getRootElementTag();
			
			// read complete file
			Stack<String> tagStack = new Stack<String>();
			Map<String, String> keyMap = new HashMap<String, String>();
			Map<String, String> uniqueNameMap = new HashMap<String, String>();
			
			is = new BufferedReader(new FileReader(file));
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(is);
			
			while (xmlStreamReader.hasNext()) {
				int event = xmlStreamReader.next();
				switch (event) {
				case XMLStreamConstants.END_DOCUMENT:
					xmlStreamReader.close();
					break;
				case XMLStreamConstants.START_ELEMENT:
					String startTag = xmlStreamReader.getLocalName();
					tagStack.push(startTag);
					for (int i = 0; i < xmlStreamReader.getAttributeCount(); ++ i) {
						String attributeName = xmlStreamReader.getAttributeName(i).getLocalPart();
						String attributeValue = xmlStreamReader.getAttributeValue(i);
						String key = getKey(tagStack, attributeName);
						keyMap.put(key, attributeValue);
						uniqueNameMap.put(attributeName, attributeValue);
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					tagStack.pop();
					String endTag = xmlStreamReader.getLocalName();
					if (endTag.equals(rootElementTag)) {
						if (parsingListener != null) {
							extractParsingItem(keyMap, uniqueNameMap);
							keyMap.clear();
							uniqueNameMap.clear();
						}
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					if (!xmlStreamReader.isWhiteSpace()) {
						String text = xmlStreamReader.getText().trim();
						String key = getKey(tagStack, null);
						if (keyMap.containsKey(key)) {
							text = keyMap.get(key) + text;
						}
						keyMap.put(key, text);
						uniqueNameMap.put(tagStack.peek(), text);
					}
					break;
				}
			}
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
	 * Returns the key
	 * @param tagStack
	 * @return
	 */
	private String getKey(Stack<String> tagStack, String attribute) {
		String key = "";
		for (String token : tagStack) {
			key += TAG_SEPARATOR;
			key += token;
		}
		if (attribute != null) {
			key += ATTRIBUTE_BEGIN + attribute + ATTRIBUTE_END;
		}
		return key;
	}
	
	/**
	 * Extracts parsing items
	 * @param keyMap
	 */
	private void extractParsingItem(Map<String, String> keyMap, Map<String, String> uniqueNameMap) {
		XMLParsingItem item = new XMLParsingItem(keyMap, uniqueNameMap);
		parsingListener.itemParsed(item);
	}

	/**
	 * @param parsingListener the parsingListener to set
	 */
	public void setParsingListener(IParsingListener parsingListener) {
		this.parsingListener = parsingListener;
	}
}
