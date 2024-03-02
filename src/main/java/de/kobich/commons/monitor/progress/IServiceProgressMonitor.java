package de.kobich.commons.monitor.progress;


/**
 * Progress monitor interface.
 * @author ckorn
 */
public interface IServiceProgressMonitor {
	/**
	 * Notifies that the main task is beginning.
	 * @param name
	 * @param totalWork the total number of work units into which the main task is been subdivided
	 */
	public void beginTask(ProgressData data);
	
	/**
	 * Notifies that the work is done.
	 */
	public void endTask(ProgressData data);
	
	/**
	 * Returns whether cancelation of current operation has been requested.
	 * @return
	 */
	public boolean isCanceled();
	
	/**
	 * Sets the cancel state to the given value.
	 * @param value
	 */
	public void setCanceled(boolean value);

	/**
	 * Notifies that a given number of work unit of the main task
	 * has been completed. Note that this amount represents an
	 * installment, as opposed to a cumulative amount of work done
	 * to date.<br/> 
	 * This method is frequently called with parameter 1 to inform 
	 * that 1 work unit of the total work is done.
	 * @param work
	 */
	public void worked(int work);

	/**
	 * Notifies that a subtask of the main task is beginning.
	 * @param name
	 */
	public void subTask(ProgressData data);
}
