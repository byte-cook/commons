package de.kobich.commons.utils;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.Channel;

public class StreamUtils {
	/**
	 * Closes a stream and hide potential exceptions
	 * @param c
	 */
	public static void forceClose(Closeable c) {
		if (c != null) {
			try {
				c.close();
			}
			catch (IOException exc) {
			}
		}
	}
	
	/**
	 * Closes a stream and hide potential exceptions
	 * @param c
	 */
	public static void forceClose(Channel c) {
		if (c != null) {
			try {
				c.close();
			}
			catch (IOException exc) {
			}
		}
	}
	
	/**
	 * Closes a stream
	 * @param c
	 * @throws IOException
	 */
	public static void close(Closeable c) throws IOException {
		if (c != null) {
			c.close();
		}
	}
}
