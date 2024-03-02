package de.kobich.commons.monitor.scanner;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This is the timer thread which is executed every n milliseconds according to the setting of the file monitor. 
 * It investigates the file in question and notify listeners if changed.
 */
public class URLScanner extends AbstractScanner<IURLListener> {
	private HashMap<URL, Boolean> urls;
	
	/**
	 * Constructor
	 */
	public URLScanner() {
		super(IURLListener.class);
		this.urls = new HashMap<URL, Boolean>();
	}

	/**
	 * Adds specified url for listening.
	 * @param url url to add.
	 * @throws MalformedURLException 
	 */
	public void addURL(String urlName) throws MalformedURLException {
		addURL(new URL(urlName));
	}
	
	/**
	 * Adds specified url for listening.
	 * @param url url to add.
	 */
	public void addURL(URL url) {
		if (!urls.containsKey(url)) {
			boolean available = false; //isURLAvailable(url);
			urls.put(url, Boolean.valueOf(available));
		}
	}
	
	/**
	 * Removes specified url for listening.
	 * @param url url to remove.
	 */
	public void removeURL(URL url) {
		urls.remove(url);
	}

	/**
	 * Loops over the registered files and see which have changed.
	 * Note:
	 * This method uses a copy of the listener list in case listener wants to alter the
	 * list within its event method.
	 */
	public void run() {
		Collection<URL> urlCollection = new ArrayList<URL>(urls.keySet());
		Iterator<URL> i = urlCollection.iterator();
		while (i.hasNext()) {
			URL url = i.next();

			boolean lastAvailability = ((Boolean) urls.get(url)).booleanValue();
			boolean newAvailability = isURLAvailable(url);

			// Chek if availability has changed
			if (newAvailability != lastAvailability) {
				// Register new availability
				urls.put(url, Boolean.valueOf(newAvailability));
				
				// Notify listeners
				notifyListeners(url, newAvailability);
			}
		}
	}
	
	/**
	 * Checks if <code>url</code> is available
	 * @return boolean
	 */
	private boolean isURLAvailable(URL url) {
		try {
			HttpURLConnection.setFollowRedirects(true);
			URLConnection urlCon = url.openConnection();
			if (urlCon instanceof HttpURLConnection) {
				HttpURLConnection con = (HttpURLConnection) urlCon;
				con.setInstanceFollowRedirects(true);
				con.setRequestMethod("HEAD");
				con.connect();
				return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
			} else {
				urlCon.connect();
				return true;
			}
		}
		catch (Exception exc) {
			return false;
		}
	}

	/**
	 * Notifies all registered listeners
	 * @param url the url that availability was changed
	 * @param availability the type of availability
	 */
	private void notifyListeners(URL url, boolean availability) {
		IURLListener[] listeners = getListeners();
        for (int i = 0; i < listeners.length; ++ i) {
			IURLListener listener = listeners[i];
			if (availability) {
				listener.urlAvailable(url);
			}
			else {
				listener.urlUnavailable(url);
			}
		}
	}
}
