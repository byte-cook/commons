package de.kobich.commons.monitor.progress;



/**
 * Request for progress monitoring.
 * @author ckorn
 */
public class ProgressMonitorRequest {
	private IServiceProgressMonitor monitor;

	/**
	 * Constructor
	 */
	public ProgressMonitorRequest() {}
	
	/**
	 * Sets the progress monitor
	 * @param progressMonitor
	 */
	public void setProgressMonitor(IServiceProgressMonitor monitor) {
		this.monitor = monitor;
	}
	
	/**
	 * Indicates if progress monitor is used
	 * @return boolean
	 */
	public boolean useProgressMonitor() {
		return monitor != null;
	}

	/**
	 * @return the support
	 */
	public IServiceProgressMonitor getProgressMonitor() {
		return monitor;
	}
}
