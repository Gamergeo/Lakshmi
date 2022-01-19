package com.project.lakshmi.model;

public class DatabaseName {
	
	public static final String ID = "id";
	
	public class CHECKLIST {
		
		public static final String TABLE = "checklist";

		public static final String CREATION_DATE = "creation_date";
		
		public static final String END_DATE = "end_date";
	}	
	
	public class CHECKLIST_STEP {
		
		public static final String TABLE = "checklist_step";
		
		public static final String ID_CHECKLIST = "id_checklist";

		public static final String ID_CHECKLIST_STEP_INFOS = "id_checklist_step_infos";
		
		public static final String STARTED = "started";
		
		public static final String ENDED = "ended";
	}	
	
	public class CHECKLIST_STEP_INFOS {
		
		public static final String TABLE = "checklist_step_infos";
		
		public static final String ORDER = "order";
		
		public static final String LABEL = "label";
	}
	
	public class ASSET {
		public static final String TABLE = "asset";
	}	
	
	public class API_IDENTIFIER {
		public static final String TABLE = "api_identifier";
		
		public static final String API = "api";
		
		public static final String MARKET = "market";
		
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
