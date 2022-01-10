package com.project.lakshmi.business.ohlc;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.Ohlc;

@Service
public interface OhlcService {

	Ohlc getOhlc(List<Ohlc> datas, String isin, Date date);

}
