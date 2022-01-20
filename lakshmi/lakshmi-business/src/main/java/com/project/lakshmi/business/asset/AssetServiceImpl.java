package com.project.lakshmi.business.asset;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.business.api.ApiService;
import com.project.lakshmi.business.asset.ohlc.OhlcService;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.persistance.asset.AssetDao;

@Service("assetService")
public class AssetServiceImpl extends AbstractDatabaseService<Asset> implements AssetService {
	
	@Autowired
	AssetDao assetDao;
	
	@Autowired
	OhlcService ohlcService;
	
	@Autowired
	ApiService apiService;
	
	@Override
	public AssetDao getDao() {
		return assetDao;
	}
	
	/**
	 * @return price for asset and date
	 * On a besoin d'aller chercher l'ohlc via l'api correspondante, le prix corresponds à la moyenne du high et du low
	 * Il faut neanmoins convertir en euro
	 */
	@Override
	@Transactional
	public Double getPrice(Asset asset, Instant instant) {
		Ohlc ohlc = apiService.getPriceOhlc(asset, instant);
		ohlcService.convertToEuro(ohlc, asset.getApiIdentifier().getCurrency());
		
		return (ohlc.getHigh() + ohlc.getLow())/2d;
	}
	
}