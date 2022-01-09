package com.project.mscomptes.business.ohlc;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.mscomptes.model.Ohlc;

@Service
public interface OhlcService {

	Ohlc getOhlc(List<Ohlc> datas, String isin, Date date);

}
