package com.project.lakshmi.technical;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
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
}
