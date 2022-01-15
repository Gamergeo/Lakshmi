package com.project.lakshmi.model.file;

import java.util.ArrayList;
import java.util.List;

/**
 * Attention, la première ligne est index 0
 */
public class RawTextFile {

	private List<String> lines = new ArrayList<String>();

	public void addLine(String line) {
		this.lines.add(line);
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	
	public String getLine(int index) {
		if (index > getNumberLine()) {
			return null;
		}
		
		return lines.get(index - 1);
	}
	
	public void removeLine(int index) {
		if (index > getNumberLine()) {
			return;
		}
		
		lines.remove(index - 1);
	}
	
	public void removeLines(int... index) {
		
		for (int i = 0; i < index.length; i++) {
			removeLine(i);
		}
	}
	
	public int getNumberLine() {
		return lines.size();
	}
	
	public String getAndRemoveLine(int index) {
		String line = getLine(index);
		removeLine(index);
		return line;
	}
	
	public boolean hasNext() {
		if (getNumberLine() >= 1) {
			return true;
		}
		
		return false;
	}
	
	public String getNext() {
		return getLine(1);
	}
	
	public String getAndRemoveNext() {
		return getAndRemoveLine(1);
	}
	
	public void removeNext() {
		removeLine(1);
	}
}
