package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.Key;
import java.security.KeyPair;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.encrypt.SimpleKey;
import de.kobich.commons.encrypt.asymmetric.RSAEncryption;
import de.kobich.commons.encrypt.symmetric.AESEncryption;
import de.kobich.commons.encrypt.symmetric.CaesarEncryption;
import de.kobich.commons.utils.SystemUtils;

/**
 * Tests class encryption.
 */
public class EncryptionTest {
	
	/**
	 * Set up
	 * @testng.before-method
	 */
	@BeforeEach
	public void setUp() {
	}

	/**
	 * Tear down
	 * @testng.after-method
	 */
	@AfterEach
	public void tearDown() {
	}
	
	/**
	 * Tests Caesar encryption
	 * @throws Exception
	 */
	@Test
	protected static void testCaesarEncryption() throws Exception {
		CaesarEncryption caesar = new CaesarEncryption();
		Key key = caesar.createKey("37");
		System.out.println(((SimpleKey) key).getAsString());
		String plainText = "ALSDUDENSCHLUESSELHASTGENANNT";
		String encrypted = caesar.encrypt(key, plainText);
		System.out.println("encrypted: " + encrypted);
//		String encrypted = "PEHLDHLDPHTRHLPSCE";
		String clearText = caesar.decrypt(key, encrypted);
		System.out.println("clearText: " + clearText);
		assertEquals(plainText, clearText);
	}

	/**
	 * Tests RSA encryption
	 * @throws Exception
	 */
	@Test
	public void testRSA() throws Exception {
		RSAEncryption assService = new RSAEncryption();
		KeyPair assKey = assService.createKey();
		System.out.println("Key: " + assKey);
		
		String plainText = "hallo test *äö";
		String encrypted = assService.encrypt(assKey, plainText);
		System.out.println("encrypted: " + encrypted);
		String clearText = assService.decrypt(assKey, encrypted);
		System.out.println("clearText: " + clearText);
		assertEquals(plainText, clearText);
	}

	/**
	 * Tests AES encryption
	 * @throws Exception
	 */
	@Test
	public void testAES() throws Exception {
		String macAddress = SystemUtils.getMacAddress().orElseThrow();
		System.out.println("macAddress: " + macAddress);
		
		AESEncryption symService = new AESEncryption();
		Key symKey = symService.createKey(macAddress);
		System.out.println("Key: " + symKey);
		String plainText = "hallo test *äö";
		String encrypted = symService.encrypt(symKey, plainText);
		System.out.println("encrypted: " + encrypted);
		String clearText = symService.decrypt(symKey, encrypted);
		System.out.println("clearText: " + clearText);
		assertEquals(plainText, clearText);
	}
}
