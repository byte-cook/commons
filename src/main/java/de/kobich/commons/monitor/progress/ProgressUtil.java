package de.kobich.commons.monitor.progress;

@Deprecated // use ProgressSupport
public class ProgressUtil {
	
	/**
	 * Begins a task
	 * @param message
	 * @param monitor
	 */
	public static void monitorBeginTask(ProgressData monitorData, IServiceProgressMonitor monitor) {
		if (monitor != null) {
			monitor.beginTask(monitorData);
			sleep(100);
			if (monitor.isCanceled()) {
				throw new ProgressCancelException("Cancelled by user while: " + monitorData.getMessage());
			}
		}
	}
	
	/**
	 * Ends a task
	 * @param message
	 * @param monitor
	 */
	public static void monitorEndTask(ProgressData monitorData, IServiceProgressMonitor monitor) {
		if (monitor != null) {
			monitor.endTask(monitorData);
		}
	}
	
	/**
	 * Runs a sub task
	 * @param monitorData
	 * @param monitor
	 */
	public static void monitorSubTask(ProgressData monitorData, IServiceProgressMonitor monitor) {
		if (monitor != null) {
			monitor.subTask(monitorData);
			
			sleep(10);
			if (monitor.isCanceled()) {
				throw new ProgressCancelException("Cancelled by user while: " + monitorData.getMessage());
			}
		}
	}
	
	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
		}
	}
}
