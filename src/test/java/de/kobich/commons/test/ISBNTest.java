package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.kobich.commons.type.ISBN;

public class ISBNTest {
	@Test
	public void testValid() {
		assertTrue(ISBN.isValid("978-3-86680-192-9"));
		// wrong checksum
		assertFalse(ISBN.isValid("978-3-86680-192-5"));
		// illegal "-"
		assertFalse(ISBN.isValid("9783866801929"));
		assertFalse(ISBN.isValid("978-3-86--680-192-9"));
	}
	
	@Test
	public void testTokens() {
		ISBN isbn = new ISBN("978-3-86680-192-9");
		assertEquals(isbn.getCode(), "978-3-86680-192-9");
		assertEquals(isbn.getPrefix(), "978");
		assertEquals(isbn.getLanguage(), "3");
		assertEquals(isbn.getPublisher(), "86680");
		assertEquals(isbn.getTitle(), "192");
		assertEquals(isbn.getChecksum(), "9");
	}
}
