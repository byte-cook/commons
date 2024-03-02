package de.kobich.commons.encrypt.symmetric;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import de.kobich.commons.encrypt.EncryptionException;

/**
 * AES symmetric encryption.
 * @author ckorn
 */
public class AESEncryption {
	private static final String ALGORITHM = "AES";
	// Create 128-bit (16Byte) SecretKey from Passphrase;
	private static final int KEY_BYTE_LENGTH = 16;
	
	/**
	 * Creates a key
	 * @return
	 * @throws EncryptionException
	 */
	public Key createKey() throws EncryptionException {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			generator.init(KEY_BYTE_LENGTH);
			return generator.generateKey();
		}
		catch (NoSuchAlgorithmException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
	}
	
	/**
	 * Creates a key
	 * @param key
	 * @return
	 * @throws EncryptionException
	 */
	public Key createKey(String key) throws EncryptionException {
		byte[] byteKey = new byte[KEY_BYTE_LENGTH];
		for (int i = 0; i < key.length() && i < byteKey.length; i++) {
			byteKey[i] = (byte) key.charAt(i);
		}

		SecretKey secretKey = new SecretKeySpec(byteKey, ALGORITHM);
		return secretKey;
	}

	/**
	 * Encrypts an input string
	 * @param key
	 * @param input
	 * @return
	 * @throws EncryptionException
	 */
	public String encrypt(Key key, String input) throws EncryptionException {
		try { 
			Cipher encryptionCipher = Cipher.getInstance(ALGORITHM);
			encryptionCipher.init(Cipher.ENCRYPT_MODE, key);

			// Transform String into Byte Array using UTF-8
			byte[] utf8 = input.getBytes("UTF-8");
			// Encrypt the Byte-Array with the encryption Cipher
			byte[] enc = encryptionCipher.doFinal(utf8);
			// Retransform Byte-Array to String with BASE64Encoder
			return Base64.encodeBase64String(enc);
		}
		catch (BadPaddingException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
		catch (IllegalBlockSizeException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
		catch (UnsupportedEncodingException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
		catch (NoSuchAlgorithmException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
		catch (NoSuchPaddingException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
		catch (InvalidKeyException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
	}

	/**
	 * Decrypts an input string
	 * @param key
	 * @param input
	 * @return
	 * @throws EncryptionException
	 */
	public String decrypt(Key key, String input) throws EncryptionException {
		try {
			Cipher decryptionCipher = Cipher.getInstance(ALGORITHM);
			decryptionCipher.init(Cipher.DECRYPT_MODE, key);

			// use BASE64 for String->Byte
			byte[] dec = Base64.decodeBase64(input);
			// Decrypt the Byte-Array with encryption Cipher
			byte[] utf8 = decryptionCipher.doFinal(dec);
			// return the decrypted String in UTF-8 Encoding
			return new String(utf8, "UTF-8");
		}
		catch (BadPaddingException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
		catch (IllegalBlockSizeException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
		catch (UnsupportedEncodingException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
		catch (NoSuchAlgorithmException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
		catch (NoSuchPaddingException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
		catch (InvalidKeyException exc) {
			throw new EncryptionException(EncryptionException.ENCRYPTION_ERROR, exc);
		}
	}
}
