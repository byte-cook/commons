package de.kobich.commons.runtime.executor.process;

import de.kobich.commons.runtime.executor.StreamForwardingThread;


public class ExecutionProcess {
	private Process process;
	private StreamForwardingThread outputGobbler;
	private StreamForwardingThread errorGobbler;
	
	public ExecutionProcess(Process process, StreamForwardingThread outputGobbler, StreamForwardingThread errorGobbler) {
		super();
		this.process = process;
		this.outputGobbler = outputGobbler;
		this.errorGobbler = errorGobbler;
	}

	/**
	 * @return the process
	 */
	public Process getProcess() {
		return process;
	}

	/**
	 * @return the outputGobbler
	 */
	public StreamForwardingThread getOutputGobbler() {
		return outputGobbler;
	}

	/**
	 * @return the errorGobbler
	 */
	public StreamForwardingThread getErrorGobbler() {
		return errorGobbler;
	}
	
}
