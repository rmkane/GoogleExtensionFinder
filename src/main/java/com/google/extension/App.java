package com.google.extension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App {
	private static final String MANIFEST_NAME = "manifest.json";
	private static final String CHROME_EXTENSIONS_DIR;

	static {
		CHROME_EXTENSIONS_DIR = googleUserDataDirectory();
	}

	public static final String googleUserDataDirectory() {
		OS os = OSUtils.getOS();

		if (os != null) {
			return OSUtils.getLocalData(os, "Google", "Chrome", "User Data",
					"Default", "Extensions").toString();
		}

		return null;
	}

	public static void main(String[] args) {
		List<Extension> extensions = findExtensions(CHROME_EXTENSIONS_DIR);

		System.out.println(listToJson(extensions));
	}

	public static <T extends Comparable<T>> String listToJson(List<T> list) {
		Collections.sort(list);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return gson.toJson(list);
	}

	public static List<Extension> findExtensions(String path) {
		List<Extension> extensions = new ArrayList<Extension>();

		for (File extensionDir : getFiles(new File(path))) {
			if (extensionDir.isDirectory()) {
				Extension extension = new Extension();

				extension.setPath(extensionDir.getAbsolutePath());

				parseExtensions(extensions, extensionDir, extension);

				if (!extension.getName().isEmpty()) {
					extensions.add(extension);
				}
			}
		}

		return extensions;
	}

	private static void parseExtensions(List<Extension> extensions,
			File extensionDir, Extension extension) {
		for (File version : getFiles(extensionDir)) {
			if (version.isDirectory()) {
				parseVersion(extensions, extensionDir, version, extension);
			}
		}
	}

	private static void parseVersion(List<Extension> extensions,
			File extensionDir, File version, Extension extension) {
		for (File file : getFiles(version)) {
			if (file.isFile() && file.getName().equals(MANIFEST_NAME)) {
				parseManifest(extensions, extensionDir, file, extension);
			}
		}
	}

	private static void parseManifest(List<Extension> extensions,
			File extensionDir, File manifest, Extension extension) {
		JsonObject json = fileToJSON(manifest.getAbsolutePath());

		extension.setName(parseName(json));
		extension.setVersion(json.get("version").getAsString());
	}

	private static String parseName(JsonObject manifest) {
		String name = manifest.get("name").getAsString();

		if (name.indexOf("__MSG_") > -1) {
			JsonElement container = manifest.get("container");
			if (container != null) {
				return container.getAsString();
			}

			JsonElement url = getNestedJson(manifest, "app.launch.web_url");
			if (url != null) {
				return url.getAsString();
			}
		}

		return name;
	}

	public static final JsonElement getNestedJson(JsonObject json, String key) {
		String currentKey = key;
		String rest = "";
		int more = key.indexOf('.');

		if (more > 0 && more < key.length() - 1) {
			currentKey = key.substring(0, more);
			rest = key.substring(more + 1);
		}

		JsonElement el = json.get(currentKey);

		if (rest == null || rest.length() < 1) {
			return el;
		}

		if (el != null) {
			return getNestedJson(el.getAsJsonObject(), rest);
		}

		return null;
	}

	public static JsonObject fileToJSON(String fileName) {
		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(fileName));

			return jsonElement.getAsJsonObject();
		} catch (FileNotFoundException e) {
			System.err.println("File is not a JSON file: " + fileName);
		}

		return null;
	}

	public static File[] getFiles(File directory)
			throws IllegalArgumentException {
		if (directory.isDirectory()) {
			return directory.listFiles();
		}

		throw new IllegalArgumentException("Not a directory: "
				+ directory.getAbsolutePath());
	}
}
