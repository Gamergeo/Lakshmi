package com.project.lakshmi.business.asset;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.project.lakshmi.business.DatabaseService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Price;

@Service
public interface AssetService extends DatabaseService<Asset> {
	
	/**
	 * @return price for asset and date
	 * Pour cela, on recupère le ohlc correspondant à la minute et l'open correspond au prix
	 */
	public Double getPrice(Asset asset, Date date);
	
//	void generateCsv() throws URISyntaxException, IOException;
//
//	List<OldAsset> findAllSpecialOrder(AssetType type);
//
//	List<OldAsset> findAllNotManaged();
//
//	List<OldAsset> findAll(AssetType type);
}

