package com.project.lakshmi.business.asset.apiIdentifier;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.lakshmi.business.AbstractDatabaseService;
import com.project.lakshmi.business.api.cryptowatch.CryptoWatchApiService;
import com.project.lakshmi.business.asset.AssetService;
import com.project.lakshmi.model.api.ApiIdentifier;
import com.project.lakshmi.model.asset.Asset;
import com.project.lakshmi.persistance.IDao;
import com.project.lakshmi.persistance.asset.apiidentifier.ApiIdentifierDao;

@Service("apiIdentifierService")
public class ApiIdentifierServiceImpl extends AbstractDatabaseService<ApiIdentifier> implements ApiIdentifierService {
	
	@Autowired
	CryptoWatchApiService cryptoWatchApiService;
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	ApiIdentifierDao apiIdentifierDao;

	@Override
	public IDao<ApiIdentifier> getDao() {
		return apiIdentifierDao;
	}
	
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
	 * @return an api identifier correclty set with asset and currency
	 */
	@Override
	@Transactional
	public ApiIdentifier createIdentifier(String pair, String market) {
		List<Asset> existingAssets = assetService.findAll();
		Asset asset = getAsset(pair, existingAssets);
		
		// Il n'en existe pas, on arrète la
		if (asset == null) {
			return null;
		}
		
		// On cherche à retrouver la currency
		Asset currency = getCurrency(pair, existingAssets, asset);
		
		// Il n'en existe pas, on arrète la
		if (currency == null) {
			return null;
		}
		
		// Sinon on crée l'apiidentifier
		return new ApiIdentifier(asset, currency, market);
	}
	
	/**
	 * Découpe la pair et verifie s'il on arrive à determiner un asset existant
	 */
	private Asset getAsset(String pair, List<Asset> existingAssets) {
		// On cerche à determiner l'isin de l'asset
		for (int i = 1; i < pair.length(); i++) {
			String subPair = pair.substring(0, i).toUpperCase(); // On découpe les n premiers caractères
			
			// On determine si l'isin existe
			for (Asset existingAsset : existingAssets) {
				
				// On a trouvé
				if (existingAsset.getIsin().equals(subPair)) {
					return existingAsset;
				}
			}
		}
		
		return null;
	}
	
	private Asset getCurrency(String pair, List<Asset> existingAssets, Asset asset) {
		if (asset == null) {
			return null;
		}
		
		String isinCurrency = pair.substring(asset.getIsin().length()).toUpperCase();
		
		// On determine si l'isin existe
		for (Asset existingAsset : existingAssets) {
			
			// On a trouvé
			if (existingAsset.getIsin().equals(isinCurrency)) {
				return existingAsset;
			}
		}
		
		return null;
	}
}