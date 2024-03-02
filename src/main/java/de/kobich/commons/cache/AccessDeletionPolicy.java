package de.kobich.commons.cache;

/**
 * Access policy: This policy weights the number of calls to an cache object doubly.
 * @author ckorn
 */
public class AccessDeletionPolicy extends StandardDeletionPolicy {
	
	public AccessDeletionPolicy() {
		super();
	}
	public AccessDeletionPolicy(double objects2RemoveFactor) {
		super(objects2RemoveFactor);
	}
	
	@Override
	protected <Value> double getObjectWeight(PolicyValue<Value> value) {
		double lifetime = System.currentTimeMillis() - value.getCreated();
		double lastAccessedTime = System.currentTimeMillis() - value.getLastAccessed();
		if (lifetime == 0.0) {
			lifetime = 1;
		}
		if (lastAccessedTime == 0.0) {
			lastAccessedTime = 1;
		}
		return (value.getAccessCount() / lifetime) * (value.getAccessCount() / lastAccessedTime);
	}
}
