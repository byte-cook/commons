package de.kobich.commons.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Optional;

public class SystemUtils {
	public static final String WINDOWS = "win";
	public static final String LINUX = "linux";
	public static final String UNIX = "unix";
	public static final String SOLARIS = "solaris";
	public static final String MAC = "mac";
	
	public static String getOperationSystem() {
		String os = "";
		if (SystemUtils.isWindowsSystem()) {
			os = WINDOWS;
		}
		else if (SystemUtils.isLinuxSystem()) {
			os = LINUX;
		}
		else if (SystemUtils.isUnixSystem()) {
			os = UNIX;
		}
		else if (SystemUtils.isSolarisSystem()) {
			os = SOLARIS;
		}
		else if (SystemUtils.isMacSystem()) {
			os = MAC;
		}
		return os;
	}
	
	public static boolean isWindowsSystem() {
		String osName = System.getProperty("os.name").toLowerCase();
		return osName.contains("windows");
	}

	public static boolean isLinuxSystem() {
		String osName = System.getProperty("os.name").toLowerCase();
		return osName.contains("linux");
	}
	
	public static boolean isMacSystem() {
		String osName = System.getProperty("os.name").toLowerCase();
		return osName.contains("mac");
	}
	
	public static boolean isUnixSystem() {
		String osName = System.getProperty("os.name").toLowerCase();
		return osName.contains("nix");
	}
	
	public static boolean isSolarisSystem() {
		String osName = System.getProperty("os.name").toLowerCase();
		return osName.contains("sunos");
	}
	
	/**
	 * Returns the mac address of the current system
	 * @return
	 * @throws SystemInformationException
	 */
	public static Optional<String> getMacAddress() throws IOException {
		if (isWindowsSystem()) {
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface ni = NetworkInterface.getByInetAddress(address);
			byte[] mac = ni.getHardwareAddress();
			if (mac != null) {
				return Optional.of(convertByteArrayToMacAddress(mac));
			}
		}
		else {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				byte[] mac = networkInterface.getHardwareAddress();
				if (mac != null) {
					return Optional.of(convertByteArrayToMacAddress(mac));
				}
			}
		}
		return Optional.empty();
	}
	
	private static String convertByteArrayToMacAddress(byte[] mac) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format((i == 0 ? "" : "-") + "%02X", mac[i]));
		}
		return sb.toString();
	}
} 
