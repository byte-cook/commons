package de.kobich.commons.ui.callback;

import java.util.HashMap;
import java.util.Map;

public class CallbackRepository {
	private Map<ICallbackType, ICallback<?, ?>> callbacks;

	public CallbackRepository() {
		this.callbacks = new HashMap<ICallbackType, ICallback<?, ?>>();
	}
	
	public void registerCallback(ICallbackType type, ICallback<?, ?> callback) {
		callbacks.put(type, callback);
	}
	
	public void deregisterCallback(ICallbackType type) {
		callbacks.remove(type);
	}

	@SuppressWarnings("unchecked")
	public <P, R> ICallback<P, R> getCallback(ICallbackType type) {
		if (callbacks.containsKey(type)) {
			return (ICallback<P, R>) callbacks.get(type);
		}
		return new DummyCallback<P, R>();
	}
	
}
