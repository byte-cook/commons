package de.kobich.commons.encrypt.symmetric;

import java.security.Key;

import de.kobich.commons.encrypt.EncryptionException;
import de.kobich.commons.encrypt.SimpleKey;


public class CaesarEncryption {
	private final String ALGORITM = "Caesar";

	/**
	 * Creates a key
	 * @return
	 * @throws EncryptionException
	 */
	public Key createKey() throws EncryptionException {
		int key = (int) (Math.random() * 26);
		return createKey("" + key);
	}

	/**
	 * Creates a key
	 * @return
	 * @throws EncryptionException
	 */
	public Key createKey(String key) throws EncryptionException {
		return new SimpleKey(ALGORITM, key.getBytes());
	}

	/**
	 * Encrypts an input string
	 * @param keyPair
	 * @param input
	 * @return
	 * @throws EncryptionException
	 */
	public String encrypt(Key key, String input) throws EncryptionException {
		if (key instanceof SimpleKey) {
			SimpleKey sKey = (SimpleKey) key;
			int caesarKey = Integer.parseInt(sKey.getAsString());
			return caesar(caesarKey, input);
		}
		return null;
	}

	/**
	 * Decrypts an input string
	 * @param keyPair
	 * @param input
	 * @return
	 * @throws EncryptionException
	 */
	public String decrypt(Key key, String input) throws EncryptionException {
		if (key instanceof SimpleKey) {
			SimpleKey sKey = (SimpleKey) key;
			int caesarKey = Integer.parseInt(sKey.getAsString()) * -1;
			return caesar(caesarKey, input);
		}
		return null;
	}

	private String caesar(int key, String input) {
		int diff = 'Z' - 'A' + 1;
		key = key % diff;
		input = input.toUpperCase();
		
		String geheimText = "";
		int num;
		if (key >= 0) {
			for (int i = 0; i < input.length(); i++) {
				num = input.charAt(i);
				if (!Character.isLetter(num)) {
					// skip non-letters 
					;
				}
				else {
					num += key;
					if (num > 'Z') {
						// out of bounds
						num -= diff; 
					}
					geheimText += (char) num;
				}
			}
		}

		else {
			for (int i = 0; i < input.length(); i++) {
				num = input.charAt(i);
				if (num == ' ')
					;
				else {
					num += key;
					if (num < 'A') {
						num += diff;
					}
					geheimText += (char) num;
				}
			}
		}
		return geheimText;
	}

}
