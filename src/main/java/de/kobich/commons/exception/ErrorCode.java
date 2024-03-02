package de.kobich.commons.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * ErrorCode collects all possible exception cases.
 * @author ckorn
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Getter
public class ErrorCode {
	public static final ErrorCode NO_ERROR = new ErrorCode(null);
	
	@EqualsAndHashCode.Include
	private final String id;
	@ToString.Include
	private final String message;

	public ErrorCode(String id) {
		this(id, null);
	}

}
