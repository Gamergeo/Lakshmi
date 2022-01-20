package com.project.lakshmi.persistance.asset.ohlc;

import java.time.Instant;

import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.persistance.IDao;

public interface OhlcDao extends IDao<Ohlc> {

	Ohlc findOhlc(Integer assetId, Instant date);

//	/**
//	 * @return the maximum of high price between two date
//	 */
//	Ohlc findMax(Integer assetId, Instant beginDate, Instant endDate);
//
//	/**
//	 * @return the minimum of low price between two date
//	 */
//	Ohlc findMin(Integer assetId, Instant beginDate, Instant endDate);

}