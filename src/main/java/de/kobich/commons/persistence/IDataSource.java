package de.kobich.commons.persistence;

/**
 * Data source (e.g. database, file system)
 * @author ckorn
 */
public interface IDataSource {
	/**
	 * Startups this data source
	 */
	public void startup();
	/**
	 * Shutdowns this data source
	 */
	public void shutdown();
	
	/**
	 * Return the session factory
	 * @return
	 */
	public ISessionFactory getSessionFactory();
}
