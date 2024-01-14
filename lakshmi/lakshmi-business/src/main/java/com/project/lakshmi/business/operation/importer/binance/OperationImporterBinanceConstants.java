package com.project.lakshmi.business.operation.importer.binance;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class OperationImporterBinanceConstants {
	
	public final static String HEADER = "User_ID,UTC_Time,Account,Operation,Coin,Change,Remark";
	public final static String SEPARATOR = ",";

	public final static int DATE_INDEX = 1;
	public final static int INVESTMENT_TYPE_INDEX = 3;
	public final static int ASSET_INDEX = 4;
	public final static int QUANTITY_INDEX = 5;
	
	public final static String ASSET_BNB = "BNB";
	
	public final static String INVESTMENT_TYPE_BUY = "Transaction Revenue";
	
	public final static String INVESTMENT_TYPE_SELL = "Transaction Sold";
	
	public final static String INVESTMENT_TYPE_FEE = "Transaction Fee";
	
	public final static String INVESTMENT_TYPE_MULTI_TRADE = "Small Assets Exchange BNB";
	
	public final static List<String> INVESTMENT_TYPE_TRADE = Arrays.asList(new String[]{
			INVESTMENT_TYPE_BUY, INVESTMENT_TYPE_SELL, INVESTMENT_TYPE_FEE});
	
	public final static List<String> INVESTMENT_TYPE_STACKING = Arrays.asList(new String[]{
			"Pool Distribution", "Savings Interest", "ETH 2.0 Staking Rewards", 
			"POS savings interest", "Super BNB Mining", "Launchpool Interest",
			"Commission Rebate", "Staking Rewards", "Simple Earn Flexible Interest",
			"Simple Earn Locked Rewards", "BNB Vault Rewards"});
	
	public final static String INVESTMENT_TYPE_WITHDRAW = "Withdraw";
	
	public final static String INVESTMENT_TYPE_DEPOSIT = "Deposit";
	
	public final static List<String> INVESTMENT_TYPE_IGNORED = Arrays.asList(new String[]{
			"Dual savings settlement", "Dual savings purchase", "Savings purchase", 
			"Savings Principal redemption", "POS savings purchase", "POS savings redemption",
			"Simple Earn Flexible Subscription", "Simple Earn Flexible Redemption", "Staking Redemption",
			"Staking Purchase", "Simple Earn Locked Redemption", "Main and funding account transfer",
			"Simple Earn Locked Subscription", "Fiat Deposit", "Transaction Revenue", "Transaction Sold"});
	
	public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public final static ZoneId DATE_ZONE = ZoneId.of("UTC");
}
