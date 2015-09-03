package com.google.extension;

import java.util.LinkedHashMap;
import java.util.Map;

public class OSUtils {
	private static final Map<String, OS> WIN_VERSIONS;

	static {
		WIN_VERSIONS = new LinkedHashMap<String, OS>() {
			private static final long serialVersionUID = -2734555809870323246L;
			{
				put("5.0", new OS("Windows 2000", 5, 0));
				put("5.1", new OS("Windows XP", 5, 1));
				put("5.2", new OS("Windows XP", 5, 2));
				put("6.0", new OS("Windows Vista", 6, 0));
				put("6.1", new OS("Windows 7", 6, 1));
				put("6.2", new OS("Windows 8", 6, 2));
				put("6.3", new OS("Windows 8.1", 6, 3));
				put("10.0", new OS("Windows 10", 10, 0));
			}
		};
	}
	
	public static OS getOS() {
		if (System.getProperty("sun.desktop").equals("windows")) {
			return getWindowsOS();
		}
		
		return null;
	}
	
	public static OS getWindowsOS() {
		return WIN_VERSIONS.get(System.getProperty("os.version"));
	}
	
	public static final Path getLocalData(OS os) {
		Path path = new Path();

		if (os.getVersion().getMajor() < 6) {
			return path.down(System.getenv("USERPROFILE"), "Local Settings",
					"Application Data");
		} else {
			return path.down(System.getenv("LOCALAPPDATA"));
		}
	}

	public static final Path getLocalData(OS os, String... paths) {
		return getLocalData(os).down(paths);
	}
}