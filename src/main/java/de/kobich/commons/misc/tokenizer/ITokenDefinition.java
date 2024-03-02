package de.kobich.commons.misc.tokenizer;

public interface ITokenDefinition {
	public static final int NO_MATCH = -1; 
	
	public TokenMatch match(String expression, int index);
	
	public TokenType getType();
}
