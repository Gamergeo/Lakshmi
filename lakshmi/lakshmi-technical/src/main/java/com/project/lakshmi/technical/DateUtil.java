package com.project.lakshmi.technical;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
	
	/**
	 * Ecrit un message sur un fichier
	 */
	public static Instant formatDate(String date, DateTimeFormatter format, ZoneId zone) {
		return LocalDateTime.parse(date, format).atZone(zone).toInstant();
	}
	
	/** 
	 * @return the coreected date (floor minute)
	 */
	public static Instant truncateMinute(Instant instant) {
		return instant.truncatedTo(ChronoUnit.MINUTES);
	}
	
	/** 
	 * @return the coreected date (floor minute)
	 */
	public static Instant ceilMinute(Instant instant) {
		return instant.truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES);
	}
	
	/** 
	 * @return the coreected date (floor minute)
	 */
	public static Instant truncateDay(Instant instant) {
		return instant.truncatedTo(ChronoUnit.DAYS);
	}
	
	/**
	 * Ecrit un message sur un fichier
	 */
	@Deprecated
	public static Date formatDate(String date, String format) {
		try {
		    SimpleDateFormat formatter = new SimpleDateFormat(format);
		    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		    return formatter.parse(date);
		} catch (ParseException exception) {
			throw new ApplicationException("Impossible de formatter la date : " + date 
					+ " avec le formattage suivant :" + format); 
		} 
	}
	
	/** 
	 * @return the coreected date (floor minute)
	 */
	@Deprecated
	public static Date truncateMinute(Date date) {
		
		// Les ohlc sont stockés par minute
		return DateUtils.truncate(date, Calendar.MINUTE);
	}
	
	/** 
	 * @return the coreected date (ceiling day)
	 */
	@Deprecated
	public static Date ceilingMinute(Date date) {
		
		// Les ohlc sont stockés par minute
		return DateUtils.ceiling(date, Calendar.MINUTE);
	}
	
	/** 
	 * @return the coreected date (floor day)
	 */
	@Deprecated
	public static Date truncateDay(Date date) {
		
		// Les ohlc sont stockés par minute
		return DateUtils.truncate(date, Calendar.DATE);
	}
	
	/** 
	 * @return the coreected date (ceiling day)
	 */
	@Deprecated
	public static Date ceilingDay(Date date) {
		
		// Les ohlc sont stockés par minute
		return DateUtils.ceiling(date, Calendar.DATE);
	}
}
