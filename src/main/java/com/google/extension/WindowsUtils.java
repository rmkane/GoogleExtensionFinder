package com.google.extension;

import java.io.*;

public class WindowsUtils {
	private static final String[] EDITIONS = {
		"Basic", "Home", "Professional", "Enterprise"
	};
	
	public static void main(String[] args) {
		System.out.printf("The edition of Windows you are using is: %s%n",
			getWindowsEdition()
		);
	}

	public static String findSysInfo(String term) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("CMD /C SYSTEMINFO | FINDSTR /B /C:\"" + term + "\"");
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			return in.readLine();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		return "";
	}

	public static String getWindowsEdition() {
		String osName = findSysInfo("OS Name:");
		
		if (!osName.isEmpty()) {
			for (String edition : EDITIONS) {
				if (osName.contains(edition)) {
					return edition;
				}
			}
		}

		return null;
	}
}
