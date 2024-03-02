package de.kobich.commons.concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import de.kobich.commons.utils.StreamUtils;

public class SocketLock {
	private static final Logger logger = Logger.getLogger(SocketLock.class);
	private final ISocketLockListener lockMessageListener;
	private final int port;
	private final InetAddress address;
	private final String message;
	private ServerSocket server;

	public SocketLock(int port, ISocketLockListener lockMessageListener, String message) throws IOException {
		this(InetAddress.getLocalHost(), port, lockMessageListener, message);
	}

	public SocketLock(InetAddress address, int port, ISocketLockListener lockMessageListener, String message) {
		this.address = address;
		this.port = port;
		this.lockMessageListener = lockMessageListener;
		this.message = message;
	}

	/**
	 * Attempts to acquire a lock
	 * @param message 
	 * @return indicates if a lock could be acquired 
	 * @throws IOException
	 */
	public boolean tryLock() {
		try {
			if (!isLocked()) {
				this.server = new ServerSocket(port);
				Thread serverThread = new Thread(new ServerLockThread());
				serverThread.start();
				return true;
			}
			else {
				Socket clientSocket = new Socket(address, port);
				PrintWriter out = null;
				try {
					out = new PrintWriter(clientSocket.getOutputStream(), true);
					out.println(message);
					return false;
				}
				finally {
					StreamUtils.forceClose(out);
					clientSocket.close();
				}
			}
		}
		catch (IOException ex) {
			return false;
		}
	}

	@SuppressWarnings("resource")
	private boolean isLocked() {
		try {
			new Socket(address, port);
			return true;
		}
		catch (IOException ex) {
			return false;
		}
	}

	/**
	 * Releases this lock
	 */
	public void release() {
		try {
			if (this.server != null) {
				this.server.close();
			}
		}
		catch (IOException exc) {
			logger.error("", exc);
		}
	}

	private class ServerLockThread implements Runnable {

		@Override
		public void run() {
			boolean socketClosed = false;
			while (!socketClosed) {
				if (server.isClosed()) {
					socketClosed = true;
				}
				else {
					Socket client = null;
					BufferedReader in = null;
					try {
						client = server.accept();
						client.setSoTimeout(1000);
						in = new BufferedReader(new InputStreamReader(client.getInputStream()));
						String message = in.readLine();
						logger.info("Retrieving message: " + message);
						if (lockMessageListener != null) {
							lockMessageListener.messageSent(message);
						}
					}
					catch (IOException ex) {
						// logger.error("", ex);
						socketClosed = false;
					}
					finally {
						StreamUtils.forceClose(in);
						try {
							if (client != null) {
								client.close();
							}
						}
						catch (IOException exc) {
						}
					}
				}
			}
		}
	}

	public interface ISocketLockListener {
		void messageSent(String message);
	}
}
