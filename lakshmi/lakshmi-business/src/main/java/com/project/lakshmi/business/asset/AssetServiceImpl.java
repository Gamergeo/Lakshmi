package com.project.lakshmi.business.asset;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.business.asset.apiIdentifier.ApiIdentifierService;
import com.project.lakshmi.business.asset.ohlc.OhlcService;
import com.project.lakshmi.model.api.Api;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.model.asset.price.Ohlc;
import com.project.lakshmi.persistance.asset.AssetDao;
import com.project.lakshmi.technical.ApplicationException;

@Service("assetService")
public class AssetServiceImpl extends AbstractDatabaseService<Asset> implements AssetService {

	@Autowired
	AssetDao assetDao;

	@Autowired
	OhlcService ohlcService;

	@Autowired
	ApiIdentifierService apiIdentifierService;

	@Override
	public AssetDao getDao() {
		return assetDao;
	}

	/**
	 * @return price for asset and date On a besoin d'aller chercher l'ohlc via
	 *         l'api correspondante, le prix corresponds à la moyenne du high et du
	 *         low Il faut neanmoins convertir en euro
	 */
	@Override
	public Double getPrice(Asset asset, Instant instant) {
		
		Ohlc ohlc = ohlcService.getOhlc(asset, instant);
		ohlcService.convertToEuro(ohlc); // Normalement déja fait

		return (ohlc.getHigh() + ohlc.getLow()) / 2d;
	}

	@Override
	@Transactional
	public List<Ohlc> getAllHistoricalData() {
		List<Asset> assets = findAllManagedByApi(Api.getManagedApi());

		return ohlcService.getHistoricalOhlc(assets);
	}

	@Override
	@Transactional
	public List<Asset> findAllNotManaged() {
		return findAllManagedByApi(Api.NONE);
	}

	@Override
	@Transactional
	public List<Asset> findAllManagedByApi(List<Api> apis) {
		return assetDao.findAllManagedByApi(apis);
	}
	
	@Override
	@Transactional
	public List<Asset> findAllManagedByApi(Api api) {
		List<Api> apis = new ArrayList<Api>();
		apis.add(api);
		return findAllManagedByApi(apis);
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

		// Dans le cas de yahoo, la currency est toujours l'euro
		if (Api.YAHOO.equals(asset.getApiIdentifier().getApi())) {
			asset.getApiIdentifier().setCurrency(findByIsin("EUR"));
		}
		
		// Dans le cas Kucoin / cryptowatch, on essaie de retrouver la currency
		if (Api.CRYPTOWATCH.equals(asset.getApiIdentifier().getApi()) ||
				Api.KUCOIN.equals(asset.getApiIdentifier().getApi())) {
			
			String currencyIsin = asset.getApiIdentifier().getCurrency().getIsin();
			Asset currency = findByIsinIfAny(currencyIsin);
			
			if (currency == null) {
				throw new ApplicationException("La currency n'existe pas !" + currencyIsin);
			}
			
			asset.getApiIdentifier().setCurrency(currency);
		}

		if (Api.NONE.equals(asset.getApiIdentifier().getApi())) {
			asset.getApiIdentifier().setCurrency(null);
		}

		assetDao.saveOrUpdate(asset);
		asset.getApiIdentifier().setAsset(asset);
		apiIdentifierService.saveOrUpdate(asset.getApiIdentifier());
	}
	
	@Override
	@Transactional
	public void delete(Integer id) {
		apiIdentifierService.delete(id);
		assetDao.delete(id);
	}
}