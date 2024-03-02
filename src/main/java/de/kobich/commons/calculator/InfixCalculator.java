package de.kobich.commons.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import de.kobich.commons.misc.tokenizer.Token;

/**
 * Calculator for infix notation:
 * <p />
 * <code>2 * 3 -> 6</code>
 * @author ckorn
 * @see http://www.chris-j.co.uk/parsing.php
 */
public class InfixCalculator implements ICalculator {
	private PostfixCalculator postfixCalculator;

	/**
	 * Constructor
	 */
	public InfixCalculator() {
		this(Locale.getDefault());
	}

	/**
	 * Constructor
	 * @param locale
	 */
	public InfixCalculator(Locale locale) {
		this.postfixCalculator = new PostfixCalculator(locale);
	}

	/*
	 * (non-Javadoc)
	 * @see de.kobich.commons.service.calculator.ICalculator#calc(java.lang.String)
	 */
	@Override
	public Number calc(String expression) {
		List<Token> tokens = toPostfix(expression);
		return postfixCalculator.calc(tokens);
	}

	/**
	 * Converts to postfix
	 * @param expression
	 * @return
	 */
	private List<Token> toPostfix(String expression) {
		List<Token> tokens = postfixCalculator.getTokenizer().tokenize(expression);
		List<Token> postfixTokens = new ArrayList<Token>(); 

		Stack<Token> stack = new Stack<Token>();
		for (Token token : tokens) {
			if (CalculatorToken.NUMBER.equals(token.getType())) {
				postfixTokens.add(token);
			}
			else {
				if (CalculatorToken.ADD.equals(token.getType())) {
					appendsOperatorsWithHigherPriority(token, stack, postfixTokens);
					stack.push(token);
				}
				else if (CalculatorToken.SUB.equals(token.getType())) {
					appendsOperatorsWithHigherPriority(token, stack, postfixTokens);
					stack.push(token);
				}
				else if (CalculatorToken.MUL.equals(token.getType())) {
					appendsOperatorsWithHigherPriority(token, stack, postfixTokens);
					stack.push(token);
				}
				else if (CalculatorToken.DIV.equals(token.getType())) {
					appendsOperatorsWithHigherPriority(token, stack, postfixTokens);
					stack.push(token);
				}
				else if (CalculatorToken.OPEN.equals(token.getType())) {
					stack.push(token);
				}
				else if (CalculatorToken.CLOSE.equals(token.getType())) {
					// pop operators off the stack and append them to the output until a opening bracket is on top
					while (!stack.empty()) {
						Token operator = stack.peek();
						if (!operator.getType().equals(CalculatorToken.OPEN)) {
							postfixTokens.add(stack.pop());
						}
						else {
							break;
						}
					}
					stack.pop();
				}
			}
		}

		// pop operators from stack and append it to the output
		while (!stack.empty()) {
			postfixTokens.add(stack.pop());
		}

		return postfixTokens;
	}

	/**
	 * Appends operators from stack to the output as long as they have higer priority than the given one
	 * @param token
	 * @param stack
	 * @param postfix
	 */
	public void appendsOperatorsWithHigherPriority(Token token, Stack<Token> stack, List<Token> postfixTokens) {
		int priority = getPriority(token);
		while (!stack.empty()) {
			Token operator = stack.peek();
			int topPriority = getPriority(operator);
			if (topPriority >= priority) {
				postfixTokens.add(stack.pop());
			}
			else {
				break;
			}
		}
	}

	/**
	 * Returns the priority of the given token (multiplication has higher priority than addition)
	 * @param token
	 * @return
	 */
	public int getPriority(Token token) {
		if (token.getType().equals(CalculatorToken.MUL) || token.getType().equals(CalculatorToken.DIV)) {
			return 2;
		}
		if (token.getType().equals(CalculatorToken.ADD) || token.getType().equals(CalculatorToken.SUB)) {
			return 1;
		}
		return 0;
	}

}
