package de.kobich.commons.runtime.executor.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.kobich.commons.utils.StreamUtils;

public class CommandDefinitionXMLParser {
	
	public CommandDefinition parseStream(InputStream in) throws IOException, XMLStreamException {
		XMLInputFactory xmlInFact = XMLInputFactory.newInstance();
		XMLStreamReader parser = xmlInFact.createXMLStreamReader(in);
		String command = null;
		List<CommandDefinitionParam> params = new ArrayList<CommandDefinitionParam>();
		List<CommandDefinitionEnv> environment = new ArrayList<CommandDefinitionEnv>();
		CommandDefinitionXMLTag lastTag = null;
		String cmdId = null;
		String key = null;
		String value = null;
		while (parser.hasNext()) {
			int event = parser.next();
			switch (event) {
				case XMLStreamConstants.END_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					if (CommandDefinitionXMLTag.ROOT.getTag().equals(parser.getLocalName())) {
					}
					else if (CommandDefinitionXMLTag.CMD.getTag().equals(parser.getLocalName())) {
						lastTag = CommandDefinitionXMLTag.CMD;
					}
					else if (CommandDefinitionXMLTag.PARAM.getTag().equals(parser.getLocalName())) {
						cmdId = parser.getAttributeValue(null, CommandDefinitionXMLTag.ATT_ID.getTag());
						lastTag = CommandDefinitionXMLTag.PARAM;
					}
					else if (CommandDefinitionXMLTag.ENV.getTag().equals(parser.getLocalName())) {
						cmdId = parser.getAttributeValue(null, CommandDefinitionXMLTag.ATT_ID.getTag());
					}
					else if (CommandDefinitionXMLTag.KEY.getTag().equals(parser.getLocalName())) {
						lastTag = CommandDefinitionXMLTag.KEY;
					}
					else if (CommandDefinitionXMLTag.VALUE.getTag().equals(parser.getLocalName())) {
						lastTag = CommandDefinitionXMLTag.VALUE;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					if (!parser.isWhiteSpace()) {
						if (CommandDefinitionXMLTag.CMD.equals(lastTag)) {
							command = parser.getText();
						}
						else if (CommandDefinitionXMLTag.PARAM.equals(lastTag)) {
							value = parser.getText();
						}
						else if (CommandDefinitionXMLTag.KEY.equals(lastTag)) {
							key = parser.getText();
						}
						else if (CommandDefinitionXMLTag.VALUE.equals(lastTag)) {
							value = parser.getText();
						}
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					if (CommandDefinitionXMLTag.PARAM.getTag().equals(parser.getLocalName())) {
						params.add(new CommandDefinitionParam(new CommandId(cmdId), value));
						key = cmdId = value = null;
					}
					else if (CommandDefinitionXMLTag.ENV.getTag().equals(parser.getLocalName())) {
						environment.add(new CommandDefinitionEnv(new CommandId(cmdId), key, value));
						key = cmdId = value = null;
					}
					break;
			}
		}
		return new CommandDefinition(command, params, environment);
	}
	
	public CommandDefinition parseFile(File xmlFile) throws IOException, XMLStreamException {
		InputStream in = null;
		try {
			in = new FileInputStream(xmlFile);
			return parseStream(in);
		}
		finally {
			StreamUtils.forceClose(in);
		}
	}
}
