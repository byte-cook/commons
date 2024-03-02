package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import de.kobich.commons.misc.tokenizer.ExtractionTokenDefinition;
import de.kobich.commons.misc.tokenizer.ITokenDefinition;
import de.kobich.commons.misc.tokenizer.NumberFormatTokenDefinition;
import de.kobich.commons.misc.tokenizer.RegExTokenDefinition;
import de.kobich.commons.misc.tokenizer.StringTokenDefinition;
import de.kobich.commons.misc.tokenizer.Token;
import de.kobich.commons.misc.tokenizer.TokenType;
import de.kobich.commons.misc.tokenizer.Tokenizer;

public class TokenizerTest {

	@Test
	public void testParseMath() {
		final TokenType NUMBER = new TokenType("number");
		final TokenType ADD = new TokenType("add");
		final TokenType SUB = new TokenType("sub");
		final TokenType MUL = new TokenType("mul");
		final TokenType DIV = new TokenType("div");
		final TokenType OPEN = new TokenType("open");
		final TokenType CLOSE = new TokenType("close");

		final String expression = " -2.1 + (-3.0 * 8) - (4 + (48/(4+2)) * 6 ) ";
		List<ITokenDefinition> definitions = new ArrayList<ITokenDefinition>();
//		definitions.add(new RegExTokenDefinition(NUMBER, "-{0,1}\\d+[\\.\\,]{0,1}\\d*"));
		DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
		definitions.add(new NumberFormatTokenDefinition(NUMBER, format));
		definitions.add(new StringTokenDefinition(OPEN, "("));
		definitions.add(new StringTokenDefinition(CLOSE, ")"));
		definitions.add(new StringTokenDefinition(ADD, "+"));
		definitions.add(new StringTokenDefinition(SUB, "-"));
		definitions.add(new StringTokenDefinition(MUL, "*"));
		definitions.add(new StringTokenDefinition(DIV, "/"));

		List<Token> tokens = new Tokenizer(definitions).tokenize(expression);
		printTokens(tokens);
		assertEquals(23, tokens.size());
	}
	
	@Test
	public void testParseSearch() {
		final TokenType TEXT = new TokenType("text");
		final TokenType COMPARE_OP = new TokenType("compareOp");
		final TokenType OPEN = new TokenType("open");
		final TokenType CLOSE = new TokenType("close");
		final TokenType SEARCH_OP = new TokenType("searchOp");

		final String expression = "typ = testor or (orinhalt=2) a = \"le er\"  ";
		System.out.println(expression);
		
		List<ITokenDefinition> definitions = new ArrayList<ITokenDefinition>();
		definitions.add(new StringTokenDefinition(COMPARE_OP, "="));
		definitions.add(new StringTokenDefinition(SEARCH_OP, "OR ", "OR", false));
		definitions.add(new StringTokenDefinition(SEARCH_OP, "OR(", "OR", false));
		definitions.add(new StringTokenDefinition(OPEN, "("));
		definitions.add(new StringTokenDefinition(CLOSE, ")"));
		definitions.add(new ExtractionTokenDefinition(TEXT, "\\\"(.*)\\\"", 2));
		definitions.add(new RegExTokenDefinition(TEXT, "\\w+"));
		
		List<Token> tokens = new Tokenizer(definitions).tokenize(expression);
		printTokens(tokens);
		assertEquals(12, tokens.size());
	}
	
	private void printTokens(List<Token> tokens) {
		System.out.println(tokens.size() + " tokens");
		for (Token token : tokens) {
			String s = String.format("%-15s: %s", token.getType().getName(),  token.getValue()); 
			System.out.println(s);
		}
	}
}
