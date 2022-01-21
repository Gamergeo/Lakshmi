package com.project.lakshmi.business.api;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;

/**
 * Generic call to api
 */
@Service
public interface ApiService {

	/**
	 * @return la liste des ohlc pour une liste d'asset
	 */
	List<Ohlc> getHistoricalOhlc(List<Asset> asset);

	/**
	 * @return l'unique ohlc correspondant au prix
	 */
	Ohlc getPriceOhlc(Asset asset, Instant instant);
}
