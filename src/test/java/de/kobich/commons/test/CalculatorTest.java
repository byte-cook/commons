package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.calculator.InfixCalculator;
import de.kobich.commons.calculator.PostfixCalculator;

public class CalculatorTest {
	
	@BeforeEach
	public void setUp() {
		BasicConfigurator.configure();
	}
	
	@Test
	public void testPostfixCalculator() {
		final String postfixExp = "2 3 8 * + 4 48 4 2+/ 6 * + - =";
		Number result = new PostfixCalculator(Locale.ENGLISH).calc(postfixExp);
		assertEquals(-26.0, result.doubleValue());
	}

	@Test
	public void testInfixCalculator() {
		final String infixExp = "2.0 + (3 *8) - ( 4 + ( 48 / ( 4 + 2 ) ) * 6 ) ";
		Number result = new InfixCalculator(Locale.ENGLISH).calc(infixExp);
		assertEquals(-26.0, result.doubleValue());
	}
}
