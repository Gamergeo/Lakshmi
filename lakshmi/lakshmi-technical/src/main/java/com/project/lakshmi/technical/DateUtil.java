package com.project.lakshmi.technical;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
	
	/**
	 * Ecrit un message sur un fichier
	 */
	public static Date formatDate(String date, String format) {
		try {
		    SimpleDateFormat formatter = new SimpleDateFormat(format);  
		    return formatter.parse(date); 
		} catch (ParseException exception) {
			throw new ApplicationException("Impossible de formatter la date : " + date 
					+ " avec le formattage suivant :" + format); 
		} 
	}
	
	/** 
	 * @return the coreected date (floor minute)
	 */
	public static Date truncateMinute(Date date) {
		
		// Les ohlc sont stockés par minute
		return DateUtils.truncate(date, Calendar.MINUTE);
	}
	
	/** 
	 * @return the coreected date (floor day)
	 */
	public static Date truncateDay(Date date) {
		
		// Les ohlc sont stockés par minute
		return DateUtils.truncate(date, Calendar.DATE);
	}
	
	/** 
	 * @return the coreected date (ceiling day)
	 */
	public static Date ceilingDay(Date date) {
		
		// Les ohlc sont stockés par minute
		return DateUtils.ceiling(date, Calendar.DATE);
	}
}
