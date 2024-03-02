package de.kobich.commons.monitor.progress;

public class SysoutProgressMonitor implements IServiceProgressMonitor {
	private boolean canceled;
	
	public SysoutProgressMonitor() {
		this.canceled = false;
	}

	@Override
	public void beginTask(ProgressData data) {
		System.out.println("++ " + data.getMessage());
	}

	@Override
	public void endTask(ProgressData data) {
		System.out.println("++ " + data.getMessage());
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void setCanceled(boolean value) {
		this.canceled = value;
	}

	@Override
	public void worked(int work) {}

	@Override
	public void subTask(ProgressData data) {
		System.out.println("++++ " + data.getMessage());
	}
}
