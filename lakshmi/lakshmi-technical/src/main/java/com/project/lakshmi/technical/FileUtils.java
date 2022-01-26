package com.project.lakshmi.technical;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
	
	/**
	 * Ecrit un message sur un fichier
	 */
	public static void removeFile(String fileName) {
		getFile(fileName).delete();
	}
	
	public static void writeOnFileAndEndLine(String fileName, String message) {
		writeOnFile(fileName, message + "\n");
	}
	
	public static void endLine(String fileName) {
		writeOnFileAndEndLine(fileName, "");
	}
	
	public static String getUri(String fileName) {
		return TechnicalConstants.BASE_DIRECTORY + fileName;
	}
	
	/**
	 * Ecrit un message sur un fichier
	 */
	public static void writeOnFile(String fileName, String message) {
		writeOnFile(getFile(fileName), message);
	}
	
	private static File getFile(String name) {
	    try {
	    	
	      File file = new File(TechnicalConstants.BASE_DIRECTORY + name);
	      
	      if (file.createNewFile()) {
	    	  return file;
	        
	      } else {
	    	  return file;
	      }
	    } catch (IOException e) {
	    	throw new NullPointerException("File problem" + e);
	    }
	}
	
	/**
	 * Ecrit un message sur le fichier 
	 */
	private static void writeOnFile(File file, String message) {
		
	    try {
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write(message);
			fileWriter.close();
			
	    } catch (IOException e) {
	    	throw new NullPointerException("File problem");
	    }
	}
}
