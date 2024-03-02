package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

import de.kobich.commons.utils.ConverterUtils;

public class ConverterUtilsTest {
	@Test
	public void testValid() {
		assertTrue(ConverterUtils.checkType("2023-01-31", Date.class));
	}
}
