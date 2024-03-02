package de.kobich.commons.runtime.executor.process;

import de.kobich.commons.runtime.executor.Executor;

/**
 * Registry for all external processes.
 * @author ckorn
 * @see Executor
 */
public interface IExecutionProcessRegistry {
	/**
	 * Registers a process
	 * @param process
	 */
	public void registerProcess(ExecutionProcess process);
	
	/**
	 * Indicates if there are running processes
	 * @return
	 */
	public boolean hasRunningProcesses();
	
	/**
	 * Destroys all running processes
	 */
	public void destroyProcesses();
}
