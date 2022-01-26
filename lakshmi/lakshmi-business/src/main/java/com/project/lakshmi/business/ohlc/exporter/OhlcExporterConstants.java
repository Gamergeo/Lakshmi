package com.project.lakshmi.business.ohlc.exporter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class OhlcExporterConstants {
	
	public static String FILE_NAME = "priceTracker.csv";
	
	public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.of("UTC"));
	
}
