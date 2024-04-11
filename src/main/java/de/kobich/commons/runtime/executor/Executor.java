package de.kobich.commons.runtime.executor;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import de.kobich.commons.runtime.executor.process.ExecutionProcess;
import de.kobich.commons.runtime.executor.process.IExecutionProcessRegistry;

/**
 * Execute service.
 * @author ckorn
 */
public class Executor implements IExecutor {
	private static final Logger logger = Logger.getLogger(Executor.class);
	private IExecutionProcessRegistry executionProcessRegistry;

	@Override
	public void executeCommand(ExecuteRequest request) throws IOException, InterruptedException {
		List<String> command = request.getCommand();
		String commandAsString = String.join(" ", command.toArray(new String[command.size()]));
		logger.info("Command: " + commandAsString);

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		if (!request.getEnv().isEmpty()) {
			processBuilder.environment().putAll(request.getEnv());
		}
		if (request.getWorkingDirectory() != null) {
			processBuilder.directory(request.getWorkingDirectory());
		}
		processBuilder.redirectErrorStream(request.isRedirectErrorStream());
		final Process process = processBuilder.start();

		StreamForwardingThread outputForwardingThread = new StreamForwardingThread(ExecutionStreamType.STANDARD, process.getInputStream(), request.getLogOutputStream(),
				request.getFormatter());
		StreamForwardingThread errorForwardingThread = new StreamForwardingThread(ExecutionStreamType.ERROR, process.getErrorStream(), request.getErrorOutputStream(),
				request.getFormatter());
		outputForwardingThread.setCommand(commandAsString);
		outputForwardingThread.setMessage(request.getMessage());
		outputForwardingThread.start();
		errorForwardingThread.start();

		// add process to registry
		if (executionProcessRegistry != null) {
			ExecutionProcess externalProcess = new ExecutionProcess(process, outputForwardingThread, errorForwardingThread);
			executionProcessRegistry.registerProcess(externalProcess);
		}

		// wait for process end and check exit code
		int exitValue = process.waitFor();
		// waiting for output is forwarded completely
		outputForwardingThread.join();
		errorForwardingThread.join();
		logger.info("Exit value: " + exitValue);
	}

	/**
	 * @param executionProcessRegistry the executionProcessRegistry to set
	 */
	public void setExecutionProcessRegistry(IExecutionProcessRegistry executionProcessRegistry) {
		this.executionProcessRegistry = executionProcessRegistry;
	}
}
