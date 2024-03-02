package de.kobich.commons.persistence;

public interface ISessionFactory {
	public ISession createSession();
	public void closeSession(ISession session);
	public IDataSourceContext getContext();
}
