package de.kobich.commons.runtime.executor;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Execution request.
 * @author ckorn
 */
public class ExecuteRequest {
	private List<String> command;
	private OutputStream logOutputStream;
	private OutputStream errorOutputStream;
	private IStreamFormatter formatter;
	private String message;
	private Map<String, String> env;
	private File workingDirectory;
	private boolean redirectErrorStream;
	
	/**
	 * Constructor
	 * @param command the command to execute
	 * @param waitFor indicates if this thread should wait until this process is over
	 */
	public ExecuteRequest(List<String> command, OutputStream logOutputStream, OutputStream errorOutputStream) {
		this.command = command;
		this.logOutputStream = logOutputStream;
		this.errorOutputStream = errorOutputStream;
		this.env = new HashMap<String, String>();
		this.redirectErrorStream = false;
		this.formatter = new DefaultStreamFormatter();
	}

	/**
	 * @return the command
	 */
	public List<String> getCommand() {
		return command;
	}

	/**
	 * @return the outputStream
	 */
	public OutputStream getLogOutputStream() {
		return logOutputStream;
	}

	/**
	 * @return the errorOutputStream
	 */
	public OutputStream getErrorOutputStream() {
		return errorOutputStream;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the env
	 */
	public Map<String, String> getEnv() {
		return env;
	}

	/**
	 * @param env the env to set
	 */
	public void setEnv(Map<String, String> env) {
		this.env = env;
	}

	/**
	 * @return the workingDirectory
	 */
	public File getWorkingDirectory() {
		return workingDirectory;
	}

	/**
	 * @param workingDirectory the workingDirectory to set
	 */
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	/**
	 * @return the redirectErrorStream
	 */
	public boolean isRedirectErrorStream() {
		return redirectErrorStream;
	}

	/**
	 * @param redirectErrorStream the redirectErrorStream to set
	 */
	public void setRedirectErrorStream(boolean redirectErrorStream) {
		this.redirectErrorStream = redirectErrorStream;
	}

	/**
	 * @return the formatter
	 */
	public IStreamFormatter getFormatter() {
		return formatter;
	}

	/**
	 * @param formatter the formatter to set
	 */
	public void setFormatter(IStreamFormatter formatter) {
		this.formatter = formatter;
	}
}
