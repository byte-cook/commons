package de.kobich.commons.runtime.executor.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.kobich.commons.runtime.executor.Executor;

/**
 * Registry for all external processes.
 * @author ckorn
 * @see Executor
 */
public class ExecutionProcessRegistry implements IExecutionProcessRegistry {
	private static final Logger logger = Logger.getLogger(ExecutionProcessRegistry.class);
	private List<ExecutionProcess> processes;
	
	/**
	 * Constructor
	 */
	public ExecutionProcessRegistry() {
		this.processes = new ArrayList<ExecutionProcess>();
	}
	
	@Override
	public synchronized void registerProcess(ExecutionProcess process) {
		this.processes.add(process);
	}
	
	@Override
	public synchronized boolean hasRunningProcesses() {
		boolean runningProcesses = false;
		for (ExecutionProcess process : processes) {
			try {
				process.getProcess().exitValue();
			} 
			catch (IllegalThreadStateException exc) {
				runningProcesses = true;
				break;
			}
		}
		return runningProcesses;
	}
	
	@Override
	public synchronized void destroyProcesses() {
		logger.info("Process count: " + processes.size());
		for (ExecutionProcess process : processes) {
			try {
		    	process.getProcess().destroy();
		    	process.getErrorGobbler().finish();
		    	process.getOutputGobbler().finish();
			}
			catch (Exception exc) {
				logger.error("Error while destroying processes", exc);
			}
		}
	}
}
