package de.kobich.commons.net;

/**
 * Request for proxy.
 * @author ckorn
 */
public class ProxyProviderRequest {
	private IProxyProvider proxyProvider;

	/**
	 * Constructor
	 */
	public ProxyProviderRequest() {}
	
	/**
	 * Sets the proxyProvider
	 * @param proxyProvider
	 */
	public void setProxyProvider(IProxyProvider proxyProvider) {
		this.proxyProvider = proxyProvider;
	}
	
	/**
	 * Indicates if a proxy provider is used
	 * @return boolean
	 */
	public boolean useProxyProvider() {
		return proxyProvider != null;
	}

	/**
	 * @return the proxyProvider
	 */
	public IProxyProvider getProxyProvider() {
		return proxyProvider;
	}
}
