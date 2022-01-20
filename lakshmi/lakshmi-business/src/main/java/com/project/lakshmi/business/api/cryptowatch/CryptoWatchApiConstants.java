package com.project.lakshmi.business.api.cryptowatch;

import java.util.Arrays;
import java.util.List;

public class CryptoWatchApiConstants {
	
	final public static String BASE_URI = "https://api.cryptowat.ch/markets/";
	
	final public static String PARAMETER_PERIOD = "period";
	
	final public static String PARAMETER_AFTER = "after";
	
	final public static String PARAMETER_BEFORE = "before";
	
	final public static List<String> PARAMETER_PERIOD_POSSIBLE_VALUES = Arrays.asList(new String[] {
			"60", "180", "300", "900", "1800", "3600", "7200", "14400", "21600", "43200", "86400", "259200", "604800"
	});
	
	final public static Double OHLC_LIMIT = 6000d;
	
	final public static String PARAMETER_PERIOD_15MINUTE = "900";
	
	final public static String PARAMETER_PERIOD_DAY = "86400";
	
}

