package de.kobich.commons.misc.tokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExTokenDefinition implements ITokenDefinition {
	private final TokenType type;
	private final Pattern pattern;
	
	public RegExTokenDefinition(TokenType type, String regex) {
		this(type, regex, true);
	}
	public RegExTokenDefinition(TokenType type, String regex, boolean caseSensitive) {
		this.type = type;
		int flags = caseSensitive ? 0 : Pattern.CASE_INSENSITIVE;
		this.pattern = Pattern.compile(regex, flags);
	}

	@Override
	public TokenType getType() {
		return type;
	}

	@Override
	public TokenMatch match(String expression, int index) {
		String text = expression.substring(index);
		Matcher matcher = pattern.matcher(text);
		// match the input sequence against the pattern (starting at the beginning of the region)
		if (matcher.lookingAt()) {
			return new TokenMatch(true, matcher.group());
		}
		return TokenMatch.NOT_MATCHING;
	}

}
