package com.project.lakshmi.business.operation.importer.kucoin;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class OperationImporterKucoinConstants {
	public final static String SEPARATOR = ",";
	
	public final static ZoneId DATE_ZONE = ZoneId.of("Asia/Hong_Kong");
	
	public static class TRADE_FILE {
		public final static String HEADER = "orderCreatedAt,id,clientOid,symbol,side,type,stopPrice,price,size,dealSize,dealFunds,averagePrice,fee,feeCurrency,remark,tags,orderStatus,";
		public final static int DATE_INDEX = 0;
		public final static int PAIR_INDEX = 3;
		public final static String PAIR_SEPARATOR = "-";
		public final static int PRICE_INDEX = 11;
		public final static int TYPE_INDEX = 4;
		public final static String TYPE_BUY = "buy";
		
		public final static int MAIN_QUANTITY_INDEX = 9;
		public final static int BALANCING_QUANTITY_INDEX = 10;
		public final static int FEE_QUANTITY = 12;
		public final static int FEE_ASSET = 13;
		
		/*public final static String HEADER = "tradeCreatedAt,orderId,symbol,side,price,size,funds,fee,liquidity,feeCurrency,orderType,";
		
		public final static int DATE_INDEX = 0;
		public final static int PAIR_INDEX = 2;
		public final static String PAIR_SEPARATOR = "-";
		public final static int PRICE_INDEX = 4;
		public final static int TYPE_INDEX = 3;
		public final static String TYPE_BUY = "buy";
		public final static int MAIN_QUANTITY_INDEX = 5;
		public final static int BALANCING_QUANTITY_INDEX = 6;
		public final static int FEE_QUANTITY = 7;
		public final static int FEE_ASSET = 9;*/
		
		public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	}
	
	public static class FEE_FILE {
		public final static String HEADER = "Coin,Side,Type,Amount(Fee included),Fee,Time,Remark";
		public final static int DATE_INDEX = 5;
		public final static int QUANTITY_INDEX = 3;
		public final static int TYPE_INDEX = 2;
		
		public final static String TYPE_FEE = "KCS Pay Fees";
		
		public final static List<String> SPECIAL_CHARACTERS = Arrays.asList(new String[] { "ï", "»", "¿" });
		
		public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	}
	
}
