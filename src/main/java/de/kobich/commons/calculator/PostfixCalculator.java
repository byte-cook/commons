package de.kobich.commons.calculator;

import java.util.List;
import java.util.Locale;
import java.util.Stack;

import org.apache.log4j.Logger;

import de.kobich.commons.misc.tokenizer.Token;

/**
 * Calculator for postfix notation: 
 * <p />
 * <code>2 3 * -> 6</code>
 * @author ckorn
 */
public class PostfixCalculator implements ICalculator {
	private static final Logger logger = Logger.getLogger(PostfixCalculator.class);
	private final CalculatorTokenizer tokenizer;

	/**
	 * Constructor
	 */
	public PostfixCalculator() {
		this(Locale.getDefault());
	}
	/**
	 * Constructor
	 * @param locale
	 */
	public PostfixCalculator(Locale locale) {
		this.tokenizer = new CalculatorTokenizer(locale);
	}

	/*
	 * (non-Javadoc)
	 * @see test.calculator.ICalculator#calc(java.lang.String)
	 */
	@Override
	public Number calc(String expression) {
		List<Token> tokens = tokenizer.tokenize(expression);
		return calc(tokens);
	}
		
	public Number calc(List<Token> tokens) {
		Stack<Number> stack = new Stack<Number>();
		for (Token token : tokens) {
			if (CalculatorToken.NUMBER.equals(token.getType())) {
				stack.push(Double.parseDouble(token.getValue()));
			}
			else {
				if (CalculatorToken.ADD.equals(token.getType())) {
					// Found + operator, perform it immediately.
					stack.push(stack.pop().doubleValue() + stack.pop().doubleValue());
				}
				else if (CalculatorToken.SUB.equals(token.getType())) {
					// Found - operator, perform it (order matters).
					double operand2 = stack.pop().doubleValue();
					stack.push(stack.pop().doubleValue() - operand2);
				}
				else if (CalculatorToken.MUL.equals(token.getType())) {
					// Multiply is commutative
					stack.push(stack.pop().doubleValue() * stack.pop().doubleValue());
				}
				else if (CalculatorToken.DIV.equals(token.getType())) {
					// Handle division carefully: order matters!
					double operand2 = stack.pop().doubleValue();
					stack.push(stack.pop().doubleValue() / operand2);
				}
				else if (CalculatorToken.EQUAL.equals(token.getType())) {
					return stack.peek();
				}
				else {
					logger.error("Illegal token found: " + token.getValue());
					return null;
				}
			}
		}
		return stack.peek();
	}
	
	/**
	 * @return the tokenizer
	 */
	public CalculatorTokenizer getTokenizer() {
		return tokenizer;
	}
}
