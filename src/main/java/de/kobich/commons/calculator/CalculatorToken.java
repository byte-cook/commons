package de.kobich.commons.calculator;

import de.kobich.commons.misc.tokenizer.TokenType;

public class CalculatorToken {
	public static final TokenType NUMBER = new TokenType("number");
	public static final TokenType ADD = new TokenType("add");
	public static final TokenType SUB = new TokenType("sub");
	public static final TokenType MUL = new TokenType("mul");
	public static final TokenType DIV = new TokenType("div");
	public static final TokenType EQUAL = new TokenType("equal");
	public static final TokenType OPEN = new TokenType("open");
	public static final TokenType CLOSE = new TokenType("close");

}
