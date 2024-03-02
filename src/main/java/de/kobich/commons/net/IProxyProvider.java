package de.kobich.commons.net;

import java.net.URL;

/**
 * Proxy for Internet access.
 */
public interface IProxyProvider {
	/**
	 * Inits this provider
	 */
	public void init();
	
	/**
	 * Returns the proxy for the given url or null, if no proxy is set
	 * @param url
	 * @return
	 */
	public Proxy getProxy(URL url);
	
	/**
	 * Disposes this provider
	 */
	public void dispose();
}
