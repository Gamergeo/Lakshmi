package com.project.lakshmi.model;

public class DatabaseName {
	
	public static final String ID = "id";
	
	public class CHECKLIST_STEP {
		
		public static final String TABLE = "checklist_step";
		
		public static final String ORDER = "order";
		
		public static final String LABEL = "label";
		
		public static final String ADVICE = "advice";
		
		public static final String DONE = "done";
	}
	
	
	public class ASSET {
		public static final String TABLE = "asset";
		
		public static final String ISIN = "isin";
		
		public static final String LABEL = "label";
		
		public static final String LINK = "link";
	}	
	
	public class API_IDENTIFIER {
		public static final String TABLE = "api_identifier";
		
		public static final String API = "api";
		
		public static final String MARKET = "market";
		
		public static final String SYMBOL = "symbol";
		
		public static final String ID_ASSET_CURRENCY = "id_asset_currency";
	}
	
	public class PRICE {
	
		public static final String TABLE = "price";
		
		public static final String ID_ASSET = "id_asset";
		
		public static final String DATE = "date";
		
		public static final String PRICE = "price";
	}
	
	public class OHLC {
		public static final String TABLE = "ohlc";
		
		public static final String ID_ASSET = "id_asset";
		
		public static final String DATE = "date";
		
		public static final String OPEN = "open";
		
		public static final String HIGH = "high";
		
		public static final String LOW = "low";
		
		public static final String CLOSE = "close";
	}
}
