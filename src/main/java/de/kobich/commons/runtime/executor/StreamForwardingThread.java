package de.kobich.commons.runtime.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import de.kobich.commons.utils.StreamUtils;

/**
 * Forwards execution output.
 * @author ckorn
 */
public class StreamForwardingThread extends Thread {
	private static final Logger logger = Logger.getLogger(StreamForwardingThread.class);
	private InputStream is;
	private ExecutionStreamType type;
	private PrintWriter forwardStream;
	private String command;
	private String message;
	private boolean finished;
	private IStreamFormatter formatter;

	/**
	 * Constructor
	 * @param type
	 * @param is
	 * @param forward
	 */
	public StreamForwardingThread(ExecutionStreamType type, InputStream is, OutputStream forward, IStreamFormatter formatter) {
		this.type = type;
		this.is = is;
		this.finished = false;
		this.formatter = formatter;
		if (forward != null) {
			this.forwardStream = new PrintWriter(forward);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		BufferedReader br = null;
		try {
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			
			// print message
			if (!StringUtils.isBlank(message)) {
				String outLine = formatter.format(message, ExecutionStreamType.MESSAGE);
				forwardStream.println(outLine);
				forwardStream.flush();
			}
			// print command
			if (!StringUtils.isBlank(command)) {
				String outLine = formatter.format(command, ExecutionStreamType.COMMAND);
				forwardStream.println(outLine);
				forwardStream.flush();
			}
			
			String line = null;
			while ((line = br.readLine()) != null && !finished) {
				String outLine = formatter.format(line, type);
				if (forwardStream != null) {
					forwardStream.println(outLine);
					forwardStream.flush();
				}
			}
		}
		catch (IOException exc) {
			logger.warn("Error while redirecting stream", exc);
		}
		finally {
			StreamUtils.forceClose(br);
		}
	}
	
	/**
	 * Finishes this thread 
	 */
	public void finish() {
		this.finished = true;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}
}
