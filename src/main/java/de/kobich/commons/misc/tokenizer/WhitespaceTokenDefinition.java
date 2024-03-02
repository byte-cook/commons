package de.kobich.commons.misc.tokenizer;

public class WhitespaceTokenDefinition implements ITokenDefinition {
	private final TokenType type;
	
	public WhitespaceTokenDefinition(TokenType type) {
		this.type = type;
	}

	@Override
	public TokenType getType() {
		return type;
	}

	@Override
	public TokenMatch match(String expression, int index) {
		char c = expression.charAt(index);
		if (Character.isWhitespace(c)) {
			return new TokenMatch(true, "" + c);
		}
		return TokenMatch.NOT_MATCHING;
	}

}
