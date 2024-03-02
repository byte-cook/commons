package de.kobich.commons.persistence;

import javax.transaction.xa.XAResource;

public interface ISession {
	public <T> T getSession(Class<T> type);
	
	public XAResource getXAResource();

}
