package de.kobich.commons.persistence;

import de.kobich.commons.persistence.tx.ITransaction;

public interface IDataSourceContext {
	public ISession getSession();
	
	public ITransaction getTransaction();
}
