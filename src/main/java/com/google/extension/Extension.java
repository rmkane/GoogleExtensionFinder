package com.google.extension;

public class Extension implements Comparable<Extension> {
	private String name;
	private String version;
	private String path;

	public Extension() {
		this("", "", "");
	}
	
	public Extension(String name, String version, String path) {
		this.name = name;
		this.version = version;
		this.path = path;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected String getVersion() {
		return version;
	}

	protected void setVersion(String version) {
		this.version = version;
	}

	protected String getPath() {
		return path;
	}

	protected void setPath(String path) {
		this.path = path;
	}

	public int compareTo(Extension other) {
		return other == null ? -1 : this.getName().compareToIgnoreCase(other.getName());
	}

	@Override
	public String toString() {
		return String.format("Extension { name: %s, version: %s, path: %s }",
				name, version, path);
	}
}
