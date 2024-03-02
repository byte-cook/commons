package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.concurrent.DirectoryLock;

public class LockTest {
	private File lockFile;
	private List<Integer> started;

	@BeforeEach
	public void setUp() throws IOException {
		BasicConfigurator.configure();
		lockFile = File.createTempFile("test", ".lock");
		started = new ArrayList<Integer>();
	}

	@Test
	public void testDirLock() throws InterruptedException {
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 5; ++i) {
			LockTestRunnable r = new LockTestRunnable(i);
			Thread t = new Thread(r);
			t.start();
			threads.add(t);
		}
		
		for (Thread r : threads) {
			r.join();
		}
		assertTrue(started.size() == 1);
	}
	
	private class LockTestRunnable implements Runnable {
		private final int id;

		public LockTestRunnable(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			System.out.println("Check if locked: " + id);

			DirectoryLock lock = new DirectoryLock(lockFile);
			boolean lockAcquired = lock.tryLock();
			System.out.println("Thread " + id + " locked: " + lockAcquired);
			if (lockAcquired) {
				started.add(id);
			}
			try {
				Thread.sleep(50);
			}
			catch (InterruptedException e) {
			}
		}

	}
}
