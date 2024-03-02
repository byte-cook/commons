package de.kobich.commons.ui.callback;


public interface ICallback<P, R> {
	public R execute(P param);
}
