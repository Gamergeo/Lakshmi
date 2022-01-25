package com.project.lakshmi.technical;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
	
	/**
	 * Ecrit un message sur un fichier
	 */
	public static Instant extractDate(String date, DateTimeFormatter format, ZoneId zone) {
		return LocalDateTime.parse(date, format).atZone(zone).toInstant();
	}
	
	public static String formatDate(Instant date, DateTimeFormatter format) {
		return format.format(date);
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
}
