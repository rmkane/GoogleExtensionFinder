package com.google.extension;

public class Version {
	private int major;
	private int minor;

	public Version(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}

	protected int getMajor() {
		return major;
	}

	protected void setMajor(int major) {
		this.major = major;
	}

	protected int getMinor() {
		return minor;
	}

	protected void setMinor(int minor) {
		this.minor = minor;
	}
	
	@Override
	public String toString() {
		return String.format("%d.%d", major, minor);
	}
}
