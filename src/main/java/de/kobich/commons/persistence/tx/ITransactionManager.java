package de.kobich.commons.persistence.tx;

public interface ITransactionManager {
	public ITransaction beginTx();
	
	public void commitTx(ITransaction tx);
	
	public void rollbackTx(ITransaction tx);
	
	public void shutdown();
}
