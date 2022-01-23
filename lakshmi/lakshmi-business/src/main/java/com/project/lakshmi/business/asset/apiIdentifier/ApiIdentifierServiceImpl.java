package com.project.lakshmi.business.asset.apiIdentifier;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.api.cryptowatch.CryptoWatchApiService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.model.asset.Asset;

@Service("apiIdentifierService")
public class ApiIdentifierServiceImpl implements ApiIdentifierService {
	
	@Autowired
	CryptoWatchApiService cryptoWatchApiService;
	
	@Autowired
	AssetService assetService;
	
	/**
	 * @param identifiers (liste préenregistré des identifiants)
	 * @param isin
	 * @return tous les identifiants qui peuvent convenir à l'isin
	 */
	@Override
	public List<ApiIdentifier> findIdentifier(List<ApiIdentifier> apiIdentifiers, String isin) {
		
		List<ApiIdentifier> results = new ArrayList<ApiIdentifier>();
		
		for (ApiIdentifier apiIdentifier : apiIdentifiers) {
			
			if (apiIdentifier.getAsset().getIsin().equals(isin)) {
				results.add(apiIdentifier);
			}
		}
		
		return results;
	}
	
	/**
	 * @param identifiers (liste préenregistré des identifiants)
	 * @param isin
	 * @param market
	 * @return tous les identifiants qui peuvent convenir à l'isin
	 */
	@Override
	public List<ApiIdentifier> findIdentifier(List<ApiIdentifier> apiIdentifiers, String isin, String market) {
		
		List<ApiIdentifier> results = new ArrayList<ApiIdentifier>();
		
		for (ApiIdentifier apiIdentifier : apiIdentifiers) {
			
			if (apiIdentifier.getAsset().getIsin().equals(isin) &&
					apiIdentifier.getMarket().equals(market)) {
				results.add(apiIdentifier);
			}
		}
		
		return results;
	}
	
	/**
	 * @return une liste de tous les identifiants présents
	 */
	@Override
	public List<ApiIdentifier> getAllIdentifiers() {
		return cryptoWatchApiService.getAllIdentifiers();
	}
	
	/**
	 * @return l'asset associé à une pair du type btcusdt
	 * Renvoie null si l'asset n'existe pas en base
	 */
	@Override
	@Transactional
	public Asset getAsset(String pair) {
		
		Asset asset;
		
		for (int i = 1; i < pair.length(); i++) {
			String subPair = pair.substring(0, i);
			asset = assetService.findByIsinIfAny(subPair.toUpperCase());
			
			if (asset != null) {
				return asset;
			}
		}
		
		return null;
	}
	
	/**
	 * @return la currency associé à une pair du type btcusdt
	 * Renvoie null si l'asset n'existe pas en base
	 */
	@Override
	@Transactional
	public Asset getCurrency(String pair) {
		Asset asset = getAsset(pair);
		
		if (asset == null) {
			return null;
		}
		
		return getCurrency(pair, asset);
	}
	
	@Override
	@Transactional
	public Asset getCurrency(String pair, Asset asset) {
		if (asset == null) {
			return null;
		}
		
		String isinCurrency = pair.substring(asset.getIsin().length());
		
		return assetService.findByIsinIfAny(isinCurrency.toUpperCase());
	}
}