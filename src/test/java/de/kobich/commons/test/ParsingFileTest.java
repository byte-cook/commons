package de.kobich.commons.test;

import java.io.File;

import org.junit.jupiter.api.Test;

import de.kobich.commons.parser.file.IParsingItem;
import de.kobich.commons.parser.file.IParsingListener;
import de.kobich.commons.parser.file.csv.CSVParser;
import de.kobich.commons.parser.file.csv.CSVParsingRequest;
import de.kobich.commons.parser.file.xml.XMLParser;
import de.kobich.commons.parser.file.xml.XMLParsingRequest;

public class ParsingFileTest {

	@Test
	public void testCSVParser() throws Exception {
		File inputFile = new File(ParsingFileTest.class.getResource("/parse/test.csv").toURI());
		IParsingListener l = new IParsingListener() {
			@Override
			public void itemParsed(IParsingItem item) {
				System.out.println("==========================");
				System.out.println(item.getValue("0"));
				System.out.println(item.getValue("name"));
				System.out.println(item.getValue("text"));
				System.out.println(item.getValue("3"));
			}
		};
		CSVParser parser = new CSVParser();
		parser.setParsingListener(l);
		CSVParsingRequest request = new CSVParsingRequest(inputFile, true);
		parser.parse(request);
	}

	@Test
	public void testXMLParser() throws Exception {
		File inputFile = new File(ParsingFileTest.class.getResource("/parse/test.xml").toURI());
		IParsingListener l = new IParsingListener() {
			@Override
			public void itemParsed(IParsingItem item) {
				System.out.println(item.getValue("name"));
				System.out.println(item.getValue("/memos/memo[@category]"));
				System.out.println(item.getValue("/memos/memo"));
				System.out.println("==========================");
			}
		};
		XMLParser parser = new XMLParser();
		parser.setParsingListener(l);
		XMLParsingRequest request = new XMLParsingRequest(inputFile, "memo");
		parser.parse(request);

		// XPath xpath = XPathFactory.newInstance().newXPath();
		// XPathExpression xlogin = xpath.compile("//users/user[login/text()='" + login.getUserName() + "' and password/text() = '" +
		// login.getPassword() + "']/home_dir/text()");
		// Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("db.xml"));
		// String homedir = xlogin.evaluate(d);
	}
}
