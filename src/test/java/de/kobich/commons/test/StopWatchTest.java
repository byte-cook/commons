package de.kobich.commons.test;

import org.junit.jupiter.api.Test;

import de.kobich.commons.StopWatch;

public class StopWatchTest {
	@Test
	public void testStopWatch() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		try {
			Thread.sleep(10);
		}
		catch (InterruptedException exc) {
		}
		
		System.out.println(stopWatch.stop());
		System.out.println("Elapsed time: " + stopWatch.getFormattedElapsedTime());
	}
}
