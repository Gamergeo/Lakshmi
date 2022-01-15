package com.project.lakshmi.business.operation.importer.binance;

import java.util.Arrays;
import java.util.List;

public class OperationImporterBinanceConstants {
	
	public final static String HEADER = "User_ID,UTC_Time,Account,Operation,Coin,Change,Remark";
	public final static String SEPARATOR = ",";

	public final static int DATE_INDEX = 1;
	public final static int INVESTMENT_TYPE_INDEX = 3;
	public final static int ASSET_INDEX = 4;
	public final static int QUANTITY_INDEX = 5;
	
	public final static String INVESTMENT_TYPE_BUY = "Buy";
	
	public final static String INVESTMENT_TYPE_SELL = "Sell";
	
	public final static String INVESTMENT_TYPE_FEE = "Fee";
	
	public final static List<String> INVESTMENT_TYPE_TRADE = Arrays.asList(new String[]{
			INVESTMENT_TYPE_BUY, INVESTMENT_TYPE_SELL, INVESTMENT_TYPE_FEE});
	
	public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
