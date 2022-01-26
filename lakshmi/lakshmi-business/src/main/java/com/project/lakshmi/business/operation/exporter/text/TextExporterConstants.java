package com.project.lakshmi.business.operation.exporter.text;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TextExporterConstants {
	
	public static String FILE_NAME = "WarningFile.txt";
	
	public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());;
	
}
