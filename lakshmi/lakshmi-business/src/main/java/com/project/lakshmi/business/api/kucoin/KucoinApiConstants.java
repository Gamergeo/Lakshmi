package com.project.lakshmi.business.api.kucoin;

public class KucoinApiConstants {
	
	final public static String BASE_URI = "https://api.kucoin.com/";
	
	final public static String URI_OHLC = "api/v1/market/candles";
	
	final public static String API_KEY = "61f29339a2de0c0001be4a7d";
	
	final public static String API_SECRET = "f6a4151e-3c63-4639-b121-97ed8d42a409";
	
	final public static String API_KEY_VERSION = "2";
	
	final public static String HEADER_API_KEY = "KC-API-KEY"; // The API key as a string.
	
	final public static String HEADER_API_SIGN = "KC-API-SIGN"; // The base64-encoded signature (see Signing a Message).
	
	final public static String HEADER_API_TIMESTAMP = "KC-API-TIMESTAMP"; // A timestamp for your request.
	
	final public static String HEADER_API_PASSPHRASE = "KC-API-PASSPHRASE"; // The passphrase you specified when creating the API key.
	
	final public static String HEADER_API_KEY_VERSION = "KC-API-KEY-VERSION"; // You can check the version of API key on the page of API Management
	
	final public static String PARAMETER_SYMBOL = "symbol";
	
	final public static String PARAMETER_PERIOD = "type";
	
	final public static String PARAMETER_START = "startAt";
	
	final public static String PARAMETER_END = "endAt";
	
	final public static String PARAMETER_PERIOD_DAY = "1day";
	
	final public static String PARAMETER_PERIOD_MINUTE = "1min";
	
	final public static String RESULT_DATA = "data";
	
	final public static Integer RESULT_INDEX_DATE= 0;
	
	final public static Integer RESULT_INDEX_OPEN = 1;
	
	final public static Integer RESULT_INDEX_HIGH = 3;
	
	final public static Integer RESULT_INDEX_LOW = 4;
	
	final public static Integer RESULT_INDEX_CLOSE = 2;
	
	
	
	
	
	
	
//	// -- OHLC
//	final public static String BASE_URI = "https://api.cryptowat.ch/markets/";
//	
//	final public static String PARAMETER_PERIOD = "period";
//	
//	final public static String PARAMETER_AFTER = "after";
//	
//	final public static String PARAMETER_BEFORE = "before";
//	
//	final public static List<String> PARAMETER_PERIOD_POSSIBLE_VALUES = Arrays.asList(new String[] {
//			"60", "180", "300", "900", "1800", "3600", "7200", "14400", "21600", "43200", "86400", "259200", "604800"
//	});
//	
//	final public static Double OHLC_LIMIT = 6000d;
//	
//	final public static String PARAMETER_PERIOD_15MINUTE = "900";
//	
//	final public static String PARAMETER_PERIOD_DAY = "86400";
//	
//	// -- Market identifier
//	final public static String MARKET_URI = "https://api.cryptowat.ch/markets";
//	
//	final public static String RESULT = "result";
//	
//	final public static String RESULT_ACTIVE = "active";
//	
//	final public static String RESULT_PAIR = "pair";
//	
//	final public static String RESULT_EXCHANGE = "exchange";
}

