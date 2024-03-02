package de.kobich.commons.misc.tokenizer;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class NumberFormatTokenDefinition implements ITokenDefinition {
	private final TokenType type;
	private final NumberFormat format;
	
	public NumberFormatTokenDefinition(TokenType type, NumberFormat format) {
		this.type = type;
		this.format = format;
	}

	@Override
	public TokenType getType() {
		return type;
	}

	@Override
	public TokenMatch match(String expression, int index) {
		ParsePosition pos = new ParsePosition(index);
		Number number = format.parse(expression, pos);
		if (number != null && pos.getIndex() > index) {
			return new TokenMatch(true, "" + number, pos.getIndex() - index);
		}
		return TokenMatch.NOT_MATCHING;
	}

}
