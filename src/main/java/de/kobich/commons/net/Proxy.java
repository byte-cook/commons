package de.kobich.commons.net;

/**
 * A Proxy.
 * @author ckorn
 */
public class Proxy {
	private String server;
	private int port;
	private String username;
	private String password;

	/**
	 * @param server
	 * @param port
	 * @param username
	 * @param password
	 */
	public Proxy(String server, int port, String username, String password) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

}
