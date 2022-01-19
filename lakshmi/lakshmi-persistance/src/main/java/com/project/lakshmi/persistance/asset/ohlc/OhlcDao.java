package com.project.lakshmi.persistance.asset.ohlc;

import java.util.Date;

import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.persistance.IDao;

public interface OhlcDao extends IDao<Ohlc> {

	Ohlc findOhlc(Integer assetId, Date date);

	/**
	 * @return the maximum of high price between two date
	 */
	Ohlc findMax(Integer assetId, Date beginDate, Date endDate);

	/**
	 * @return the minimum of low price between two date
	 */
	Ohlc findMin(Integer assetId, Date beginDate, Date endDate);

}