package de.kobich.commons.misc.tokenizer;


public class StringTokenDefinition implements ITokenDefinition {
	private final TokenType type;
	private final String text;
	private final String value;
	private final boolean caseSensitive;
	
	public StringTokenDefinition(TokenType type, String text) {
		this(type, text, text, true);
	}
	public StringTokenDefinition(TokenType type, String text, String value, boolean caseSensitive) {
		this.type = type;
		this.text = text;
		this.value = value;
		this.caseSensitive = caseSensitive;
	}

	@Override
	public TokenType getType() {
		return type;
	}

	@Override
	public TokenMatch match(String expression, int index) {
		if (caseSensitive && expression.startsWith(text, index)) {
			return new TokenMatch(true, value);
		}
		else if (!caseSensitive && expression.toUpperCase().startsWith(text.toUpperCase(), index)) {
			return new TokenMatch(true, value);
		}
		return TokenMatch.NOT_MATCHING;
	}

}
