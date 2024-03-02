package de.kobich.commons.misc.tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * This tokenizer breaks a stream of text into tokens.
 * @author ckorn
 */
public class Tokenizer {
	private final List<ITokenDefinition> definitions;
	private ITokenDefinition delim;
	
	public Tokenizer(List<ITokenDefinition> definitions) {
		this.definitions = definitions;
		this.delim = new WhitespaceTokenDefinition(null);
	}
	
	public List<Token> tokenize(String expression) {
		List<Token> tokens = new ArrayList<Token>();
		
		int index = 0;
		while (index < expression.length()) {
			TokenMatch delimMatch = delim.match(expression, index);
			if (delimMatch.isMatching()) {
				index += delimMatch.getLength();
				continue;
			}
			
			
			int length = 0;
			for (ITokenDefinition definition : definitions) {
				TokenMatch match = definition.match(expression, index);
				if (match.isMatching()) {
					String value = match.getValue();
					length = match.getLength();
					Token token = new Token(definition.getType(), value);
					tokens.add(token);
					
					break;
				}
			}
			index += Math.max(length, 1);
		}
		return tokens;
	}

	public void setDelim(ITokenDefinition delim) {
		this.delim = delim;
	}
	
}
