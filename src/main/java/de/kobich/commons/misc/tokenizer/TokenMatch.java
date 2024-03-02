package de.kobich.commons.misc.tokenizer;

public class TokenMatch {
	public static final TokenMatch NOT_MATCHING = new TokenMatch(false, "");
	private final boolean matching;
	private final String value;
	private final int length;

	public TokenMatch(boolean matching, String value) {
		this(matching, value, value.length());
	}
	public TokenMatch(boolean matching, String value, int length) {
		this.matching = matching;
		this.value = value;
		this.length = length;
	}

	public boolean isMatching() {
		return matching;
	}

	public String getValue() {
		return value;
	}
	
	public int getLength() {
		return length;
	}

}
