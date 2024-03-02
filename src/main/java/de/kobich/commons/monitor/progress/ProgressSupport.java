package de.kobich.commons.monitor.progress;

import javax.annotation.Nullable;

/**
 * Progress support that guarantees a responsive UI by sleeping periodically.  
 * @author ckorn
 */
public class ProgressSupport {
	public static final long SLEEP_INTERVAL_MILLIS = 500;
	private final IServiceProgressMonitor monitor;
	private long lastSleepMillis;

	public ProgressSupport(@Nullable IServiceProgressMonitor monitor) {
		this.monitor = monitor != null ? monitor : DummyProgressMonitor.INSTANCE;
		this.lastSleepMillis = System.currentTimeMillis() - (SLEEP_INTERVAL_MILLIS / 2);
	}

	public void monitorBeginTask(String message) {
		monitorBeginTask(new ProgressData(message, ProgressData.INDETERMINATE_MODE));
	}
	public void monitorBeginTask(String message, int totalCount) {
		monitorBeginTask(new ProgressData(message, totalCount));
	}
	public void monitorBeginTask(ProgressData monitorData) {
		monitor.beginTask(monitorData);
		sleepIfNecessary();
		if (monitor.isCanceled()) {
			throw new ProgressCancelException("Cancelled by user while: " + monitorData.getMessage());
		}
	}

	public void monitorEndTask(String message) {
		monitorEndTask(new ProgressData(message));
	}
	public void monitorEndTask(ProgressData monitorData) {
		monitor.endTask(monitorData);
	}

	public void monitorSubTask(String message, int count) {
		monitorSubTask(new ProgressData(message, count));
	}
	public void monitorSubTask(ProgressData monitorData) {
		monitor.subTask(monitorData);
		sleepIfNecessary();
		if (monitor.isCanceled()) {
			throw new ProgressCancelException("Cancelled by user while: " + monitorData.getMessage());
		}
	}

	private void sleepIfNecessary() {
		if (isSleepNecessary()) {
			try {
				Thread.sleep(5);
				this.lastSleepMillis = System.currentTimeMillis();
			}
			catch (InterruptedException e) {}
		}
	}

	private boolean isSleepNecessary() {
		long diff = Math.abs(System.currentTimeMillis() - lastSleepMillis);
		return diff >= SLEEP_INTERVAL_MILLIS;
	}

}
