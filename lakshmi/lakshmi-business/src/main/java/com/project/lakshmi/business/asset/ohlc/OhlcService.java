package com.project.lakshmi.business.asset.ohlc;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;

@Service
public interface OhlcService extends DatabaseService<Ohlc> {

	/**
	 * @return the price in database if exists
	 */
	Ohlc find(Asset asset, Date date);

	/**
	 * @return the ohlc corresponding to date and asset
	 * Get the day ohlc
	 */
	Ohlc findDayOhlc(Asset asset, Date date);

	/**
	 * modify the ohlc to convert to euroµ
	 * Ohlc is in currency asset
	 */
	void convertToEuro(Ohlc ohlc, Asset currency);

	/**
	 * save an update a OHLC set asset currency
	 */
	void saveOrUpdate(Ohlc ohlc, Asset currency);

}

