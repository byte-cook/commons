package de.kobich.commons.persistence.tx;

public interface ITransaction {
	public <T> T getTx(Class<T> type);
}
