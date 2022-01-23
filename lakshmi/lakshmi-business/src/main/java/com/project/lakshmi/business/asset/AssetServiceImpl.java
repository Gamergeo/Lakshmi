package com.project.lakshmi.business.asset;

import java.time.Instant;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.business.asset.ohlc.OhlcService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.persistance.asset.AssetDao;

@Service("assetService")
public class AssetServiceImpl extends AbstractDatabaseService<Asset> implements AssetService {
	
	@Autowired
	AssetDao assetDao;
	
	@Autowired
	OhlcService ohlcService;
	
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
	public Double getPrice(Asset asset, Instant instant) {
		Ohlc ohlc = ohlcService.getOhlc(asset, instant);
		ohlcService.convertToEuro(ohlc);
		
		return (ohlc.getHigh() + ohlc.getLow())/2d;
	}
	
	@Override
	@Transactional
	public List<Ohlc> getAllHistoricalData() {
		List<Asset> assets = findAll();
		
		return ohlcService.getHistoricalOhlc(assets);
	}
	
	@Override
	@Transactional
	public List<Asset> findAllNotManaged() {
		return assetDao.findAllNotManaged();
	}
	
	@Override
	@Transactional
	public List<Asset> findAllManagedByApi(Api api) {
		return assetDao.findAllManagedByApi(api);
	}
	
	/**
	 * Renvoie une exception si non trouvé
	 */
	@Override
	@Transactional
	public Asset findByIsin(String isin) {
		Asset asset = findByIsinIfAny(isin);
		
		if (asset == null) {
			throw new NotYetImplementedException("Isin non trouvé : " + isin);
		}
		
		return asset;
	}
	
	/**
	 * Renvoie null si non trouvé
	 */
	@Override
	@Transactional
	public Asset findByIsinIfAny(String isin) {
		return assetDao.findByIsin(isin);
	}
	
	@Override
	@Transactional
	public void saveOrUpdate(Asset asset) {
		
	}
}