package de.kobich.commons.misc.tokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractionTokenDefinition implements ITokenDefinition {
	private final TokenType type;
	private final Pattern pattern;
	private final int add2Length;
	
	public ExtractionTokenDefinition(TokenType type, String regex, int add2Length) {
		this.type = type;
		this.pattern = Pattern.compile(regex + ".*");
		this.add2Length = add2Length;
	}

	@Override
	public TokenType getType() {
		return type;
	}

	@Override
	public TokenMatch match(String expression, int index) {
		String text = expression.substring(index);
		Matcher matcher = pattern.matcher(text);
		if (matcher.matches()) {
			String value = matcher.group(1);
			return new TokenMatch(true, value, value.length() + add2Length);
		}
		return TokenMatch.NOT_MATCHING;
	}

}
