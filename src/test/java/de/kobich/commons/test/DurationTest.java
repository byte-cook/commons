package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import de.kobich.commons.type.Duration;

/**
 * Test class for the <code>Duration</code> POJO
 */
public class DurationTest {
	private static final String DATE_FORMAT = "HH:mm:ss:SSS";
	private static final String LONG_DATE_FORMAT = "0:HH:mm:ss:SSS";
	private static final int MAX_MSECS = 24 * 60 * 60 * 1000 - 1;
	
	@Test
	public void test() {
		assertEquals(0, new Duration().getTime());
		assertEquals(343, new Duration("0:0:0:343").getTime());
		assertEquals(3600000, new Duration(1, 0, 0, 00).getTime());
		
		Duration d = new Duration(1, 1, 1, 1);
		assertEquals(1, d.getMillis());
		assertEquals(1, d.getSeconds());
		assertEquals(1, d.getMinutes());
		assertEquals(1, d.getHours());
	}

	@Test
	public void testFormat() {
		Duration dur = null;
		try {
			new Duration("foo");
		}
		catch (IllegalArgumentException ex) {
		}
		assertTrue(dur == null);
		dur = null;

		DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		String durStr = format.format(now);
		try {
			dur = new Duration(durStr);
		}
		catch (IllegalArgumentException ex) {
		}
		assertFalse(dur == null);
		assertEquals(new SimpleDateFormat(LONG_DATE_FORMAT).format(now), dur.getAsString());
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), dur.getHours());
		assertEquals(cal.get(Calendar.MINUTE), dur.getMinutes());
		assertEquals(cal.get(Calendar.SECOND), dur.getSeconds());
		assertEquals(cal.get(Calendar.MILLISECOND), dur.getMillis());
		dur = null;
	}

	@Test
	public void testRange() {
		Duration dur = null;
		try {
			new Duration(24, 0, 0, 0);
		}
		catch (IllegalArgumentException ex) {
		}
		assertTrue(dur == null);
		dur = null;

		try {
			new Duration(0, 0, 0, -1);
		}
		catch (IllegalArgumentException ex) {
		}
		assertTrue(dur == null);
		dur = null;

		try {
			dur = new Duration(23, 59, 59, 999);
		}
		catch (IllegalArgumentException ex) {
		}
		assertFalse(dur == null);
		assertEquals(dur.getTime(), MAX_MSECS);
		dur = null;
	}

	@Test
	public void testConverter() {
		Duration dur = null;
		try {
			dur = new Duration(0, 0, 0, MAX_MSECS);
		}
		catch (IllegalArgumentException ex) {
		}
		assertFalse(dur == null);
		assertEquals(23, dur.getHours());
		assertEquals(59, dur.getMinutes());
		assertEquals(59, dur.getSeconds());
		assertEquals(999, dur.getMillis());
		dur = null;
	}

}
