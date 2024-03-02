package de.kobich.commons.encrypt;

import java.security.Key;

public class SimpleKey implements Key {
	private static final long serialVersionUID = 200204997908994100L;
	private final String algorithm;
	private final byte[] key;
	
	public SimpleKey(String algorithm, byte[] key) {
		this.algorithm = algorithm;
		this.key = key;
	}

	@Override
	public String getAlgorithm() {
		return algorithm;
	}

	@Override
	public String getFormat() {
		return null;
	}

	@Override
	public byte[] getEncoded() {
		return key;
	}
	
	public String getAsString() {
		return new String(getEncoded());
	}

}
