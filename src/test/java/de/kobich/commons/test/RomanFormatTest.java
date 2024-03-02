package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.format.RomanFormat;

/**
 * Tests class for Roman format.
 */
public class RomanFormatTest {
	/**
	 * Set up
	 * @testng.before-method
	 */
	@BeforeEach
	public void setUp() {
	}

	/**
	 * Tear down
	 * @testng.after-method
	 */
	@AfterEach
	public void tearDown() {
	}
	
	/**
	 * Tests Caesar encryption
	 * @throws Exception
	 */
	@Test
	protected static void testRomanFormat() throws Exception {
		assertEquals("I", RomanFormat.toRoman(1));
		assertEquals("IV", RomanFormat.toRoman(4));
		assertEquals("V", RomanFormat.toRoman(5));
		assertEquals("VII", RomanFormat.toRoman(7));
		assertEquals("VIII", RomanFormat.toRoman(8));
		assertEquals("IX", RomanFormat.toRoman(9));
		assertEquals("XX", RomanFormat.toRoman(20));
		assertEquals("LIV", RomanFormat.toRoman(54));
		assertEquals("LXXXIX", RomanFormat.toRoman(89));
		assertEquals("C", RomanFormat.toRoman(100));
		assertEquals("D", RomanFormat.toRoman(500));
		assertEquals("MM", RomanFormat.toRoman(2000));
		assertEquals(null, RomanFormat.toRoman(5000));
		assertEquals("-MCCXLIV", RomanFormat.toRoman(-1244));
	}

}
