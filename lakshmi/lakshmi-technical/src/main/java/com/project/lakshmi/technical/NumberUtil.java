package com.project.lakshmi.technical;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtil {
	
	/**
	 * Ecrit un double correctement
	 */
	public static String toExactString(double d) {
		DecimalFormat format = new DecimalFormat("#.############");
		format.setRoundingMode(RoundingMode.HALF_DOWN);
		
		return format.format(d);
	}
}