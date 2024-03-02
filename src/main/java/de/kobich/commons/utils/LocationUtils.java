package de.kobich.commons.utils;

public class LocationUtils {
	
	/**
	 * Replaces location variables
	 * @param location
	 * @return
	 */
	public static String replaceLocationVariables(String location) {
		location = location.replace("@user.home", System.getProperty("user.home", ""));
		location = location.replace("@user.dir", System.getProperty("user.dir", ""));
		return location;
	}
}
