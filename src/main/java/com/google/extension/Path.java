package com.google.extension;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

public class Path {
	private List<String> tokens;

	public Path() {
		tokens = new ArrayList<String>();
	}

	public Path down(String path) {
		tokens.add(path);
		
		return this;
	}

	public Path down(String... paths) {
		if (paths != null && paths.length > 0) {
			for (int i = 0; i < paths.length; i++) {
				down(paths[i]);
			}
		}
		
		return this;
	}

	public Path up() {
		tokens.remove(tokens.size() - 1);
		return this;
	}

	@Override
	public String toString() {
		return Joiner.on(System.getProperty("file.separator")).join(tokens);
	}
}