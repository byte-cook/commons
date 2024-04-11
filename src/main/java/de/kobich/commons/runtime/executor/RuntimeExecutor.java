package de.kobich.commons.runtime.executor;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Execute service.
 * @author ckorn
 */
public class RuntimeExecutor implements IExecutor {
	private static final Logger logger = Logger.getLogger(RuntimeExecutor.class);

	@Override
	public void executeCommand(ExecuteRequest request) throws IOException, InterruptedException {
		List<String> command = request.getCommand();
		String commandAsString = String.join(" ", command.toArray(new String[command.size()]));
		logger.info("Command: " + command);

		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(command.toArray(new String[0]));

		StreamForwardingThread outputForwardingThread = new StreamForwardingThread(ExecutionStreamType.STANDARD, process.getInputStream(), request.getLogOutputStream(),
				request.getFormatter());
		StreamForwardingThread errorForwardingThread = new StreamForwardingThread(ExecutionStreamType.ERROR, process.getErrorStream(), request.getErrorOutputStream(),
				request.getFormatter());
		outputForwardingThread.setCommand(commandAsString);
		outputForwardingThread.setMessage(request.getMessage());
		outputForwardingThread.start();
		errorForwardingThread.start();

		// wait for process end and check exit code
		int exitValue = process.waitFor();
		// waiting for output is forwarded completely
		outputForwardingThread.join();
		errorForwardingThread.join();
		logger.info("Exit value: " + exitValue);
	}
}
