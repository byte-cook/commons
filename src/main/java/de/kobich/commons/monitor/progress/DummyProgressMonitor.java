package de.kobich.commons.monitor.progress;

public class DummyProgressMonitor implements IServiceProgressMonitor {
	public static IServiceProgressMonitor INSTANCE = new DummyProgressMonitor();
	
	private DummyProgressMonitor() {
	}

	@Override
	public void beginTask(ProgressData data) {}

	@Override
	public void endTask(ProgressData data) {}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void setCanceled(boolean value) {
	}

	@Override
	public void worked(int work) {}

	@Override
	public void subTask(ProgressData data) {}
}
