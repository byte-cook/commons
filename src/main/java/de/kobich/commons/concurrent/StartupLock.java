package de.kobich.commons.concurrent;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * This class works as a lock. It prevents others from working until the system is fully initialised.
 * @author ckorn
 */
public class StartupLock {
	private static final Logger logger = Logger.getLogger(StartupLock.class);
	private final CountDownLatch countDownLatch;

	public StartupLock() {
		this(1);
	}
	public StartupLock(int count) {
		this.countDownLatch = new CountDownLatch(count);
	}
	
	public boolean isLocked() {
		return countDownLatch.getCount() > 0;
	}

	public void waitForInitialisation() {
		try {
			if (isLocked()) {
				logger.debug("Waiting for initialisation...");
			}
			countDownLatch.await();
		}
		catch (InterruptedException exception) {
		}
	}

	public void release() {
		countDownLatch.countDown();
	}

}
