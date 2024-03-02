package de.kobich.commons.ui.i18n;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class to simplify use of internationalization message strings.
 * @author ckorn
 */
public class I18N {
	private I18N() {}
	
	/**
	 * Returns the message string with the specified key
	 * @param key
	 * @return
	 */
	public static String getString(II18NKey key) {
		return getString(key.getId(), key.getBundle(), key.getDefault());
	}
	
	/**
	 * Returns the message string with the specified key and parameters
	 * @param key
	 * @param args
	 * @return
	 */
	public static String getFormattedString(II18NKey key, Object... args) {
		return MessageFormat.format(getString(key.getId(), key.getBundle(), key.getDefault()), args);
	}
	
	/**
	 * Returns the message string with the specified key
	 * @param key
	 * @param bundle
	 * @return
	 */
	public static String getString(II18NKey key, ResourceBundle bundle) {
		return getString(key.getId(), bundle, key.getDefault());
	}
	
	/**
	 * Returns the message string with the specified key and parameters
	 * @param key
	 * @param bundle
	 * @param args
	 * @return
	 */
	public static String getFormattedString(II18NKey key, ResourceBundle bundle, Object... args) {
		return MessageFormat.format(getString(key.getId(), bundle, key.getDefault()), args);
	}
	
	/**
	 * Returns the message string with the specified key
	 * @param key
	 * @param bundle
	 * @return
	 */
	public static String getString(String key, ResourceBundle bundle) {
		return getString(key, bundle, null);
	}

	/**
	 * Returns the message string with the specified key and parameters
	 * @param key
	 * @param bundle
	 * @param args
	 * @return
	 */
	public static String getFormattedString(String key, ResourceBundle bundle, Object... args) {
		return MessageFormat.format(getString(key, bundle, null), args);
	}

	/**
	 * Returns the message string with the specified key
	 * @param key
	 * @param bundle
	 * @param defaultValue
	 * @return
	 */
	private static String getString(String key, ResourceBundle bundle, String defaultValue) {
		if (bundle != null) {
			try {
				return bundle.getString(key);
			}
			catch (MissingResourceException e) {
			}
		}
		if (defaultValue != null) {
			return defaultValue;
		}
		return "!" + key + "!";
	}
}
