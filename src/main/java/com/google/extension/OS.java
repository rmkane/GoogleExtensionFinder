package com.google.extension;

public class OS {
	private String name;
	private Version version;

	public OS(String name, int major, int minor) {
		this.name = name;
		this.version = new Version(major, minor);
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected Version getVersion() {
		return version;
	}

	protected void setVersion(Version version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)", name, version);
	}
}