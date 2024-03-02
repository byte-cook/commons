package de.kobich.commons.encrypt;

import de.kobich.commons.exception.ApplicationException;
import de.kobich.commons.exception.ErrorCode;

public class EncryptionException extends ApplicationException {
	private static final long serialVersionUID = 7896692833037579019L;
	public static final ErrorCode ENCRYPTION_ERROR = new ErrorCode("commons.encryption", "Encryption error");

	public EncryptionException(ErrorCode errorCode, Object... params) {
		super(errorCode, params);
	}

	public EncryptionException(ErrorCode errorCode, Throwable cause, Object... params) {
		super(errorCode, cause, params);
	}
}
