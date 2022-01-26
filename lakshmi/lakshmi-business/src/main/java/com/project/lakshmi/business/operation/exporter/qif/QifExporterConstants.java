package com.project.lakshmi.business.operation.exporter.qif;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class QifExporterConstants {
	
	public final static String HEADER = "!Type:Invst";
	
	public static String FILE_NAME = "OperationExport.qif";
	
	public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());;
	
	public final static String PREFIX_DATE = "D";
	
	public final static String PREFIX_QUANTITY = "Q";
	
	public final static String PREFIX_SECURITY = "Y"; // Asset
	
	public final static String PREFIX_ACTION = "N"; // Investment type
	
	public final static String PREFIX_TOTAL = "T"; // Prix total
	
	public final static String PREFIX_FEE = "O"; // Cout des fees
	
	public final static String PREFIX_PRICE = "I"; // Prix
	
	public final static String PREFIX_MEMO = "M"; // Commentaire
	
	public final static String TYPE_BUY = "BUY";
	
	public final static String TYPE_SELL = "SELL";
	
	public final static String TYPE_ADD = "SHRSIN";
	
	public final static String END_OPERATION = "^";
}
