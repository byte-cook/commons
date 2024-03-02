package de.kobich.commons.runtime.executor;

import java.io.IOException;


/**
 * Execution service.
 * @author ckorn
 */
public interface IExecutor {
	/**
	 * Executes a command and blocks until the command is completed
	 * @param request
	 * @throws InterruptedException 
	 * @throws FileExecutionException
	 */
	public void executeCommand(ExecuteRequest request) throws IOException, InterruptedException;
}
