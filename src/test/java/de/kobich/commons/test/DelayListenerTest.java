package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import de.kobich.commons.ui.DelayListener;
import lombok.Getter;

public class DelayListenerTest {
	
	@Test
	public void testNumberOfCalls() throws InterruptedException {
		final TestDelayListener dl = new TestDelayListener(100, TimeUnit.MILLISECONDS);
		dl.delayEvent(1);
		dl.delayEvent(2);
		Thread.sleep(20);
		dl.delayEvent(3);
		Thread.sleep(120);
		dl.delayEvent(4);

		Thread.sleep(200);
		System.out.println("Calls: " + dl.getCount());
		assertTrue(dl.getCount() <= 2);
	}

	static class TestDelayListener extends DelayListener<Integer> {
		@Getter
		private int count = 0;
		
		public TestDelayListener(long delay, TimeUnit unit) {
			super(delay, unit);
		}
		
		@Override
		public void handleEvent(List<Integer> events) {
			count++;
		}
	};
}
