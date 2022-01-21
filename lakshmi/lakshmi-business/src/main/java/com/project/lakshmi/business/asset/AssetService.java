package com.project.lakshmi.business.asset;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;

@Service
public interface AssetService extends DatabaseService<Asset> {
	
	/**
	 * @return price for asset and date
	 * On a besoin d'aller chercher l'ohlc via l'api correspondante, le prix corresponds plus ou moins à l'open
	 * Il faut neanmoins convertir en euro
	 */
	Double getPrice(Asset asset, Instant instant);

	List<Ohlc> getAllHistoricalData();
	
//	void generateCsv() throws URISyntaxException, IOException;
//
//	List<OldAsset> findAllSpecialOrder(AssetType type);
//
//	List<OldAsset> findAllNotManaged();
//
//	List<OldAsset> findAll(AssetType type);
}

