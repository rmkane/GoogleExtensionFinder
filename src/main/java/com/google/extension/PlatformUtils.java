package com.google.extension;

import java.util.regex.Pattern;

// http://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
public final class PlatformUtils {
	private static final Platform PLATFORM = Platform.getPlatform();
	
	public static final boolean IS_WINDOWS = isWindows();
	public static final boolean IS_MAC = isMac();
	public static final boolean IS_UNIX = isUnix();
	public static final boolean IS_SOLARIS = isSolaris();
	
	private enum Platform {
		WINDOWS("Windows", "win"),
		MACHINTOSH("Mac", "mac"),
		UNIX("Unix", "nix|nux|aix"),
		SOLARIS("Solaris", "sunos"),
		UNKNOWN("Undefined", "");
		
		private String name;
		private String pattern;
		
		public String getName() {
			return name;
		}
		
		public String getPattern() {
			return pattern;
		}
		
		private Platform(String name, String pattern) {
			this.name = name;
			this.pattern = pattern;
		}
		
		private String getMessage() {
			return getMessage(this);
		}
		
		private static String getMessage(Platform platform) {
			if (platform != Platform.UNKNOWN) {
				return String.format("This is %s", platform.getName());
			}
			
			return "Your OS is not supported!";
		}
		
		public static Platform getPlatform() {
			String osName = System.getProperty("os.name").toLowerCase();

			for (Platform platform : Platform.values()) {
				if (containsPattern(osName, platform.getPattern())) {
					return platform;
				}
			}
			
			return Platform.UNKNOWN;
		}
		
		private static final boolean isWindows(Platform platform) {
			return platform == Platform.WINDOWS;
		}
		
		private final boolean isWindows() {
			return isWindows(this);
		}

		private static final boolean isMac(Platform platform) {
			return platform == Platform.MACHINTOSH;
		}
		
		private final boolean isMac() {
			return isMac(this);
		}

		private static final boolean isUnix(Platform platform) {
			return platform == Platform.UNIX;
		}
		
		private final boolean isUnix() {
			return isUnix(this);
		}

		private static final boolean isSolaris(Platform platform) {
			return platform == Platform.SOLARIS;
		}
		
		private final boolean isSolaris() {
			return isSolaris(this);
		}
	}
	
	public static boolean isWindows() {
		return PLATFORM.isWindows();
	}

	public static boolean isMac() {
		return PLATFORM.isMac();
	}

	public static boolean isUnix() {
		return PLATFORM.isUnix();
	}

	public static boolean isSolaris() {
		return PLATFORM.isSolaris();
	}
	
	private static final boolean containsPattern(String str, String pattern) {
		if (str.contains("|")) {
			return Pattern.compile(pattern).matcher(str).find();
		} else {
			return str.contains(pattern);
		}
	}
	
	private static class TestUtils {
		public static String getMessage1() {
			switch (PLATFORM) {
				case WINDOWS:
					return "This is Windows";
				case MACHINTOSH:
					return "This is Mac";
				case UNIX:
					return "This is Unix (including Linux)";
				case SOLARIS:
					return "This is Solaris";
				default:
					return "Your OS is not supported!";
			}
		}
		
		public static String getMessage2() {
			if (PlatformUtils.IS_WINDOWS)
				return "This is Windows";
			else if (PlatformUtils.IS_MAC)
				return "This is Mac";
			else if (PlatformUtils.IS_UNIX)
				return "This is Unix (including Linux)";
			else if (PlatformUtils.IS_SOLARIS)
				return "This is Solaris";
			else
				return "Your OS is not supported!";
		}
		
		public static String getMessage3() {
			return PLATFORM.getMessage();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(TestUtils.getMessage1());
		System.out.println(TestUtils.getMessage2());
		System.out.println(TestUtils.getMessage3());
	}
}
