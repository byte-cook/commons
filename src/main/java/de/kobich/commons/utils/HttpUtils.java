package de.kobich.commons.utils;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	
	public static boolean exists(String URLName) {
		try {
			HttpURLConnection.setFollowRedirects(true);
//			HttpURLConnection.setInstanceFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		}
		catch (Exception e) {
			return false;
		}
	}
}
