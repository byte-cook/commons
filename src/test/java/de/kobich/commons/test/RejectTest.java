package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import de.kobich.commons.Reject;

public class RejectTest {
	
	@Test
	public void testNonReject() {
		Reject.ifFalse(true);
		Reject.ifNull(new Object());
		Reject.ifTrue(false);
		Reject.ifEmpty(Arrays.asList(1, 2));
		Reject.ifNotEmpty(new ArrayList<String>());
		
		assertTrue(true);
	}
	
	@Test
	public void testReject() {
		try {
			Reject.ifNull(null);
			assertTrue(false);
		}
		catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
}
