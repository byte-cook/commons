package de.kobich.commons.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.monitor.scanner.FileScanner;
import de.kobich.commons.monitor.scanner.IFileListener;
import de.kobich.commons.monitor.scanner.IURLListener;
import de.kobich.commons.monitor.scanner.Monitor;
import de.kobich.commons.monitor.scanner.URLScanner;

/**
 * This is a test class for the monitor package.
 */
class MonitorTest {
	private static Logger logger = Logger.getLogger(MonitorTest.class);
	
	@BeforeEach
	public void init() {
		BasicConfigurator.configure();
	}
	
	/**
	 * Tests url scanner
	 */
	@Test
	public void urlScannerTest() throws MalformedURLException {
		String urlName = "http://google.de";
		URL url = new URL(urlName);
		
		Monitor<IURLListener> monitor = new Monitor<IURLListener>();
		URLScanner scanner = new URLScanner();
		scanner.addURL(url);
		scanner.addListener(new MyURLListener(monitor));

		monitor.setScanner(scanner);

		logger.info("Starting Monitor...");
		monitor.start();
		try {
			while (monitor.isRunning()) {
				Thread.sleep(100);
			} 
		}
		catch (Exception e) {
			e.printStackTrace();
			monitor.stop();
		}
		logger.info("Monitor stopped");		
	}
	
	/**
	 * My url listener class
	 */
	private static class MyURLListener implements IURLListener {
		private Monitor<IURLListener> m;
		public MyURLListener(Monitor<IURLListener> m) {
			this.m = m;
		}
		public void urlAvailable(URL url) {
			logger.info("Url " + url.toExternalForm() + " is available");
			m.stop();
		}

		public void urlUnavailable(URL url) {
			logger.info("Url " + url.toExternalForm() + " is not available");
		}
	}
	
	/**
	 * Tests file scanner
	 */
	@Test
	public static void fileScannerTest() {
		File folder = new File("C://timertask");
		File file = new File("C://timertask//Neu Textdokument.txt");

		Monitor<IFileListener> monitor = new Monitor<IFileListener>();

		for (int i = 0; i < 2; ++ i) {
			FileScanner scanner = new FileScanner();
			scanner.addFile(folder);
			scanner.addListener(new MyFileListener());
			monitor.setScanner(scanner);

			logger.info("Monitor started...");
			monitor.start();
			try {
				Thread.sleep(5000);
				logger.info("create new file");
				file.createNewFile();
				Thread.sleep(5000);
				logger.info("delete file");
				file.delete();
				Thread.sleep(5000);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			monitor.stop();
		}
		logger.info("Monitor stopped");		
	}
	
	/**
	 * My file listener class
	 */
	private static class MyFileListener implements IFileListener {
		public void fileCreated(File file) {
			logger.info("File " + file.getAbsolutePath() + " created.");
		}

		public void fileDeleted(File file) {
			logger.info("File " + file.getAbsolutePath() + " deleted.");
		}

		public void fileModified(File file) {
			logger.info("File " + file.getAbsolutePath() + " modified.");
		}
	}
}
