package com.project.lakshmi.business.asset.ohlc;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;

@Service
public interface OhlcService extends DatabaseService<Ohlc> {

	/**
	 * modify the ohlc to convert to euroµ
	 * Ohlc is in currency asset
	 */
	void convertToEuro(Ohlc ohlc, Asset currency);

	/**
	 * save an update a OHLC set asset currency
	 */
	void saveOrUpdate(Ohlc ohlc, Asset currency);

	/**
	 * @return the ohlc corresponding to date and asset
	 */
	Ohlc find(Asset asset, Instant date);

}

