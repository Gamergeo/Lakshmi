package com.project.lakshmi.business.ohlc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.Ohlc;

@Service("ohlcService")
public class OhlcServiceImpl implements OhlcService {
	
	@Override
	public Ohlc getOhlc(List<Ohlc> datas, String isin, Date date)  {
		
		LocalDate requestedLocalDate = toLocalDate(date);
		
		for (Ohlc ohlc : datas) {
			
			LocalDate localDate = toLocalDate(ohlc.getDate());
			
			if (ohlc.getIsin().equals(isin) && localDate.isEqual(requestedLocalDate)) {
				return ohlc;
			}
		}
		
		return null;
	}
	
	private LocalDate toLocalDate(Date date) {
	    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
	}
	

}
