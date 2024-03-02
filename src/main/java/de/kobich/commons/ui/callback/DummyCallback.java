package de.kobich.commons.ui.callback;


public class DummyCallback<P, R> implements ICallback<P, R> {

	@Override
	public R execute(P param) {
		return null;
	}
}
