package com.project.lakshmi.business.ohlc.exporter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.technical.DateUtil;
import com.project.lakshmi.technical.FileUtils;
import com.project.lakshmi.technical.NumberUtil;
import com.project.lakshmi.technical.TechnicalConstants;

@Service("ohlcExporterService")
public class OhlcExporterServiceImpl implements OhlcExporterService {

	@Override
	public String exportOhlc(List<Ohlc> ohlcs) {
		// S'il existe déja on le supprime
		FileUtils.removeFile(OhlcExporterConstants.FILE_NAME);
		String currentIsin = null;
		
		String message = "";
		
		for (Ohlc ohlc : ohlcs) {
			
			Asset asset = ohlc.getAsset();
			
			if (!asset.getIsin().equals(currentIsin)) {
				currentIsin = asset.getIsin();
				System.out.println("Writing " + currentIsin);
			}
			
			// On n'importe que les OHLC passés
			if (ohlc.getDate().isBefore(Instant.now().minus(1, ChronoUnit.DAYS)) && ohlc.getOpen() != 0d) {
				// ISIN
				message += asset.getIsin();
				message += TechnicalConstants.CSV_SEPARATOR;

				// Date		
				String date = DateUtil.formatDate(ohlc.getDate(), OhlcExporterConstants.DATE_FORMAT); 
				message += date;
				message += TechnicalConstants.CSV_SEPARATOR;
				
				// Open
				Double openPrice = ohlc.getOpen();
				if (openPrice != null) {
					message += NumberUtil.toExactString(openPrice);
				}
				message += TechnicalConstants.CSV_SEPARATOR;
				
				// High
				Double highPrice = ohlc.getHigh();
				if (highPrice != null) {
					message += NumberUtil.toExactString(highPrice);
				}
				message += TechnicalConstants.CSV_SEPARATOR;
				
				// Low
				Double lowPrice = ohlc.getLow();
				if (lowPrice != null) {
					message += NumberUtil.toExactString(lowPrice);
				}
				message += TechnicalConstants.CSV_SEPARATOR;
				
				// Close
				Double closePrice = ohlc.getClose();
				if (closePrice != null) {
					message += NumberUtil.toExactString(closePrice);
				}
				message += TechnicalConstants.CSV_SEPARATOR;
				
				message += "\n";
			}
		}
		
		writeOnFile(message);
		
		return OhlcExporterConstants.FILE_NAME;
	}
	
	private void writeOnFile(String message) {
		FileUtils.writeOnFile(OhlcExporterConstants.FILE_NAME, message);
	}
}