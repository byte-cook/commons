package de.kobich.commons.calculator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.kobich.commons.misc.tokenizer.ITokenDefinition;
import de.kobich.commons.misc.tokenizer.NumberFormatTokenDefinition;
import de.kobich.commons.misc.tokenizer.StringTokenDefinition;
import de.kobich.commons.misc.tokenizer.Token;
import de.kobich.commons.misc.tokenizer.Tokenizer;

public class CalculatorTokenizer {
	private final Locale locale;
	
	
	public CalculatorTokenizer(Locale locale) {
		this.locale = locale;
	}
	
	public List<Token> tokenize(String expression) {
		List<ITokenDefinition> definitions = new ArrayList<ITokenDefinition>();
		DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		definitions.add(new NumberFormatTokenDefinition(CalculatorToken.NUMBER, format));
		definitions.add(new StringTokenDefinition(CalculatorToken.OPEN, "("));
		definitions.add(new StringTokenDefinition(CalculatorToken.CLOSE, ")"));
		definitions.add(new StringTokenDefinition(CalculatorToken.ADD, "+"));
		definitions.add(new StringTokenDefinition(CalculatorToken.SUB, "-"));
		definitions.add(new StringTokenDefinition(CalculatorToken.MUL, "*"));
		definitions.add(new StringTokenDefinition(CalculatorToken.DIV, "/"));

		List<Token> tokens = new Tokenizer(definitions).tokenize(expression);
		return tokens;
	}
}
