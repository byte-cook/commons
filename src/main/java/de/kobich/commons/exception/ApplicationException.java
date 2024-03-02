package de.kobich.commons.exception;

import java.util.ArrayList;
import java.util.List;

import de.kobich.commons.utils.PlaceholderUtil;

/**
 * Indicates an error in wparse.
 */
public class ApplicationException extends Exception {
	private static final long serialVersionUID = 156402542742725376L;
	private final ErrorCode errorCode;
	private final Object[] arguments;

	/**
	 * Constructor
	 * @param errorCode the error code
	 * @param args arguments
	 */
	public ApplicationException(ErrorCode errorCode, Object... args) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.arguments = args;
	}

	/**
	 * Constructor
	 * @param errorCode the error code
	 * @param args arguments
	 */
	public ApplicationException(ErrorCode errorCode, Throwable cause, Object... args) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
		this.arguments = args;
	}

	/**
	 * @return the errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * @return
	 */
	@Override
	public String getMessage() {
		String msg = errorCode.getMessage();
		if (getArguments().length > 0) {
			List<String> args = new ArrayList<String>();
			for (Object o : getArguments()) {
				args.add(o.toString());
			}
			msg = PlaceholderUtil.replace(msg, args.toArray(new String[args.size()]));
		}
		return msg;
	}

	/**
	 * @return the arguments
	 */
	public Object[] getArguments() {
		return arguments;
	}
}
