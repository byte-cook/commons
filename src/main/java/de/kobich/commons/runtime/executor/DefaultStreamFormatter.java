package de.kobich.commons.runtime.executor;

public class DefaultStreamFormatter implements IStreamFormatter {

	@Override
	public String format(String text, ExecutionStreamType type) {
		switch (type) {
			case COMMAND:
				return "> " + text;
			case MESSAGE:
				return "# " + text;
			case STANDARD:
			case ERROR:
				return "  " + text;
			default:
				return text;
		}
	}

}
